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

        BaseApplication.ROOT_FRAGMENT_TRANSACTION = getFragmentManager().beginTransaction();
        BaseApplication.ROOT_FRAGMENT_TRANSACTION.add(R.id.root_view, new MainFragment());
        BaseApplication.ROOT_FRAGMENT_TRANSACTION.commit();

        return view;
    }

    /**
     * Replaces 'root_view' of main page in RootFragment with other views
     * @author Sejin Jeon
     * @param newFragment
     */
    public void replaceRootView(Fragment newFragment) {
        FragmentManager fm = getFragmentManager();
        BaseApplication.ROOT_FRAGMENT_TRANSACTION = fm.beginTransaction();
        BaseApplication.ROOT_FRAGMENT_TRANSACTION.replace(R.id.root_view, newFragment);
        BaseApplication.ROOT_FRAGMENT_TRANSACTION.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        BaseApplication.ROOT_FRAGMENT_TRANSACTION.addToBackStack(null);
        if(fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        }
        BaseApplication.ROOT_FRAGMENT_TRANSACTION.commit();
    }
}
