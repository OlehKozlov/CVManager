package bigheadsman.cvmanager;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class ExperienceFragment extends Fragment {
    private String start;
    private String end;
    private String name;
    private int expId;
    DatabaseHelper dbHealper;
    LinearLayout llt;
    private static ArrayList<Integer> itemList = new ArrayList<Integer>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.experience_fragment, container, false);
        dbHealper = new DatabaseHelper(view.getContext()); //create a database object
        SQLiteDatabase db = dbHealper.getReadableDatabase(); // get database to reading
        Cursor c = db.query("experienceTable", null, null, null, null, null, null);
        llt = (LinearLayout) view.findViewById(R.id.experienceContainer);
        LinearLayout.LayoutParams lEditParams = new LinearLayout.LayoutParams(300, LinearLayout.LayoutParams.WRAP_CONTENT);
        lEditParams.topMargin = 5;
        while (c.moveToNext()) {
            int startColIndex = c.getColumnIndex("start");
            int endColIndex = c.getColumnIndex("end");
            int nameColIndex = c.getColumnIndex("name");
            int idColIndex = c.getColumnIndex("id");
            final EditText editTxt = new EditText(view.getContext());
            editTxt.setLayoutParams(lEditParams);
            editTxt.setFocusable(false);
            editTxt.setClickable(false);
            editTxt.setId(c.getInt(idColIndex));
            String str = c.getString(startColIndex) +
                    " - " + c.getString(endColIndex) + "\n" + c.getString(nameColIndex);
            editTxt.setText(str);
            editTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editTxt.setBackgroundColor(Color.WHITE);
                    expId = editTxt.getId();
                    if (itemList.contains(expId)) {
                        for (int i = 0; i < itemList.size(); i++) {
                            if (itemList.get(i).equals(expId)) {
                                itemList.remove(i);
                            }
                        }
                    } else {
                        editTxt.setFocusable(true);
                        //   editTxt.setBackgroundResource(R.color.backgroundButtonF);
                        itemList.add(expId);
                    }
                }
            });
            llt.addView(editTxt);
        }
        return view;
    }

    public ArrayList<Integer> getItemList() {
        return itemList;
    }
}
