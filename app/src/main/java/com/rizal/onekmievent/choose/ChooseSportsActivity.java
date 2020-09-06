package com.rizal.onekmievent.choose;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.rizal.onekmievent.LoginActivity;
import com.rizal.onekmievent.R;
import com.rizal.onekmievent.model.Peserta;
import com.rizal.onekmievent.utils.Preferences;
import com.rizal.onekmievent.virtual.VirtualBadmintonActivity;
import com.rizal.onekmievent.virtual.VirtualBasketActivity;
import com.rizal.onekmievent.virtual.VirtualBikeActivity;
import com.rizal.onekmievent.virtual.VirtualBolaVoli;
import com.rizal.onekmievent.virtual.VirtualPanahanActivity;
import com.rizal.onekmievent.virtual.VirtualRunActivity;
import com.rizal.onekmievent.virtual.VirtualSepakBolaActivity;
import com.rizal.onekmievent.virtual.VirtualTenisMeja;

import org.json.JSONObject;

public class ChooseSportsActivity extends AppCompatActivity {

    @BindView(R.id.lVirtualBike)
    LinearLayout virtualBike;
    @BindView(R.id.lVirtualRun)
    LinearLayout virtualRun;
    @BindView(R.id.lSepakBola)
    LinearLayout sepakBola;
    @BindView(R.id.lTenisMeja)
    LinearLayout tenisMeja;
    @BindView(R.id.lBolaVoli)
    LinearLayout bolaVoli;
    @BindView(R.id.lBasket)
    LinearLayout bolaBasket;
    @BindView(R.id.lBadminton)
    LinearLayout badminton;
    @BindView(R.id.lPanahan)
    LinearLayout panahan;

    @BindView(R.id.checkVirtualBike)
    ImageView checkBike;
    @BindView(R.id.checkVirtualRun)
    ImageView checkRun;
    @BindView(R.id.checkSepakBola)
    ImageView checkBola;
    @BindView(R.id.checkTenisMeja)
    ImageView checkTenisMeja;
    @BindView(R.id.checkVoli)
    ImageView checkVoli;
    @BindView(R.id.checkBasket)
    ImageView checkBasket;
    @BindView(R.id.checkBadminton)
    ImageView checkBadminton;
    @BindView(R.id.checkPanahan)
    ImageView checkPanahan;

    @BindView(R.id.progressSport)
    ProgressBar progressBarSport;
    @BindView(R.id.backSport)
    ImageView kembaliSports;
    @BindView(R.id.btnKonfirmasi)
    Button btnKonfirmasi;
    @BindView(R.id.textKategoriSport)
    TextView kategoriSport;

    private static final String TAG = "ChooseSportsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_sports);
        ButterKnife.bind(this);

        virtualBike.setOnClickListener(v -> setVirtualBike());
        virtualRun.setOnClickListener(v -> setVirtualRun());
        sepakBola.setOnClickListener(v -> setVirtualBola());
        tenisMeja.setOnClickListener(v -> setVirtualTenis());
        bolaVoli.setOnClickListener(v -> setBolaVoli());
        bolaBasket.setOnClickListener(v -> setBolaBasket());
        badminton.setOnClickListener(v -> setBadminton());
        panahan.setOnClickListener(v -> setPanahan());

