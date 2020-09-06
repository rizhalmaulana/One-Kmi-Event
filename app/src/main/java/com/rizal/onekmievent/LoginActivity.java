package com.rizal.onekmievent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rizal.onekmievent.client.MobileService;
import com.rizal.onekmievent.model.ApiUtils;
import com.rizal.onekmievent.model.Peserta;
import com.rizal.onekmievent.model.Response;
import com.rizal.onekmievent.utils.BaseActivity;
import com.rizal.onekmievent.utils.ConstanKey;
import com.rizal.onekmievent.utils.Preferences;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.btn_masuk)
    Button btnMasuk;
    @BindView(R.id.et_peserta)
    EditText etPeserta;
    @BindView(R.id.progressLogin)
    ProgressBar progressBar;

    MobileService mobileService;
    LoginActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        if (Preferences.getLoginFlag(getApplicationContext())) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        mobileService = ApiUtils.MobileService(getApplicationContext());
        btnMasuk.setOnClickListener(v -> {
            String noPeserta = etPeserta.getText().toString().trim();

            if (noPeserta.isEmpty()) {
                etPeserta.setError("Kolom Masih Kosong");
                Toast.makeText(LoginActivity.this, "No Peserta Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, String> map = new HashMap<>();
            map.put("varcharEmployeeID", noPeserta);

            progressBar.setVisibility(View.VISIBLE);
            mobileService.login(map).enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    Response body = response.body();
                    if (response.isSuccessful()){
                        Peserta peserta = new Gson().fromJson(new Gson().toJson(body.getData()), Peserta.class);
                        if (!body.isState()){
                            progressBar.setVisibility(View.INVISIBLE);
                            showMessage("No Peserta Salah");
                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            activity.goToHome(body, ConstanKey.FLAG_PERSONE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    showMessage("Terjadi kesalahan, Coba beberapa saat lagi");
                }
            });
        });
    }

    private void goToHome(Response body, ConstanKey flagPersone) {
        if(flagPersone == ConstanKey.FLAG_PERSONE){
            Peserta peserta = new Gson().fromJson(new Gson().toJson(body.getData()), Peserta.class);
            Preferences.setPeserta(getApplicationContext(), peserta);
            showMessage("Login berhasil. Selamat datang, " + peserta.getTxtEmployeeName());
        }

        Preferences.setLoginType(getApplicationContext(), flagPersone);
        Preferences.setLoginFlag(getApplicationContext(), true);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}
