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
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class AboutMyselfFragment extends Fragment {
    private static ArrayList<Integer> itemList = new ArrayList<Integer>();
    DatabaseHelper dbHealper;
    LinearLayout llt;
    private int myselfId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_myself_fragment, container, false);
        dbHealper = new DatabaseHelper(view.getContext());
        SQLiteDatabase db = dbHealper.getReadableDatabase();
        Cursor c = db.query("aboutMyselfTable", null, null, null, null, null, null);
        llt = (LinearLayout) view.findViewById(R.id.aboutMyselfContainer);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 5, 0, 0);
        while (c.moveToNext()) {
            int nameColIndex = c.getColumnIndex("name");
            int idColIndex = c.getColumnIndex("id");
            final Button button = new Button(view.getContext());
            button.setBackgroundResource(R.drawable.button_style);
            button.setLayoutParams(params);
            button.setId(c.getInt(idColIndex));
            String str = c.getString(nameColIndex);
            button.setText(str);
            Drawable icon = getContext().getResources().getDrawable(R.drawable.item_unselected);
            button.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myselfId = button.getId();
                    if (itemList.contains(myselfId)) {
                        for (int i = 0; i < itemList.size(); i++) {
                            if (itemList.get(i).equals(myselfId)) {
                                Drawable icon = getContext().getResources().getDrawable(R.drawable.item_unselected);
                                button.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
                                itemList.remove(i);
                            }
                        }
                    } else {
                        Drawable icon = getContext().getResources().getDrawable(R.drawable.item_selected);
                        button.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
                        itemList.add(myselfId);
                    }
                }
            });
            llt.addView(button);
        }
        return view;
    }

    public ArrayList<Integer> getItemList() {
        return itemList;
    }
}
