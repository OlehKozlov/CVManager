package bigheadsman.cvmanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class NewCVFragment extends Fragment {
    private static ArrayList<String> personalSelectedItems = new ArrayList<String>();
    private static ArrayList<String> educationSelectedItems = new ArrayList<String>();
    private static ArrayList<String> skillsSelectedItems = new ArrayList<String>();
    private static ArrayList<String> experienceSelectedItems = new ArrayList<String>();
    private static ArrayList<String> languagesSelectedItems = new ArrayList<String>();
    private static ArrayList<String> aboutMyselfSelectedItems = new ArrayList<String>();
    private static String title;
    DatabaseHelper dbHealper;
    EditText editTitle;
    private ArrayList<String> personalItems = new ArrayList<String>();
    private ArrayList<String> educationItems = new ArrayList<String>();
    private ArrayList<String> skillsItems = new ArrayList<String>();
    private ArrayList<String> experienceItems = new ArrayList<String>();
    private ArrayList<String> languagesItems = new ArrayList<String>();
    private ArrayList<String> aboutMyselfItems = new ArrayList<String>();
    private LinearLayout personalView;
    private LinearLayout educationView;
    private LinearLayout skillsView;
    private LinearLayout experienceView;
    private LinearLayout languagesView;
    private LinearLayout aboutMyselfView;
    private FileOutputStream os;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.new_cv_fragment, container, false);
        personalView = (LinearLayout) view.findViewById(R.id.newCVPersonalContainer);
        educationView = (LinearLayout) view.findViewById(R.id.newCVEducationContainer);
        skillsView = (LinearLayout) view.findViewById(R.id.newCVSkillsContainer);
        experienceView = (LinearLayout) view.findViewById(R.id.newCVExperienceContainer);
        languagesView = (LinearLayout) view.findViewById(R.id.newCVLanguagesContainer);
        aboutMyselfView = (LinearLayout) view.findViewById(R.id.newCVAboutMyselfContainer);
        editTitle = (EditText) view.findViewById(R.id.editNewCVTitle);
        Button buttonCreate = (Button) view.findViewById(R.id.buttonNewCVCreate);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = editTitle.getText().toString();
                if (title.equals("")) title = getString(R.string.my_cv);
                createCV("traditional_cv");
            }
        });
        setPersonalData(view);
        setEducationData(view);
        setSkillsData(view);
        setExperienceData(view);
        setLanguagesData(view);
        setAboutMyselfData(view);
        return view;
    }

    private void setPersonalData(View view) {
        dbHealper = new DatabaseHelper(view.getContext());
        SQLiteDatabase db = dbHealper.getReadableDatabase();
        Cursor c = db.query("personalTable", null, null, null, null, null, null);
        while (c.moveToNext()) {
            int nameColIndex = c.getColumnIndex("name");
            int ageColIndex = c.getColumnIndex("age");
            int phoneColIndex = c.getColumnIndex("phone");
            int emailColIndex = c.getColumnIndex("email");
            int addressColIndex = c.getColumnIndex("address");
            int sexColIndex = c.getColumnIndex("sex");
            personalItems.add(c.getString(nameColIndex));
            personalItems.add(c.getString(ageColIndex));
            personalItems.add(c.getString(sexColIndex));
            personalItems.add(c.getString(phoneColIndex));
            personalItems.add(c.getString(emailColIndex));
            personalItems.add(c.getString(addressColIndex));
        }
        personalItems.remove("");
        addViews(personalItems, personalSelectedItems, personalView, view);
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
        addViews(educationItems, educationSelectedItems, educationView, view);
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
        addViews(skillsItems, skillsSelectedItems, skillsView, view);
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
        addViews(experienceItems, experienceSelectedItems, experienceView, view);
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
        addViews(languagesItems, languagesSelectedItems, languagesView, view);
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
        addViews(aboutMyselfItems, aboutMyselfSelectedItems, aboutMyselfView, view);
        c.close();
        db.close();
    }

    private void addViews(ArrayList list, final ArrayList selectedList, LinearLayout layout, View view) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 5, 0, 0);
        for (int i = 0; i < list.size(); i++) {
            final Button button = new Button(view.getContext());
            button.setBackgroundResource(R.drawable.button_style);
            button.setLayoutParams(params);
            button.setText(list.get(i).toString());
            selectedList.add(list.get(i).toString());
            Drawable icon = getContext().getResources().getDrawable(R.drawable.item_selected);
            button.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedList.contains(button.getText().toString())) {
                        Drawable icon = getContext().getResources().getDrawable(R.drawable.item_unselected);
                        button.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
                        selectedList.remove(button.getText().toString());
                    } else {
                        Drawable icon = getContext().getResources().getDrawable(R.drawable.item_selected);
                        button.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
                        selectedList.add(button.getText().toString());
                    }
                }
            });
            layout.addView(button);
        }
    }

    public void createCV(String type) {
        Log.d("mylog", "createCV");
        File folder = new File(Environment.getExternalStorageDirectory() + "/" + "CV Manager");
        if (!folder.exists()) {
            folder.mkdirs();
            Log.d("mylog", "folder not exists");
        }
        try {
            if (folder.canWrite()) {
                Log.d("mylog", "folder can write");
                os = new FileOutputStream(folder + "/" + title + ".pdf");
                Log.d("mylog", "file created");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        creatingPDF(type);
    }

    private void creatingPDF(String type) {
        try {
            Log.d("mylog", "creating pdf");
            Document document = new Document(PageSize.A4, 50, 14, 14, 30);
            PdfWriter.getInstance(document, os);
            document.open();
            Log.d("mylog", "open document");
            if (type.equals("default_resume")) {
            } else if (type.equals("traditional_cv")) {
                addPagesTraditionalCV(document);
            }
            document.close();
            cleanData();
            Fragment mainFragment = new MainFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, mainFragment, "main");
            transaction.addToBackStack(null);
            transaction.commit();
            Log.d("mylog", "close document");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cleanData() {
        personalItems = new ArrayList<String>();
        educationItems = new ArrayList<String>();
        skillsItems = new ArrayList<String>();
        experienceItems = new ArrayList<String>();
        languagesItems = new ArrayList<String>();
        aboutMyselfItems = new ArrayList<String>();
        personalSelectedItems = new ArrayList<String>();
        educationSelectedItems = new ArrayList<String>();
        skillsSelectedItems = new ArrayList<String>();
        experienceSelectedItems = new ArrayList<String>();
        languagesSelectedItems = new ArrayList<String>();
        aboutMyselfSelectedItems = new ArrayList<String>();
    }


    private void addPagesTraditionalCV(Document document)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        BaseFont helvetica = null;
        try {
            helvetica = BaseFont.createFont("assets/fonts/HelveticaMedium.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Font font1 = new Font(helvetica, 22, Font.NORMAL);
        Font font2 = new Font(helvetica, 14, Font.NORMAL);
        Font font3 = new Font(helvetica, 14, Font.UNDERLINE);
        addEmptyLine(preface, 1);

        Paragraph personalParagraph = new Paragraph("");
        for (int i = 0; i < personalSelectedItems.size(); i++) {
            personalParagraph.add(personalSelectedItems.get(i).toString() + "\n");
        }
        personalParagraph.setAlignment(Element.ALIGN_CENTER);
        preface.add(personalParagraph);

        if (aboutMyselfSelectedItems.size() > 0) {
            addEmptyLine(preface, 1);
            for (int i = 0; i < aboutMyselfSelectedItems.size(); i++) {
                if (i == 0) {
                    Paragraph aboutMyselfParagraph = new Paragraph(aboutMyselfSelectedItems.get(i).toString() + "." + "\n", font2);
                    preface.add(aboutMyselfParagraph);
                } else {
                    Paragraph aboutMyselfParagraph = new Paragraph(aboutMyselfSelectedItems.get(i).toString() + "." + "\n", font2);
                    preface.add(aboutMyselfParagraph);
                }
            }
        }

        if (educationSelectedItems.size() > 0) {
            addEmptyLine(preface, 1);
            for (int i = 0; i < educationSelectedItems.size(); i++) {
                if (i == 0) {
                    Paragraph educationTitleParagraph = new Paragraph("Education and qualifications", font3);
                    preface.add(educationTitleParagraph);
                    addEmptyLine(preface, 1);
                    Paragraph educationParagraph = new Paragraph(educationSelectedItems.get(i).toString() + ";" + "\n", font2);
                    preface.add(educationParagraph);
                } else {
                    addEmptyLine(preface, 1);
                    Paragraph educationParagraph = new Paragraph(educationSelectedItems.get(i).toString() + ";" + "\n", font2);
                    preface.add(educationParagraph);
                }

            }
        }
        if (experienceSelectedItems.size() > 0) {
            addEmptyLine(preface, 1);
            for (int i = 0; i < experienceSelectedItems.size(); i++) {
                if (i == 0) {
                    Paragraph experienceTitleParagraph = new Paragraph("Work experience", font3);
                    preface.add(experienceTitleParagraph);
                    addEmptyLine(preface, 1);
                    Paragraph experienceParagraph = new Paragraph(experienceSelectedItems.get(i).toString() + ";" + "\n", font2);
                    preface.add(experienceParagraph);
                } else {
                    addEmptyLine(preface, 1);
                    Paragraph experienceParagraph = new Paragraph(experienceSelectedItems.get(i).toString() + ";" + "\n", font2);
                    preface.add(experienceParagraph);
                }

            }
        }
        if (skillsSelectedItems.size() > 0) {
            addEmptyLine(preface, 1);
            for (int i = 0; i < skillsSelectedItems.size(); i++) {
                if (i == 0) {
                    Paragraph skillsTitleParagraph = new Paragraph("Additional Skills", font3);
                    preface.add(skillsTitleParagraph);
                    addEmptyLine(preface, 1);
                    Paragraph skillsParagraph = new Paragraph(skillsSelectedItems.get(i).toString() + ";" + "\n", font2);
                    preface.add(skillsParagraph);
                } else {
                    Paragraph skillsParagraph = new Paragraph(skillsSelectedItems.get(i).toString() + ";" + "\n", font2);
                    preface.add(skillsParagraph);
                }
            }
        }
        if (languagesSelectedItems.size() > 0) {
            addEmptyLine(preface, 1);
            for (int i = 0; i < languagesSelectedItems.size(); i++) {
                if (i == 0) {
                    Paragraph languagesTitleParagraph = new Paragraph("Languages", font3);
                    preface.add(languagesTitleParagraph);
                    addEmptyLine(preface, 1);
                    Paragraph languagesParagraph = new Paragraph(languagesSelectedItems.get(i).toString() + ";" + "\n", font2);
                    preface.add(languagesParagraph);
                } else {
                    Paragraph languagesParagraph = new Paragraph(languagesSelectedItems.get(i).toString() + ";" + "\n", font2);
                    preface.add(languagesParagraph);
                }
            }
        }

        document.add(preface);
    }

    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }


}
