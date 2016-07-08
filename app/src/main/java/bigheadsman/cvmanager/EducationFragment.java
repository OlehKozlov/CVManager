package bigheadsman.cvmanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;


public class EducationFragment extends Fragment {
    private static ArrayList<Integer> itemList = new ArrayList<Integer>();
    DatabaseHelper dbHealper;
    LinearLayout llt;
    private int eduId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.education_fragment, container, false);
        Spinner spinner = (Spinner) view.findViewById(R.id.spinnerTypeOfEdu);
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(view.getContext(), R.array.educationItem, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        dbHealper = new DatabaseHelper(view.getContext());
        SQLiteDatabase db = dbHealper.getReadableDatabase();
        Cursor c = db.query("educationTable", null, null, null, null, null, null);
        llt = (LinearLayout) view.findViewById(R.id.educationContainer);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 5, 0, 0);
        while (c.moveToNext()) {
            int typeColIndex = c.getColumnIndex("type");
            int startColIndex = c.getColumnIndex("start");
            int endColIndex = c.getColumnIndex("end");
            int nameColIndex = c.getColumnIndex("name");
            int idColIndex = c.getColumnIndex("id");
            final Button button = new Button(view.getContext());
            button.setBackgroundResource(R.drawable.button_style);
            button.setLayoutParams(params);
            button.setId(c.getInt(idColIndex));
            String str = c.getString(typeColIndex) + "\n" + c.getString(startColIndex) +
                    " - " + c.getString(endColIndex) + "\n" + c.getString(nameColIndex);
            button.setText(str);
            Drawable icon = getContext().getResources().getDrawable(R.drawable.item_unselected);
            button.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eduId = button.getId();
                    if (itemList.contains(eduId)) {
                        for (int i = 0; i < itemList.size(); i++) {
                            if (itemList.get(i).equals(eduId)) {
                                Drawable icon = getContext().getResources().getDrawable(R.drawable.item_unselected);
                                button.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
                                itemList.remove(i);
                            }
                        }
                    } else {
                        Drawable icon = getContext().getResources().getDrawable(R.drawable.item_selected);
                        button.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
                        itemList.add(eduId);
                    }
                }
            });
            llt.addView(button);
        }
        db.close();
        return view;
    }

    public ArrayList<Integer> getItemList() {
        return itemList;
    }

}