        kembaliSports.setOnClickListener(v -> finish());
    }

    //Kirim Data Bike
    private void kirimDataBike() {
        Peserta peserta = Preferences.getPeserta(getApplicationContext());
        AndroidNetworking.post("https://onekmievents.000webhostapp.com/api/tambahDataBike.php")
                .addBodyParameter("varcharEmployeeID", "" + peserta.getVarcharEmployeeID())
                .addBodyParameter("txtEmployeeName", "" + peserta.getTxtEmployeeName())
                .setPriority(Priority.MEDIUM)
                .setTag("Tambah Data")
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
                                Toast.makeText(ChooseSportsActivity.this, "Berhasil Ditambahkan Ke Pertandingan", Toast.LENGTH_SHORT).show();
                                progressBarSport.setVisibility(View.GONE);
                                Intent i = getIntent();
                                setResult(RESULT_OK, i);
                                startActivity(new Intent(ChooseSportsActivity.this, VirtualBikeActivity.class));
                                finish();
                            }else if (pesan.equals("Gagal, Data Sudah Ada")){
                                Toast.makeText(ChooseSportsActivity.this, "Anda Sedang Mengikuti Lomba Ini", Toast.LENGTH_SHORT).show();
                                progressBarSport.setVisibility(View.GONE);
                                Intent i = getIntent();
                                setResult(RESULT_CANCELED, i);
                                startActivity(new Intent(ChooseSportsActivity.this, VirtualBikeActivity.class));
                            }else {
                                Toast.makeText(ChooseSportsActivity.this, "Gagal Konfirmasi Lomba", Toast.LENGTH_SHORT).show();
                                progressBarSport.setVisibility(View.GONE);
                                Intent i = getIntent();
                                setResult(RESULT_CANCELED, i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("Error Tambah Data", "" + anError.getErrorBody());
                    }
                });
    }

    @SuppressLint("SetTextI18n")
    private void setVirtualBike() {
        checkBike.setVisibility(View.VISIBLE);

        checkRun.setVisibility(View.GONE);
        checkBadminton.setVisibility(View.GONE);
        checkBasket.setVisibility(View.GONE);
        checkBola.setVisibility(View.GONE);
        checkPanahan.setVisibility(View.GONE);
        checkTenisMeja.setVisibility(View.GONE);
        checkVoli.setVisibility(View.GONE);

        kategoriSport.setText("Virtual Bike");

        btnKonfirmasi.setOnClickListener(v -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ChooseSportsActivity.this);
            alertDialogBuilder.setTitle("Konfirmasi Lomba");
            alertDialogBuilder
                    .setMessage("Anda Yakin Ingin Mengikuti Virtual Bike?")
                    .setIcon(R.drawable.bike)
                    .setCancelable(false)
                    .setPositiveButton("Ya", (dialog, which) -> {
                        progressBarSport.setVisibility(View.VISIBLE);
                        kirimDataBike();
                    })
                    .setNegativeButton("Tidak", (dialog, which) -> {
                        progressBarSport.setVisibility(View.GONE);
                        dialog.cancel();
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        });
    }
    //Kirim Data Bike

    //Kirim Data Run
    private void kirimDataRun() {
        Peserta peserta = Preferences.getPeserta(getApplicationContext());
        AndroidNetworking.post("https://onekmievents.000webhostapp.com/api/tambahDataRun.php")
                .addBodyParameter("varcharEmployeeID", "" + peserta.getVarcharEmployeeID())
                .addBodyParameter("txtEmployeeName", "" + peserta.getTxtEmployeeName())
                .setPriority(Priority.MEDIUM)
                .setTag("Tambah Data")
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
                                Toast.makeText(ChooseSportsActivity.this, "Berhasil Ditambahkan Ke Pertandingan", Toast.LENGTH_SHORT).show();
                                progressBarSport.setVisibility(View.GONE);
                                Intent i = getIntent();
                                setResult(RESULT_OK, i);
                                startActivity(new Intent(ChooseSportsActivity.this, VirtualRunActivity.class));
                                finish();
                            }else if (pesan.equals("Gagal, Data Sudah Ada")){
                                Toast.makeText(ChooseSportsActivity.this, "Anda Sedang Mengikuti Lomba Ini", Toast.LENGTH_SHORT).show();
                                progressBarSport.setVisibility(View.GONE);
                                Intent i = getIntent();
                                setResult(RESULT_CANCELED, i);
                                startActivity(new Intent(ChooseSportsActivity.this, VirtualRunActivity.class));
                                finish();
                            }else {
                                Toast.makeText(ChooseSportsActivity.this, "Gagal Mengikuti Lomba", Toast.LENGTH_SHORT).show();
                                progressBarSport.setVisibility(View.GONE);
                                Intent i = getIntent();
                                setResult(RESULT_CANCELED, i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("Error Tambah Data", "" + anError.getErrorBody());
                    }
                });
    }

    @SuppressLint("SetTextI18n")
    private void setVirtualRun() {
        checkRun.setVisibility(View.VISIBLE);

        checkBike.setVisibility(View.GONE);
        checkBadminton.setVisibility(View.GONE);
        checkBasket.setVisibility(View.GONE);
        checkBola.setVisibility(View.GONE);
        checkPanahan.setVisibility(View.GONE);
        checkTenisMeja.setVisibility(View.GONE);
        checkVoli.setVisibility(View.GONE);

        kategoriSport.setText("Virtual Run");

        btnKonfirmasi.setOnClickListener(v -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ChooseSportsActivity.this);
            alertDialogBuilder.setTitle("Konfirmasi Lomba");
            alertDialogBuilder
                    .setMessage("Anda Yakin Ingin Mengikuti Virtual Run?")
                    .setIcon(R.drawable.run)
                    .setCancelable(false)
                    .setPositiveButton("Ya", (dialog, which) -> {
                        progressBarSport.setVisibility(View.VISIBLE);
                        kirimDataRun();
                    })
                    .setNegativeButton("Tidak", (dialog, which) -> {
                        progressBarSport.setVisibility(View.GONE);
                        dialog.cancel();
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        });
    }
    //Kirim Data Run

    //Kirim Data Bola
    private void setVirtualBola() {
        checkBola.setVisibility(View.VISIBLE);

        checkBike.setVisibility(View.GONE);
        checkBadminton.setVisibility(View.GONE);
        checkBasket.setVisibility(View.GONE);
        checkRun.setVisibility(View.GONE);
        checkPanahan.setVisibility(View.GONE);
        checkTenisMeja.setVisibility(View.GONE);
        checkVoli.setVisibility(View.GONE);

        kategoriSport.setText("Sepak Bola");

        btnKonfirmasi.setOnClickListener(v -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ChooseSportsActivity.this);
            alertDialogBuilder.setTitle("Konfirmasi Lomba");
            alertDialogBuilder
                    .setMessage("Anda Yakin Ingin Mengikuti Sepak Bola?")
                    .setIcon(R.drawable.run)
                    .setCancelable(false)
                    .setPositiveButton("Ya", (dialog, which) -> {
                        progressBarSport.setVisibility(View.VISIBLE);
                        kirimDataBola();
                    })
                    .setNegativeButton("Tidak", (dialog, which) -> {
                        progressBarSport.setVisibility(View.GONE);
                        dialog.cancel();
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        });
    }

    private void kirimDataBola() {
        Peserta peserta = Preferences.getPeserta(getApplicationContext());
        AndroidNetworking.post("https://onekmievents.000webhostapp.com/api/tambahDataBola.php")
                .addBodyParameter("varcharEmployeeID", "" + peserta.getVarcharEmployeeID())
                .addBodyParameter("txtEmployeeName", "" + peserta.getTxtEmployeeName())
                .setPriority(Priority.MEDIUM)
                .setTag("Tambah Data")
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
                                Toast.makeText(ChooseSportsActivity.this, "Berhasil Ditambahkan Ke Pertandingan", Toast.LENGTH_SHORT).show();
                                progressBarSport.setVisibility(View.GONE);
                                Intent i = getIntent();
                                setResult(RESULT_OK, i);
                                startActivity(new Intent(ChooseSportsActivity.this, VirtualSepakBolaActivity.class));
                                finish();
                            }else if (pesan.equals("Gagal, Data Sudah Ada")){
                                Toast.makeText(ChooseSportsActivity.this, "Anda Sedang Mengikuti Lomba Ini", Toast.LENGTH_SHORT).show();
                                progressBarSport.setVisibility(View.GONE);
                                Intent i = getIntent();
                                setResult(RESULT_CANCELED, i);
                                startActivity(new Intent(ChooseSportsActivity.this, VirtualSepakBolaActivity.class));
                                finish();
                            }else {
                                Toast.makeText(ChooseSportsActivity.this, "Gagal Konfirmasi Lomba", Toast.LENGTH_SHORT).show();
                                progressBarSport.setVisibility(View.GONE);
                                Intent i = getIntent();
                                setResult(RESULT_CANCELED, i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("Error Tambah Data", "" + anError.getErrorBody());
                    }
                });
    }
    //Kirim Data Bola

    //Kirim Data Tenis
    private void setVirtualTenis() {
        checkTenisMeja.setVisibility(View.VISIBLE);

        checkBike.setVisibility(View.GONE);
        checkBadminton.setVisibility(View.GONE);
        checkBasket.setVisibility(View.GONE);
        checkRun.setVisibility(View.GONE);
        checkPanahan.setVisibility(View.GONE);
        checkBola.setVisibility(View.GONE);
        checkVoli.setVisibility(View.GONE);

        kategoriSport.setText("Tenis Meja");

        btnKonfirmasi.setOnClickListener(v -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ChooseSportsActivity.this);
            alertDialogBuilder.setTitle("Konfirmasi Lomba");
            alertDialogBuilder
                    .setMessage("Anda Yakin Ingin Mengikuti Tenis Meja?")
                    .setIcon(R.drawable.tenismeja)
                    .setCancelable(false)
                    .setPositiveButton("Ya", (dialog, which) -> {
                        progressBarSport.setVisibility(View.VISIBLE);
                        kirimDataTenis();
                    })
                    .setNegativeButton("Tidak", (dialog, which) -> {
                        progressBarSport.setVisibility(View.GONE);
                        dialog.cancel();
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        });
    }

    private void kirimDataTenis() {
        Peserta peserta = Preferences.getPeserta(getApplicationContext());
        AndroidNetworking.post("https://onekmievents.000webhostapp.com/api/tambahDataTenis.php")
                .addBodyParameter("varcharEmployeeID", "" + peserta.getVarcharEmployeeID())
                .addBodyParameter("txtEmployeeName", "" + peserta.getTxtEmployeeName())
                .setPriority(Priority.MEDIUM)
                .setTag("Tambah Data")
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
                                Toast.makeText(ChooseSportsActivity.this, "Berhasil Ditambahkan Ke Pertandingan", Toast.LENGTH_SHORT).show();
                                progressBarSport.setVisibility(View.GONE);
                                Intent i = getIntent();
                                setResult(RESULT_OK, i);
                                startActivity(new Intent(ChooseSportsActivity.this, VirtualTenisMeja.class));
                                finish();
                            }else if (pesan.equals("Gagal, Data Sudah Ada")){
                                Toast.makeText(ChooseSportsActivity.this, "Anda Sedang Mengikuti Lomba Ini", Toast.LENGTH_SHORT).show();
                                progressBarSport.setVisibility(View.GONE);
                                Intent i = getIntent();
                                setResult(RESULT_CANCELED, i);
                                startActivity(new Intent(ChooseSportsActivity.this, VirtualTenisMeja.class));
                                finish();
                            }else {
                                Toast.makeText(ChooseSportsActivity.this, "Gagal Konfirmasi Lomba", Toast.LENGTH_SHORT).show();
                                progressBarSport.setVisibility(View.GONE);
                                Intent i = getIntent();
                                setResult(RESULT_CANCELED, i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("Error Tambah Data", "" + anError.getErrorBody());
                    }
                });
    }
    //Kirim Data Tenis

    //Kirim Data Bola Voli
    private void setBolaVoli() {
        checkVoli.setVisibility(View.VISIBLE);

        checkBike.setVisibility(View.GONE);
        checkBadminton.setVisibility(View.GONE);
        checkBasket.setVisibility(View.GONE);
        checkRun.setVisibility(View.GONE);
        checkPanahan.setVisibility(View.GONE);
        checkBola.setVisibility(View.GONE);
        checkTenisMeja.setVisibility(View.GONE);

        kategoriSport.setText("Bola Voli");

        btnKonfirmasi.setOnClickListener(v -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ChooseSportsActivity.this);
            alertDialogBuilder.setTitle("Konfirmasi Lomba");
            alertDialogBuilder
                    .setMessage("Anda Yakin Ingin Mengikuti Bola Voli?")
                    .setIcon(R.drawable.volleyball)
                    .setCancelable(false)
                    .setPositiveButton("Ya", (dialog, which) -> {
                        progressBarSport.setVisibility(View.VISIBLE);
                        kirimDataVoli();
                    })
                    .setNegativeButton("Tidak", (dialog, which) -> {
                        progressBarSport.setVisibility(View.GONE);
                        dialog.cancel();
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        });
    }

    private void kirimDataVoli() {
        Peserta peserta = Preferences.getPeserta(getApplicationContext());
        AndroidNetworking.post("https://onekmievents.000webhostapp.com/api/tambahDataVoli.php")
                .addBodyParameter("varcharEmployeeID", "" + peserta.getVarcharEmployeeID())
                .addBodyParameter("txtEmployeeName", "" + peserta.getTxtEmployeeName())
                .setPriority(Priority.MEDIUM)
                .setTag("Tambah Data")
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
                                Toast.makeText(ChooseSportsActivity.this, "Berhasil Ditambahkan Ke Pertandingan", Toast.LENGTH_SHORT).show();
                                progressBarSport.setVisibility(View.GONE);
                                Intent i = getIntent();
                                setResult(RESULT_OK, i);
                                startActivity(new Intent(ChooseSportsActivity.this, VirtualBolaVoli.class));
                                finish();
                            }else if (pesan.equals("Gagal, Data Sudah Ada")){
                                Toast.makeText(ChooseSportsActivity.this, "Anda Sedang Mengikuti Lomba Ini", Toast.LENGTH_SHORT).show();
                                progressBarSport.setVisibility(View.GONE);
                                Intent i = getIntent();
                                setResult(RESULT_CANCELED, i);
                                startActivity(new Intent(ChooseSportsActivity.this, VirtualBolaVoli.class));
                                finish();
                            }else {
                                Toast.makeText(ChooseSportsActivity.this, "Gagal Konfirmasi Lomba", Toast.LENGTH_SHORT).show();
                                progressBarSport.setVisibility(View.GONE);
                                Intent i = getIntent();
                                setResult(RESULT_CANCELED, i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("Error Tambah Data", "" + anError.getErrorBody());
                    }
                });
    }
    //Kirim Data Bola Voli

    //Kirim Data BOla Basket
    private void setBolaBasket() {
        checkBasket.setVisibility(View.VISIBLE);

        checkBike.setVisibility(View.GONE);
        checkBadminton.setVisibility(View.GONE);
        checkVoli.setVisibility(View.GONE);
        checkRun.setVisibility(View.GONE);
        checkPanahan.setVisibility(View.GONE);
        checkBola.setVisibility(View.GONE);
        checkTenisMeja.setVisibility(View.GONE);

        kategoriSport.setText("Bola Basket");

        btnKonfirmasi.setOnClickListener(v -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ChooseSportsActivity.this);
            alertDialogBuilder.setTitle("Konfirmasi Lomba");
            alertDialogBuilder
                    .setMessage("Anda Yakin Ingin Mengikuti Bola Basket?")
                    .setIcon(R.drawable.basketball)
                    .setCancelable(false)
                    .setPositiveButton("Ya", (dialog, which) -> {
                        progressBarSport.setVisibility(View.VISIBLE);
                        kirimDataBasket();
                    })
                    .setNegativeButton("Tidak", (dialog, which) -> {
                        progressBarSport.setVisibility(View.GONE);
                        dialog.cancel();
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        });
    }

    private void kirimDataBasket() {
        Peserta peserta = Preferences.getPeserta(getApplicationContext());
        AndroidNetworking.post("https://onekmievents.000webhostapp.com/api/tambahDataBasket.php")
                .addBodyParameter("varcharEmployeeID", "" + peserta.getVarcharEmployeeID())
                .addBodyParameter("txtEmployeeName", "" + peserta.getTxtEmployeeName())
                .setPriority(Priority.MEDIUM)
                .setTag("Tambah Data")
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
                                Toast.makeText(ChooseSportsActivity.this, "Berhasil Ditambahkan Ke Pertandingan", Toast.LENGTH_SHORT).show();
                                progressBarSport.setVisibility(View.GONE);
                                Intent i = getIntent();
                                setResult(RESULT_OK, i);
                                startActivity(new Intent(ChooseSportsActivity.this, VirtualBasketActivity.class));
                                finish();
                            }else if (pesan.equals("Gagal, Data Sudah Ada")){
                                Toast.makeText(ChooseSportsActivity.this, "Anda Sedang Mengikuti Lomba Ini", Toast.LENGTH_SHORT).show();
                                progressBarSport.setVisibility(View.GONE);
                                Intent i = getIntent();
                                setResult(RESULT_CANCELED, i);
                                startActivity(new Intent(ChooseSportsActivity.this, VirtualBasketActivity.class));
                                finish();
                            }else {
                                Toast.makeText(ChooseSportsActivity.this, "Gagal Konfirmasi Lomba", Toast.LENGTH_SHORT).show();
                                progressBarSport.setVisibility(View.GONE);
                                Intent i = getIntent();
                                setResult(RESULT_CANCELED, i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("Error Tambah Data", "" + anError.getErrorBody());
                    }
                });
    }
    //Kirim Data BOla Basket

    //Kirim Data Badminton
    private void setBadminton() {
        checkBadminton.setVisibility(View.VISIBLE);

        checkBike.setVisibility(View.GONE);
        checkBasket.setVisibility(View.GONE);
        checkVoli.setVisibility(View.GONE);
        checkRun.setVisibility(View.GONE);
        checkPanahan.setVisibility(View.GONE);
        checkBola.setVisibility(View.GONE);
        checkTenisMeja.setVisibility(View.GONE);

        kategoriSport.setText("Badminton");

        btnKonfirmasi.setOnClickListener(v -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ChooseSportsActivity.this);
            alertDialogBuilder.setTitle("Konfirmasi Lomba");
            alertDialogBuilder
                    .setMessage("Anda Yakin Ingin Mengikuti Badminton?")
                    .setIcon(R.drawable.badminton)
                    .setCancelable(false)
                    .setPositiveButton("Ya", (dialog, which) -> {
                        progressBarSport.setVisibility(View.VISIBLE);
                        kirimDataBadminton();
                    })
                    .setNegativeButton("Tidak", (dialog, which) -> {
                        progressBarSport.setVisibility(View.GONE);
                        dialog.cancel();
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        });
    }

    private void kirimDataBadminton() {
        Peserta peserta = Preferences.getPeserta(getApplicationContext());
        AndroidNetworking.post("https://onekmievents.000webhostapp.com/api/tambahDataBadminton.php")
                .addBodyParameter("varcharEmployeeID", "" + peserta.getVarcharEmployeeID())
                .addBodyParameter("txtEmployeeName", "" + peserta.getTxtEmployeeName())
                .setPriority(Priority.MEDIUM)
                .setTag("Tambah Data")
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
                                Toast.makeText(ChooseSportsActivity.this, "Berhasil Ditambahkan Ke Pertandingan", Toast.LENGTH_SHORT).show();
                                progressBarSport.setVisibility(View.GONE);
                                Intent i = getIntent();
                                setResult(RESULT_OK, i);
                                startActivity(new Intent(ChooseSportsActivity.this, VirtualBadmintonActivity.class));
                                finish();
                            }else if (pesan.equals("Gagal, Data Sudah Ada")){
                                Toast.makeText(ChooseSportsActivity.this, "Anda Sedang Mengikuti Lomba Ini", Toast.LENGTH_SHORT).show();
                                progressBarSport.setVisibility(View.GONE);
                                Intent i = getIntent();
                                setResult(RESULT_CANCELED, i);
                                startActivity(new Intent(ChooseSportsActivity.this, VirtualBadmintonActivity.class));
                                finish();
                            }else {
                                Toast.makeText(ChooseSportsActivity.this, "Gagal Konfirmasi Lomba", Toast.LENGTH_SHORT).show();
                                progressBarSport.setVisibility(View.GONE);
                                Intent i = getIntent();
                                setResult(RESULT_CANCELED, i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("Error Tambah Data", "" + anError.getErrorBody());
                    }
                });
    }
    //Kirim Data Badminton

    //Kirim Data Panahan
    private void setPanahan() {
        checkPanahan.setVisibility(View.VISIBLE);

        checkBike.setVisibility(View.GONE);
        checkBasket.setVisibility(View.GONE);
        checkVoli.setVisibility(View.GONE);
        checkRun.setVisibility(View.GONE);
        checkBadminton.setVisibility(View.GONE);
        checkBola.setVisibility(View.GONE);
        checkTenisMeja.setVisibility(View.GONE);

        kategoriSport.setText("Panahan");

        btnKonfirmasi.setOnClickListener(v -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ChooseSportsActivity.this);
            alertDialogBuilder.setTitle("Konfirmasi Lomba");
            alertDialogBuilder
                    .setMessage("Anda Yakin Ingin Mengikuti Panahan?")
                    .setIcon(R.drawable.panahan)
                    .setCancelable(false)
                    .setPositiveButton("Ya", (dialog, which) -> {
                        progressBarSport.setVisibility(View.VISIBLE);
                        kirimDataPanahan();
                    })
                    .setNegativeButton("Tidak", (dialog, which) -> {
                        progressBarSport.setVisibility(View.GONE);
                        dialog.cancel();
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        });
    }

    private void kirimDataPanahan() {
        Peserta peserta = Preferences.getPeserta(getApplicationContext());
        AndroidNetworking.post("https://onekmievents.000webhostapp.com/api/tambahDataPanahan.php")
                .addBodyParameter("varcharEmployeeID", "" + peserta.getVarcharEmployeeID())
                .addBodyParameter("txtEmployeeName", "" + peserta.getTxtEmployeeName())
                .setPriority(Priority.MEDIUM)
                .setTag("Tambah Data")
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
                                Toast.makeText(ChooseSportsActivity.this, "Berhasil Ditambahkan Ke Pertandingan", Toast.LENGTH_SHORT).show();
                                progressBarSport.setVisibility(View.GONE);
                                Intent i = getIntent();
                                setResult(RESULT_OK, i);
                                startActivity(new Intent(ChooseSportsActivity.this, VirtualPanahanActivity.class));
                                finish();
                            }else if (pesan.equals("Gagal, Data Sudah Ada")){
                                Toast.makeText(ChooseSportsActivity.this, "Anda Sedang Mengikuti Lomba Ini", Toast.LENGTH_SHORT).show();
                                progressBarSport.setVisibility(View.GONE);
                                Intent i = getIntent();
                                setResult(RESULT_CANCELED, i);
                                startActivity(new Intent(ChooseSportsActivity.this, VirtualPanahanActivity.class));
                                finish();
                            }else {
                                Toast.makeText(ChooseSportsActivity.this, "Gagal Konfirmasi Lomba", Toast.LENGTH_SHORT).show();
                                progressBarSport.setVisibility(View.GONE);
                                Intent i = getIntent();
                                setResult(RESULT_CANCELED, i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("Error Tambah Data", "" + anError.getErrorBody());
                    }
                });
    }
    //Kirim Data Panahan
}
