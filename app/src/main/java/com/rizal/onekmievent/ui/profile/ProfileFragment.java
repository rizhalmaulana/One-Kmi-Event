package com.rizal.onekmievent.ui.profile;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rizal.onekmievent.LoginActivity;
import com.rizal.onekmievent.R;
import com.rizal.onekmievent.client.MobileService;
import com.rizal.onekmievent.model.Peserta;
import com.rizal.onekmievent.utils.Preferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends Fragment {

    @BindView(R.id.textNamaPeserta)
    TextView namaPeserta;
    @BindView(R.id.textIdPeserta)
    TextView idPeserta;
    @BindView(R.id.textPosisiPeserta)
    TextView posisiPeserta;
    @BindView(R.id.textGrupPeserta)
    TextView grupPeserta;

    @BindView(R.id.lKeluar)
    LinearLayout btnKeluar;
    @BindView(R.id.lnamaPeserta)
    LinearLayout lPeserta;
    @BindView(R.id.lIdPeserta)
    LinearLayout lIdPeserta;
    @BindView(R.id.lGrupPeserta)
    LinearLayout lGrupPeserta;
    @BindView(R.id.lPosisiPeserta)
    LinearLayout lPosisiPeserta;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Peserta peserta = Preferences.getPeserta(getActivity());
        namaPeserta.setText(peserta.getTxtEmployeeName());
        idPeserta.setText(peserta.getVarcharEmployeeID());
        posisiPeserta.setText(peserta.getTxtJobTitle());
        grupPeserta.setText(peserta.getTxtGroupName());

        btnKeluar.setOnClickListener(v -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

            alertDialogBuilder.setTitle("Yakin Anda Ingin Keluar?");
            alertDialogBuilder
                    .setIcon(R.drawable.logout)
                    .setCancelable(false)
                    .setPositiveButton("Ya", (dialog, which) -> {
                        Preferences.setPeserta(getActivity(), null);
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    })
                    .setNegativeButton("Tidak", (dialog, which) -> dialog.cancel());

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        });

        lPeserta.setOnClickListener(v -> Toast.makeText(getActivity(), "Nama Anda : "+ peserta.getTxtEmployeeName(), Toast.LENGTH_SHORT).show());
        lIdPeserta.setOnClickListener(v -> Toast.makeText(getActivity(), "ID Anda : "+ peserta.getVarcharEmployeeID(), Toast.LENGTH_SHORT).show());
        lPosisiPeserta.setOnClickListener(v -> Toast.makeText(getActivity(), "Jabatan Anda : "+ peserta.getTxtJobTitle(), Toast.LENGTH_SHORT).show());
        lGrupPeserta.setOnClickListener(v -> Toast.makeText(getActivity(), "Grup Anda : "+ peserta.getTxtGroupName(), Toast.LENGTH_SHORT).show());
    }
}
