package layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softmilktea.camcha.Dlog;
import com.softmilktea.camcha.R;

/**
 * Created by SEJIN on 2017-10-22.
 */

public class DecideWhereToReportFragment extends Fragment {
    private int mSwitch = 0;    /* 1: camcha, 2: call, 3: message */

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_decide_where_to_report, container, false);
        mSwitch = getArguments().getInt("switch");
        return view;
    }
}
