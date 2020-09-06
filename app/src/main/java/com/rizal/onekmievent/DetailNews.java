package com.rizal.onekmievent;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.rizal.onekmievent.utils.Constant;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;

public class DetailNews extends AppCompatActivity {
    String title, desc, content, img;
    public static final String EXTRA_NEWS = "extra_news";

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);

        setup();
    }

    private void setup() {
        ImageView myphoto = findViewById(R.id.imgdetailberita);
        TextView mytitle = findViewById(R.id.txtdetailtitle);
        TextView mydesc = findViewById(R.id.txtdetaildesc);
        TextView mycontent = findViewById(R.id.txtdetailcontent);

        Intent myintent = getIntent();
        img = myintent.getStringExtra(Constant.KEY_GAMBAR_NEWS);
        title = myintent.getStringExtra(Constant.KEY_NAMA_NEWS);
        desc = myintent.getStringExtra(Constant.KEY_DESC_NEWS);
        content = myintent.getStringExtra(Constant.KEY_CONTENT_NEWS);

        Picasso.get().load(img).into(myphoto);
        mytitle.setText(title);
        mydesc.setText(desc);
        mycontent.setText(content);
    }
}
