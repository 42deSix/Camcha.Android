package layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.softmilktea.camcha.BaseApplication;
import com.softmilktea.camcha.Dlog;
import com.softmilktea.camcha.R;

/**
 * Created by SEJIN on 2017-10-03.
 */

public class ReportFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_report, container, false);

        LinearLayout reportByCamcha = (LinearLayout) view.findViewById(R.id.report_by_camcha);
        LinearLayout reportByCall = (LinearLayout) view.findViewById(R.id.report_by_call);
        LinearLayout reportByMessage = (LinearLayout) view.findViewById(R.id.report_by_message);

        reportByCamcha.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new DecideWhereToReportFragment(), 1);
            }
        });
        reportByCall.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new DecideWhereToReportFragment(), 2);
            }
        });
        reportByMessage.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new DecideWhereToReportFragment(), 3);
            }
        });

        return view;
    }

    public void replaceFragment(Fragment newFragment, int _switch) {
        Bundle bundle = new Bundle();
        bundle.putInt("switch", _switch);
        newFragment.setArguments(bundle);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.root_view, newFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
