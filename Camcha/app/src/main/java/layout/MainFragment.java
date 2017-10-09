package layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softmilktea.camcha.R;

/**
 * Created by SEJIN on 2017-10-05.
 */

public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		/* Inflate the layout for this fragment */
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        AppCompatButton detectionButton = (AppCompatButton) view.findViewById(R.id.detection_button);
        AppCompatButton showMapButton = (AppCompatButton) view.findViewById(R.id.show_map_button);
        detectionButton.setOnClickListener(
                new AppCompatButton.OnClickListener() {
                    public void onClick(View v) {
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.root_view, new GpsPlzFragment());
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }
        );
        showMapButton.setOnClickListener(
                new AppCompatButton.OnClickListener() {
                    public void onClick(View v) {
//                        Log.e(TAG, "Show me the map");
////                        startActivity(new Intent(MainFragment.super.getContext(), ShowMapActivity.class));
//                        if(transaction != null) {
//                            transaction.replace(R.id.root_view, new ShowMapFragment());
//                            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                            transaction.addToBackStack(null);
//                            transaction.commit();
//                        }
                    }
                }
        );
        return view;
    }
}
