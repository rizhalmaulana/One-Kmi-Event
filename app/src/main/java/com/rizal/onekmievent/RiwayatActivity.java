package com.rizal.onekmievent;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RiwayatActivity extends AppCompatActivity {

    @BindView(R.id.backRiwayat)
    ImageView backRiwayat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);
        ButterKnife.bind(this);

        backRiwayat.setOnClickListener(v -> finish());
    }

}
