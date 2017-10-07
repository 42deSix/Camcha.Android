package layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softmilktea.camcha.R;

/**
 * Created by SEJIN on 2017-10-03.
 */

public class ReportFragment extends Fragment {
    private static final String TAG = "RootFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		/* Inflate the layout for this fragment */
        View view = inflater.inflate(R.layout.view_report, container, false);
        return view;
    }
}
