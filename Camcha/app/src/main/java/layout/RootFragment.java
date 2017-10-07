package layout;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softmilktea.camcha.R;

/**
 * Created by SEJIN on 2017-10-07.
 */

public class RootFragment extends Fragment{
    private static final String TAG = "RootFragment";
    private static FragmentTransaction transaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		/* Inflate the layout for this fragment */
        View view = inflater.inflate(R.layout.fragment_root, container, false);

        transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.root_view, new MainFragment());
        transaction.commit();

        return view;
    }

    public void replaceRootView(Fragment newFragment) {
        transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.root_view, newFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
