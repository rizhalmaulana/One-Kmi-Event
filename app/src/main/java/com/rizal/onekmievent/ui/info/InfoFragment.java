package com.rizal.onekmievent.ui.info;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rizal.onekmievent.MencegahActivity;
import com.rizal.onekmievent.MengantisipasiActivity;
import com.rizal.onekmievent.MengenalActivity;
import com.rizal.onekmievent.MengobatiActivity;
import com.rizal.onekmievent.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class InfoFragment extends Fragment {
    @BindView(R.id.mengenal)
    LinearLayout layoutMengenal;
    @BindView(R.id.mencegah)
    LinearLayout layoutMencegah;
    @BindView(R.id.mengantisipasi)
    LinearLayout layoutMengantisipasi;
    @BindView(R.id.mengobati)
    LinearLayout layoutMengobati;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_info, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutMengenal.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), MengenalActivity.class));
        });

        layoutMencegah.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), MencegahActivity.class));
        });

        layoutMengantisipasi.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), MengantisipasiActivity.class));
        });

        layoutMengobati.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), MengobatiActivity.class));
        });
    }
}
