package bigheadsman.cvmanager;


import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;

public class MainFragment extends Fragment {
    GridView gridView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        gridView = (GridView) view.findViewById(R.id.mainContainer);
        File dirFiles = new File(Environment.getExternalStorageDirectory() + "/" + "CV Manager");
        if (dirFiles.isDirectory()) {
            final String[] filesList = dirFiles.list();
            String[] filesNameList = new String[filesList.length + 1];
            filesNameList[0] = "New";
            for (int i = 0; i < filesList.length; i++) {
                filesNameList[i + 1] = filesList[i].substring(0, filesList[i].length() - 4);
            }
            MainFragmentArrayAdapter adapter = new MainFragmentArrayAdapter(view.getContext(), filesNameList);
            gridView.setAdapter(adapter);
        }
        return view;
    }
}
