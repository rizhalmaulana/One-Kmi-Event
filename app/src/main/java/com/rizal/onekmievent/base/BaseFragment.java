package com.rizal.onekmievent.base;

import com.rizal.onekmievent.utils.DataSource;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {
    public DataSource getDataSources() {
        return new DataSource();
    }
}
