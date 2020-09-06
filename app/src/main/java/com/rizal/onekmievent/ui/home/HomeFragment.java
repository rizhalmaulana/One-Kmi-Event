package com.rizal.onekmievent.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rizal.onekmievent.R;
import com.rizal.onekmievent.RiwayatActivity;
import com.rizal.onekmievent.SelfCheckActivity;
import com.rizal.onekmievent.choose.ChooseEsportsActivity;
import com.rizal.onekmievent.choose.ChooseEntertainmentActivity;
import com.rizal.onekmievent.choose.ChooseSportsActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    @BindView(R.id.cvViewVirus)
    CardView selfAssesment;
    @BindView(R.id.virtualSports)
    LinearLayout virtualSports;
    @BindView(R.id.esport)
    LinearLayout virtualEsports;
    @BindView(R.id.virtualEntertainment)
    LinearLayout virtualEntertainment;
    @BindView(R.id.cvViewHistory)
    CardView viewHistory;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selfAssesment.setOnClickListener(v -> startActivity(new Intent(getActivity(), SelfCheckActivity.class)));

        virtualSports.setOnClickListener(v -> startActivity(new Intent(getActivity(), ChooseSportsActivity.class)));

        virtualEsports.setOnClickListener(v -> startActivity(new Intent(getActivity(), ChooseEsportsActivity.class)));

        virtualEntertainment.setOnClickListener(v -> startActivity(new Intent(getActivity(), ChooseEntertainmentActivity.class)));

        viewHistory.setOnClickListener(v -> startActivity(new Intent(getActivity(), RiwayatActivity.class)));
    }
}
