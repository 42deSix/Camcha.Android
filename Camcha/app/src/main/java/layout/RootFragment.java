package layout;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softmilktea.camcha.BaseApplication;
import com.softmilktea.camcha.R;

/**
 * Created by SEJIN on 2017-10-07.
 */

public class RootFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		/* Inflate the layout for this fragment */
        View view = inflater.inflate(R.layout.fragment_root, container, false);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.root_view, new MainFragment());
        transaction.commit();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(BaseApplication.GO_TO_REPORT_FRAGMENT) {
            replaceRootView(new ReportFragment());
            BaseApplication.GO_TO_REPORT_FRAGMENT = false;
        }
    }

    /**
     * Replaces 'root_view' of main page in RootFragment with other views
     * @author Sejin Jeon
     * @param newFragment
     */
    public void replaceRootView(Fragment newFragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.root_view, newFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        if(fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        }
        transaction.commit();
    }
}
