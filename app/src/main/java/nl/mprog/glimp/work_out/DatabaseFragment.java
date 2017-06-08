package nl.mprog.glimp.work_out;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Gido Limperg on 8-6-2017.
 */

public class DatabaseFragment extends Fragment {
    private static final String TAG = "DatabaseFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.database_fragment, container, false);
    }
}
