package bigheadsman.cvmanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;


public class NewCVFragment extends Fragment {
    DatabaseHelper dbHealper;

    private static String cvTitle;
    private ArrayList<String> personalItems = new ArrayList<String>();
    private ArrayList<String> educationItems = new ArrayList<String>();
    private ArrayList<String> skillsItems = new ArrayList<String>();
    private ArrayList<String> experienceItems = new ArrayList<String>();
    private ArrayList<String> languagesItems = new ArrayList<String>();
    private ArrayList<String> aboutMyselfItems = new ArrayList<String>();
    private ArrayList<String> coverLettersItems = new ArrayList<String>();
    private LinearLayout personalView;
    private LinearLayout educationView;
    private LinearLayout skillsView;
    private LinearLayout experienceView;
    private LinearLayout languagesView;
    private LinearLayout aboutMyselfView;
    private LinearLayout coverLettersView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_cv_fragment, container, false);
        personalView = (LinearLayout) view.findViewById(R.id.newCVPersonalContainer);
        educationView = (LinearLayout) view.findViewById(R.id.newCVEducationContainer);
        skillsView = (LinearLayout) view.findViewById(R.id.newCVSkillsContainer);
        experienceView = (LinearLayout) view.findViewById(R.id.newCVExperienceContainer);
        languagesView = (LinearLayout) view.findViewById(R.id.newCVLanguagesContainer);
        aboutMyselfView = (LinearLayout) view.findViewById(R.id.newCVAboutMyselfContainer);
        coverLettersView = (LinearLayout) view.findViewById(R.id.newCVCoverLetterContainer);

        setPersonalData(view);
        setEducationData(view);
        setSkillsData(view);
        setExperienceData(view);
        setLanguagesData(view);
        setAboutMyselfData(view);
        setCoverLettersData(view);
        return view;
    }

    private void setPersonalData(View view) {
        dbHealper = new DatabaseHelper(view.getContext()); //create a database object
        SQLiteDatabase db = dbHealper.getReadableDatabase(); // get database to reading
        Cursor c = db.query("personalTable", null, null, null, null, null, null);
        while (c.moveToNext()) {
            int fotoColIndex = c.getColumnIndex("foto");
            int nameColIndex = c.getColumnIndex("name");
            int ageColIndex = c.getColumnIndex("age");
            int phoneColIndex = c.getColumnIndex("phone");
            int emailColIndex = c.getColumnIndex("email");
            int addressColIndex = c.getColumnIndex("address");
            int sexColIndex = c.getColumnIndex("sex");
            personalItems.add(c.getString(fotoColIndex));
            personalItems.add(c.getString(nameColIndex));
            personalItems.add(c.getString(ageColIndex));
            personalItems.add(c.getString(sexColIndex));
            personalItems.add(c.getString(phoneColIndex));
            personalItems.add(c.getString(emailColIndex));
            personalItems.add(c.getString(addressColIndex));
        }
        while (personalItems.contains("")) {
            personalItems.remove("");
        }
        addViews(personalItems, personalView, view);
        c.close();
        db.close();
    }

    private void setEducationData(View view) {
        dbHealper = new DatabaseHelper(view.getContext());
        SQLiteDatabase db = dbHealper.getReadableDatabase();
        Cursor c = db.query("educationTable", null, null, null, null, null, null);
        while (c.moveToNext()) {
            int typeColIndex = c.getColumnIndex("type");
            int startColIndex = c.getColumnIndex("start");
            int endColIndex = c.getColumnIndex("end");
            int nameColIndex = c.getColumnIndex("name");
            educationItems.add(c.getString(typeColIndex) + ": " + c.getString(startColIndex) +
                    " - " + c.getString(endColIndex) + "\n" + c.getString(nameColIndex));
        }
        addViews(educationItems, educationView, view);
        c.close();
        db.close();
    }

    private void setSkillsData(View view) {
        dbHealper = new DatabaseHelper(view.getContext());
        SQLiteDatabase db = dbHealper.getReadableDatabase();
        Cursor c = db.query("scillsTable", null, null, null, null, null, null);
        while (c.moveToNext()) {
            int nameColIndex = c.getColumnIndex("name");
            skillsItems.add(c.getString(nameColIndex));
        }
        addViews(skillsItems, skillsView, view);
        c.close();
        db.close();
    }

    private void setExperienceData(View view) {
        dbHealper = new DatabaseHelper(view.getContext());
        SQLiteDatabase db = dbHealper.getReadableDatabase();
        Cursor c = db.query("experienceTable", null, null, null, null, null, null);
        while (c.moveToNext()) {
            int startColIndex = c.getColumnIndex("start");
            int endColIndex = c.getColumnIndex("end");
            int nameColIndex = c.getColumnIndex("name");
            experienceItems.add(c.getString(startColIndex) +
                    " - " + c.getString(endColIndex) + "\n" + c.getString(nameColIndex));
        }
        addViews(experienceItems, experienceView, view);
        c.close();
        db.close();
    }

    private void setLanguagesData(View view) {
        dbHealper = new DatabaseHelper(view.getContext());
        SQLiteDatabase db = dbHealper.getReadableDatabase();
        Cursor c = db.query("languagesTable", null, null, null, null, null, null);
        while (c.moveToNext()) {
            int nameColIndex = c.getColumnIndex("name");
            int levelColIndex = c.getColumnIndex("level");
            languagesItems.add(c.getString(nameColIndex) + " - " + c.getString(levelColIndex));
        }
        addViews(languagesItems, languagesView, view);
        c.close();
        db.close();
    }

    private void setAboutMyselfData(View view) {
        dbHealper = new DatabaseHelper(view.getContext());
        SQLiteDatabase db = dbHealper.getReadableDatabase();
        Cursor c = db.query("aboutMyselfTable", null, null, null, null, null, null);
        while (c.moveToNext()) {
            int nameColIndex = c.getColumnIndex("name");
            aboutMyselfItems.add(c.getString(nameColIndex));
        }
        addViews(aboutMyselfItems, aboutMyselfView, view);
        c.close();
        db.close();
    }

    private void setCoverLettersData(View view) {
        dbHealper = new DatabaseHelper(view.getContext());
        SQLiteDatabase db = dbHealper.getReadableDatabase();
        Cursor c = db.query("lettersTable", null, null, null, null, null, null);
        while (c.moveToNext()) {
            int nameColIndex = c.getColumnIndex("name");
            coverLettersItems.add(c.getString(nameColIndex));
        }
        addViews(coverLettersItems, coverLettersView, view);
        c.close();
        db.close();
    }

    private void addViews(ArrayList list, LinearLayout layout, View view) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 5, 0, 0);
        for (int i = 0; i < list.size(); i++) {
            Button button = new Button(view.getContext());
            button.setBackgroundResource(R.drawable.button_style);
            button.setLayoutParams(params);
            button.setText(list.get(i).toString());
            layout.addView(button);
        }
    }


}
