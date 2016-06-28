package bigheadsman.cvmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int REQ_CODE_PICK_IMAGE = 1;
    public int PICK_IMAGE_REQUEST = 1;
    private String foto = "foto";
    private String name;
    private String phone;
    private String age;
    private String email;
    private String address;
    private String sex;
    DatabaseHelper dbHealper;
    private Locale mNewLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragmentToMain();
    }

    public void setLocale(String mLang) {
        mNewLocale = new Locale(mLang);
        Locale.setDefault(mNewLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = mNewLocale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (id == R.id.personal_data) {
            Fragment personalDataFragment = new PersonalDataFragment();
            transaction.replace(R.id.fragmentContainer, personalDataFragment);
        } else if (id == R.id.education) {
            Fragment educationFragment = new EducationFragment();
            transaction.replace(R.id.fragmentContainer, educationFragment);
        } else if (id == R.id.skills) {
            Fragment skillsFragment = new SkillsFragment();
            transaction.replace(R.id.fragmentContainer, skillsFragment);
        } else if (id == R.id.experience) {
            Fragment experienceFragment = new ExperienceFragment();
            transaction.replace(R.id.fragmentContainer, experienceFragment);
        } else if (id == R.id.languages) {
            Fragment languagesFragment = new LanguagesFragment();
            transaction.replace(R.id.fragmentContainer, languagesFragment);
        } else if (id == R.id.about_myself) {
            Fragment aboutMyselfFragment = new AboutMyselfFragment();
            transaction.replace(R.id.fragmentContainer, aboutMyselfFragment);
        } else if (id == R.id.letters) {
            Fragment lettersFragment = new LettersFragment();
            transaction.replace(R.id.fragmentContainer, lettersFragment);
        } else if (id == R.id.choose_languages) {
            Fragment chooseLocaleFragment = new ChooseLoacaleFragment();
            transaction.replace(R.id.fragmentContainer, chooseLocaleFragment);
        }
        transaction.addToBackStack(null);
        transaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onPersonalDataButtonClick(View view) {
        dbHealper = new DatabaseHelper(this);
        ContentValues cv = new ContentValues();
        view.setBackgroundResource(R.color.button_background_active);
        switch (view.getId()) {
            case R.id.personalUploadFoto: {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                break;
            }
            case R.id.personalDeleteFoto: {
                foto = "foto";
                Bitmap yourSelectedImage = BitmapFactory.decodeFile(foto);
                ImageView imageView = (ImageView) findViewById(R.id.photo);
                imageView.setImageBitmap(yourSelectedImage);
                break;
            }
            case R.id.personalSave: {
                SQLiteDatabase db = dbHealper.getWritableDatabase();
                db.execSQL("delete from personalTable");
                EditText editName = (EditText) findViewById(R.id.fullName);
                name = editName.getText().toString();
                EditText editAge = (EditText) findViewById(R.id.age);
                age = editAge.getText().toString();
                RadioButton rbM = (RadioButton) findViewById(R.id.radioButtonMale);
                RadioButton rbF = (RadioButton) findViewById(R.id.radioButtonFemale);
                if (rbM.isChecked()) {
                    sex = "Male";
                } else if (rbF.isChecked()) {
                    sex = "Female";
                } else {
                    sex = "Hermafrodit";
                }
                EditText editPhone = (EditText) findViewById(R.id.phone);
                phone = editPhone.getText().toString();
                EditText editEMail = (EditText) findViewById(R.id.email);
                email = editEMail.getText().toString();
                EditText editAddress = (EditText) findViewById(R.id.address);
                address = editAddress.getText().toString();
                cv.put("foto", foto);
                cv.put("name", name);
                cv.put("age", age);
                cv.put("phone", phone);
                cv.put("sex", sex);
                cv.put("email", email);
                cv.put("address", address);
                db.insert("personalTable", null, cv);
                db.close();
                replaceFragmentToPersonalData();
                break;
            }
            case R.id.personalBack: {
                replaceFragmentToMain();
                break;
            }
        }
    }

    public void onEducationButtonClick(View view) {
        view.setBackgroundResource(R.color.button_background_active);
        switch (view.getId()) {
            case R.id.addNewEdu: {
                dbHealper = new DatabaseHelper(getApplicationContext());
                ContentValues cv = new ContentValues();
                Spinner spType = (Spinner) findViewById(R.id.spinnerTypeOfEdu);
                Spinner spStart = (Spinner) findViewById(R.id.spinnerStartEdu);
                Spinner spEnd = (Spinner) findViewById(R.id.spinnerEduEnd);
                EditText editEdu = (EditText) findViewById(R.id.eduMultiText);
                SQLiteDatabase db = dbHealper.getWritableDatabase();
                cv.put("type", spType.getSelectedItem().toString());
                cv.put("start", spStart.getSelectedItem().toString());
                cv.put("end", spEnd.getSelectedItem().toString());
                cv.put("name", editEdu.getText().toString());
                db.insert("educationTable", null, cv);
                replaceFragmentToEducation();
                db.close();
                break;
            }
            case R.id.educationDelete: {
                EducationFragment edu = new EducationFragment();
                dbHealper = new DatabaseHelper(getApplicationContext());
                SQLiteDatabase db = dbHealper.getWritableDatabase();
                ArrayList<Integer> itemList = edu.getItemList();
                for (int i = 0; i < itemList.size(); i++) {
                    db.delete("educationTable", "id = " + itemList.get(i), null);
                }
                db.close();
                replaceFragmentToEducation();
                break;
            }
            case R.id.educationBack: {
                replaceFragmentToMain();
                break;
            }
        }
    }

    public void onSkillsButoonClick(View view) {
        view.setBackgroundResource(R.color.button_background_active);
        dbHealper = new DatabaseHelper(getApplicationContext());
        switch (view.getId()) {
            case R.id.buttonSkillAdd: {
                ContentValues cv = new ContentValues();
                EditText editScill = (EditText) findViewById(R.id.editTextNewScill);
                SQLiteDatabase db = dbHealper.getWritableDatabase();
                cv.put("name", editScill.getText().toString());
                db.insert("scillsTable", null, cv);
                db.close();
                replaceFragmentToSkills();
                break;
            }
            case R.id.buttonSkillDelete: {
                SkillsFragment skl = new SkillsFragment();
                SQLiteDatabase db = dbHealper.getWritableDatabase();
                ArrayList<Integer> itemList = skl.getItemList();
                for (int i = 0; i < itemList.size(); i++) {
                    db.delete("scillsTable", "id = " + itemList.get(i), null);
                }
                db.close();
                replaceFragmentToSkills();
                break;
            }
            case R.id.buttonSkillBack: {
                replaceFragmentToMain();
                break;
            }
        }
        dbHealper.close();
    }

    public void onExperienceButtonClick(View view) {
        view.setBackgroundResource(R.color.button_background_active);
        dbHealper = new DatabaseHelper(getApplicationContext());
        switch (view.getId()) {
            case R.id.buttonAddExperience: {
                ContentValues cv = new ContentValues();
                Spinner spStart = (Spinner) findViewById(R.id.spinnerExperienceStart);
                Spinner spEnd = (Spinner) findViewById(R.id.spinnerExperienceEnd);
                EditText editEdu = (EditText) findViewById(R.id.editTextExp);
                SQLiteDatabase db = dbHealper.getWritableDatabase();
                cv.put("start", spStart.getSelectedItem().toString());
                cv.put("end", spEnd.getSelectedItem().toString());
                cv.put("name", editEdu.getText().toString());
                db.insert("experienceTable", null, cv);
                db.close();
                replaceFragmentToExperience();
                break;
            }
            case R.id.buttonDeleteExperience: {
                ExperienceFragment exp = new ExperienceFragment();
                ArrayList<Integer> itemList = exp.getItemList();
                SQLiteDatabase db = dbHealper.getWritableDatabase();
                for (int i = 0; i < itemList.size(); i++) {
                    db.delete("experienceTable", "id = " + itemList.get(i), null);
                }
                db.close();
                replaceFragmentToExperience();
                break;
            }
            case R.id.buttonBackExperience: {
                replaceFragmentToMain();
                break;
            }
        }
        dbHealper.close();
    }

    public void onLanguagesButtonClick(View view) {
        view.setBackgroundResource(R.color.button_background_active);
        dbHealper = new DatabaseHelper(getApplicationContext());
        switch (view.getId()) {
            case R.id.buttonAddLanguages: {
                ContentValues cv = new ContentValues();
                Spinner spType = (Spinner) findViewById(R.id.spinnerLanguages);
                EditText editEdu = (EditText) findViewById(R.id.languagesText);
                SQLiteDatabase db = dbHealper.getWritableDatabase();
                cv.put("name", editEdu.getText().toString());
                cv.put("level", spType.getSelectedItem().toString());
                db.insert("languagesTable", null, cv);
                db.close();
                replaceFragmentToLanguages();
                break;
            }
            case R.id.buttonDeleteLanguages: {
                LanguagesFragment lan = new LanguagesFragment();
                ArrayList<Integer> itemList = lan.getItemList();
                SQLiteDatabase db = dbHealper.getWritableDatabase();
                for (int i = 0; i < itemList.size(); i++) {
                    db.delete("languagesTable", "id = " + itemList.get(i), null);
                }
                db.close();
                replaceFragmentToLanguages();
                break;
            }
            case R.id.buttonBackLanguages: {
                replaceFragmentToMain();
                break;
            }
        }
        dbHealper.close();
    }

    public void onAboutMyselfButtonClick(View view) {
        view.setBackgroundResource(R.color.button_background_active);
        dbHealper = new DatabaseHelper(getApplicationContext());
        switch (view.getId()) {
            case R.id.buttonAboutMyselfAdd: {
                ContentValues cv = new ContentValues();
                SQLiteDatabase db = dbHealper.getWritableDatabase();
                EditText editEdu = (EditText) findViewById(R.id.aboutMyselfText);
                cv.put("name", editEdu.getText().toString());
                db.insert("aboutMyselfTable", null, cv);
                db.close();
                replaceFragmentToAboutMyself();
                break;
            }
            case R.id.buttonAboutMyselfDelete: {
                AboutMyselfFragment abmy = new AboutMyselfFragment();
                ArrayList<Integer> itemList = abmy.getItemList();
                SQLiteDatabase db = dbHealper.getWritableDatabase();
                for (int i = 0; i < itemList.size(); i++) {
                    db.delete("aboutMyselfTable", "id = " + itemList.get(i), null);
                }
                db.close();
                replaceFragmentToAboutMyself();
                break;
            }
            case R.id.buttonAboutMySelfBack: {
                replaceFragmentToMain();
                break;
            }
        }
        dbHealper.close();
    }

    public void onLetterButtonClick(View view) {
        view.setBackgroundResource(R.color.button_background_active);
        dbHealper = new DatabaseHelper(getApplicationContext());
        switch (view.getId()) {
            case R.id.buttonLetterAdd: {
                ContentValues cv = new ContentValues();
                SQLiteDatabase db = dbHealper.getWritableDatabase();
                EditText editEdu = (EditText) findViewById(R.id.lettersText);
                cv.put("name", editEdu.getText().toString());
                db.insert("lettersTable", null, cv);
                db.close();
                replaceFragmentToLetters();
                break;
            }
            case R.id.buttonLetterDelete: {
                LettersFragment letters = new LettersFragment();
                ArrayList<Integer> itemList = letters.getItemList();
                SQLiteDatabase db = dbHealper.getWritableDatabase();
                for (int i = 0; i < itemList.size(); i++) {
                    db.delete("lettersTable", "id = " + itemList.get(i), null);
                }
                db.close();
                replaceFragmentToLetters();
                break;
            }
            case R.id.buttonLetterBack: {
                replaceFragmentToMain();
                break;
            }
        }
        dbHealper.close();
    }

    public void onChooseLocaleClick(View view) {
        view.setBackgroundResource(R.color.button_background_active);
        switch (view.getId()) {
            case R.id.englishButton: {
                setLocale("en");
                break;
            }
            case R.id.ukrainianButton: {
                setLocale("uk");
                break;
            }
            case R.id.russianButton: {
                setLocale("ru");
                break;
            }
        }
        replaceFragmentToMain();
    }

    public void onMainFragmentButtonClick(View view) {
        view.setBackgroundResource(R.color.button_background_active);
        Button button = (Button) view;
        if (button.getText().toString().equals("New")) {

        } else {
            SharedPreferences sPref = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = sPref.edit();
            editor.putString("cv", button.getText().toString());
            editor.commit();
            replaceFragmentToCV();
        }
    }

    public void onCVFragmentButtonClick(View view) {
        view.setBackgroundResource(R.color.button_background_active);
        SharedPreferences sPref = getPreferences(MODE_PRIVATE);
        String cvName = sPref.getString("cv", "");
        switch (view.getId()) {
            case R.id.buttonCVOpen: {
                String targetFile = Environment.getExternalStorageDirectory().getPath() +
                        "/CV Manager/" + cvName + ".pdf";
                Uri targetUri = Uri.parse(targetFile);
                Intent openIntent = new Intent(Intent.ACTION_VIEW, targetUri);
                openIntent.setDataAndType(targetUri, "application/pdf");
                startActivity(openIntent);
                break;
            }
            case R.id.buttonCVSend: {
                String targetFile = Environment.getExternalStorageDirectory().getPath() +
                        "/CV Manager/" + cvName + ".pdf";
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("application/pdf");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, "");
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(targetFile));
                startActivity(Intent.createChooser(emailIntent, "Send email"));
                break;
            }
            case R.id.buttonCVDelete: {
                File deleteFile = new File(Environment.getExternalStorageDirectory() +
                        "/" + "CV Manager" + "/" + cvName + ".pdf");
                try {
                    deleteFile.getCanonicalFile().delete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                replaceFragmentToCV();
                break;
            }
            case R.id.buttonCVBack: {
                replaceFragmentToMain();
                break;
            }
        }
    }

    private void replaceFragmentToCV() {
        Fragment cvFragment = new CVFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, cvFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void replaceFragmentToMain() {
        Fragment mainFragment = new MainFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, mainFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void replaceFragmentToPersonalData() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment personalDataFragment = new PersonalDataFragment();
        transaction.replace(R.id.fragmentContainer, personalDataFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void replaceFragmentToEducation() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment educationFragment = new EducationFragment();
        transaction.replace(R.id.fragmentContainer, educationFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void replaceFragmentToSkills() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment skillsFragment = new SkillsFragment();
        transaction.replace(R.id.fragmentContainer, skillsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void replaceFragmentToExperience() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment experienceFragment = new ExperienceFragment();
        transaction.replace(R.id.fragmentContainer, experienceFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void replaceFragmentToLanguages() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment languagesFragment = new LanguagesFragment();
        transaction.replace(R.id.fragmentContainer, languagesFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void replaceFragmentToAboutMyself() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment aboutMyselfFragment = new AboutMyselfFragment();
        transaction.replace(R.id.fragmentContainer, aboutMyselfFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void replaceFragmentToLetters() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment lettersFragment = new LettersFragment();
        transaction.replace(R.id.fragmentContainer, lettersFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void replaceFragmentToChooseLocale() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment chooseLocaleFragment = new ChooseLoacaleFragment();
        transaction.replace(R.id.fragmentContainer, chooseLocaleFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override //get foto
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case REQ_CODE_PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(
                            selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    foto = filePath;
                    cursor.close();
                    Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
                    ImageView imageView = (ImageView) findViewById(R.id.photo);
                    imageView.setImageBitmap(yourSelectedImage);
                }
        }
    }
}
