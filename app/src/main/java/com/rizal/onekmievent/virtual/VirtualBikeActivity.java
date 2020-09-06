package com.rizal.onekmievent.virtual;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import butterknife.BindView;
import butterknife.ButterKnife;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.textfield.TextInputEditText;
import com.rizal.onekmievent.LoginActivity;
import com.rizal.onekmievent.MainActivity;
import com.rizal.onekmievent.R;
import com.rizal.onekmievent.choose.ChooseSportsActivity;
import com.rizal.onekmievent.model.DataPesertaBike;
import com.rizal.onekmievent.model.Peserta;
import com.rizal.onekmievent.ui.home.HomeFragment;
import com.rizal.onekmievent.utils.Preferences;

import org.json.JSONObject;

public class VirtualBikeActivity extends AppCompatActivity {

    @BindView(R.id.backBike)
    ImageView backBike;
    @BindView(R.id.et_urlBike)
    TextInputEditText urlImage;
    @BindView(R.id.et_durasiBike)
    TextInputEditText durasiWaktu;
    @BindView(R.id.et_jarakBike)
    TextInputEditText jarakTempuh;
    @BindView(R.id.et_genderBike)
    TextInputEditText genderPeserta;
    @BindView(R.id.et_sepedaBike)
    TextInputEditText jenisSepeda;
    @BindView(R.id.btn_uploadBike)
    Button uploadData;
    @BindView(R.id.progressUploadBike)
    ProgressBar progressBarUpload;

    private static final String TAG = "VirtualBikeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_bike);
        ButterKnife.bind(this);

        AndroidNetworking.initialize(this);

        backBike.setOnClickListener(v -> {
            startActivity(new Intent(VirtualBikeActivity.this, MainActivity.class));
            finish();
        });

        uploadData.setOnClickListener(view -> updateBike());

    }

    private void updateBike() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(VirtualBikeActivity.this);
        alertDialogBuilder.setTitle("Konfirmasi Lomba");
        alertDialogBuilder
                .setMessage("Anda Yakin Data Sudah Terisi Benar?")
                .setIcon(R.drawable.bike)
                .setCancelable(false)
                .setPositiveButton("Ya", (dialog, which) -> {
                    progressBarUpload.setVisibility(View.VISIBLE);
                    updateDataBike();
                })
                .setNegativeButton("Tidak", (dialog, which) -> {
                    progressBarUpload.setVisibility(View.GONE);
                    dialog.cancel();
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void updateDataBike(){
        String url = urlImage.getText().toString();
        String durasi = durasiWaktu.getText().toString();
        String jarak = jarakTempuh.getText().toString();
        String gender = genderPeserta.getText().toString();
        String jenis = jenisSepeda.getText().toString();

        if (TextUtils.isEmpty(url)) {
            urlImage.setError("Url Image Harus Diisi");
            return;
        }

        if (TextUtils.isEmpty(durasi)) {
            durasiWaktu.setError("Durasi Waktu Harus Diisi");
            return;
        }

        if (TextUtils.isEmpty(jarak)) {
            jarakTempuh.setError("Jarak Tempuh Harus Diisi");
            return;
        }

        if (TextUtils.isEmpty(gender)) {
            genderPeserta.setError("Gender Harus Diisi");
            return;
        }

        if (TextUtils.isEmpty(jenis)) {
            jenisSepeda.setError("Jenis Sepeda Harus Diisi");
            return;
        }

        progressBarUpload.setVisibility(View.VISIBLE);

        if (TextUtils.isEmpty(url) && TextUtils.isEmpty(durasi) && TextUtils.isEmpty(jarak) && TextUtils.isEmpty(gender) && TextUtils.isEmpty(jenis)) {
            Toast.makeText(this, "Kolom Masih Kosong, Silahkan Isi", Toast.LENGTH_SHORT).show();
        }

        Peserta peserta = Preferences.getPeserta(getApplicationContext());
        Log.d(TAG, "proses: " + peserta.getVarcharEmployeeID());
        AndroidNetworking.post("http://api.smknsatupebayuran.sch.id/api/updateDataBike.php")
                .addBodyParameter("varcharEmployeeID", "" +peserta.getVarcharEmployeeID())
                .addBodyParameter("txtUrlImage", "" +url)
                .addBodyParameter("txtKategoriGender", "" +gender)
                .addBodyParameter("txtKategoriSepeda", "" +jenis)
                .addBodyParameter("txtWaktuTempuh", "" +durasi)
                .addBodyParameter("txtJarakTempuh", "" +jarak)
                .setPriority(Priority.MEDIUM)
                .setTag("Update Data")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean status = response.getBoolean("state");
                            String pesan = response.getString("data");
                            Log.d(TAG, "onStatus: " + status);
                            Log.d(TAG, "onPesan: " + pesan);
                            if (status) {
                                Toast.makeText(VirtualBikeActivity.this, "Berhasil Diupdate Ke Pertandingan", Toast.LENGTH_SHORT).show();
                                progressBarUpload.setVisibility(View.GONE);
                                Intent i = getIntent();
                                setResult(RESULT_OK, i);
                                startActivity(new Intent(VirtualBikeActivity.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(VirtualBikeActivity.this, "Gagal Update Pertandingan", Toast.LENGTH_SHORT).show();
                                progressBarUpload.setVisibility(View.GONE);
                                Intent i = getIntent();
                                setResult(RESULT_CANCELED, i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("onError: ", "" + anError.getErrorBody());
                    }
                });
    }
}
