package bigheadsman.cvmanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;


public class EducationFragment extends Fragment {
    private int eduId;
    DatabaseHelper dbHealper;
    LinearLayout llt;
    private static ArrayList<Integer> itemList = new ArrayList<Integer>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.education_fragment, container, false);
        Spinner spinner = (Spinner) view.findViewById(R.id.spinnerTypeOfEdu);
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(view.getContext(), R.array.educationItem, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        dbHealper = new DatabaseHelper(view.getContext()); //create a database object
        SQLiteDatabase db = dbHealper.getReadableDatabase(); // get database to reading
        Cursor c = db.query("educationTable", null, null, null, null, null, null);
        llt = (LinearLayout) view.findViewById(R.id.educationContainer);
        LinearLayout.LayoutParams lEditParams = new LinearLayout.LayoutParams(300, LinearLayout.LayoutParams.WRAP_CONTENT);
        lEditParams.topMargin = 5;
        while (c.moveToNext()) {
            int typeColIndex = c.getColumnIndex("type");
            int startColIndex = c.getColumnIndex("start");
            int endColIndex = c.getColumnIndex("end");
            int nameColIndex = c.getColumnIndex("name");
            int idColIndex = c.getColumnIndex("id");
            final EditText editTxt = new EditText(view.getContext());
            editTxt.setLayoutParams(lEditParams);
            editTxt.setFocusable(false);
            editTxt.setClickable(false);
            editTxt.setId(c.getInt(idColIndex));
            String str = c.getString(typeColIndex) + "\n" + c.getString(startColIndex) +
                    " - " + c.getString(endColIndex) + "\n" + c.getString(nameColIndex);
            editTxt.setText(str);
            editTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editTxt.setBackgroundColor(Color.WHITE);
                    eduId = editTxt.getId();
                    if (itemList.contains(eduId)) {
                        for (int i = 0; i < itemList.size(); i++) {
                            if (itemList.get(i).equals(eduId)) {
                                itemList.remove(i);
                            }
                        }
                    } else {
                        editTxt.setFocusable(true);
                        //  editTxt.setBackgroundResource(R.color.backgroundButtonF);
                        itemList.add(eduId);
                    }
                }
            });
            llt.addView(editTxt);
        }
        db.close();
        return view;
    }

    public ArrayList<Integer> getItemList() {
        return itemList;
    }

}
