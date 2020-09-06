package com.rizal.onekmievent.virtual;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.rizal.onekmievent.LoginActivity;
import com.rizal.onekmievent.R;
import com.rizal.onekmievent.choose.ChooseSportsActivity;
import com.rizal.onekmievent.model.Peserta;
import com.rizal.onekmievent.ui.home.HomeFragment;
import com.rizal.onekmievent.utils.Preferences;

import org.json.JSONObject;

public class VirtualBikeActivity extends AppCompatActivity {

    @BindView(R.id.backBike)
    ImageView backBike;
    @BindView(R.id.et_urlBike)
    EditText urlImage;
    @BindView(R.id.et_durasiBike)
    EditText durasiWaktu;
    @BindView(R.id.et_jarakBike)
    EditText jarakTempuh;
    @BindView(R.id.et_genderBike)
    EditText genderPeserta;
    @BindView(R.id.et_sepedaBike)
    EditText jenisSepeda;
    @BindView(R.id.cvUpload)
    CardView uploadData;
    @BindView(R.id.progressUpload)
    ProgressBar progressBarUpload;

    private static final String TAG = "VirtualBikeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_bike);
        ButterKnife.bind(this);

        backBike.setOnClickListener(v -> {
            startActivity(new Intent(VirtualBikeActivity.this, HomeFragment.class));
            finish();
        });

        String url = urlImage.getText().toString();
        String durasi = durasiWaktu.getText().toString();
        String jarak = jarakTempuh.getText().toString();
        String gender = genderPeserta.getText().toString();
        String jenis = jenisSepeda.getText().toString();

        uploadData.setOnClickListener(v -> {
            if (url.isEmpty()){
                urlImage.setError("Kolom Masih Kosong");
                Toast.makeText(VirtualBikeActivity.this, "Url Image Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show();
                return;
            }

            if(durasi.isEmpty()){
                durasiWaktu.setError("Kolom Masih Kosong");
                Toast.makeText(VirtualBikeActivity.this, "Durasi Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show();
                return;
            }

            if(jarak.isEmpty()){
                jarakTempuh.setError("Kolom Masih Kosong");
                Toast.makeText(VirtualBikeActivity.this, "Jarak Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show();
                return;
            }

            if(gender.isEmpty()){
                genderPeserta.setError("Kolom Masih Kosong");
                Toast.makeText(VirtualBikeActivity.this, "Gender Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show();
                return;
            }

            if(jenis.isEmpty()){
                jenisSepeda.setError("Kolom Masih Kosong");
                Toast.makeText(VirtualBikeActivity.this, "Jenis Sepeda Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show();
                return;
            }

            progressBarUpload.setVisibility(View.VISIBLE);
            Peserta peserta = Preferences.getPeserta(VirtualBikeActivity.this);
            AndroidNetworking.post("https://onekmievents.000webhostapp.com/api/updateDataBike.php")
                    .addBodyParameter("varcharEmployeeID",""+peserta.getVarcharEmployeeID())
                    .addBodyParameter("txtUrlImage",""+url)
                    .addBodyParameter("txtKategoriGender",""+gender)
                    .addBodyParameter("txtKategoriSepeda",""+jenis)
                    .addBodyParameter("txtWaktuTempuh",""+durasiWaktu)
                    .addBodyParameter("txtJarakTempuh",""+jarak)
                    .setPriority(Priority.MEDIUM)
                    .setTag("Update Data")
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                boolean status = response.getBoolean("status");
                                String pesan = response.getString("result");
                                Log.d(TAG, "onStatus: " + status);
                                Log.d(TAG, "onPesan: " + pesan);

                                if (status){
                                    Toast.makeText(VirtualBikeActivity.this, "Berhasil Diupdate Ke Pertandingan", Toast.LENGTH_SHORT).show();
                                    progressBarUpload.setVisibility(View.GONE);
                                    Intent i = getIntent();
                                    setResult(RESULT_OK, i);
//                                    startActivity(new Intent(VirtualBikeActivity.this, VirtualBikeActivity.class));
//                                    finish();
                                }else {
                                    Toast.makeText(VirtualBikeActivity.this, "Gagal Update Lomba", Toast.LENGTH_SHORT).show();
                                    progressBarUpload.setVisibility(View.GONE);
                                    Intent i = getIntent();
                                    setResult(RESULT_CANCELED, i);
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.d("onError: ", "" + anError.getErrorBody());
                        }
                    });
        });
    }
}
