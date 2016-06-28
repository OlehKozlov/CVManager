package bigheadsman.cvmanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

public class PersonalDataFragment extends Fragment {
    DatabaseHelper dbHealper;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.personal_data_fragment, container, false);
        dbHealper = new DatabaseHelper(view.getContext());
        SQLiteDatabase db = dbHealper.getReadableDatabase(); // get database to reading
        final Cursor c = db.query("personalTable", null, null, null, null, null, null);
        if (c.moveToLast()) { // if table is true get data
            int fotoColIndex = c.getColumnIndex("foto");
            int nameColIndex = c.getColumnIndex("name");
            int ageColIndex = c.getColumnIndex("age");
            int sexColIndex = c.getColumnIndex("sex");
            int phoneColIndex = c.getColumnIndex("phone");
            int emailColIndex = c.getColumnIndex("email");
            int addressColIndex = c.getColumnIndex("address");
            Bitmap yourSelectedImage = BitmapFactory.decodeFile(c.getString(fotoColIndex));
            ImageView imageView = (ImageView) view.findViewById(R.id.photo);
            imageView.setImageBitmap(yourSelectedImage);
            EditText editName = (EditText) view.findViewById(R.id.fullName);
            editName.setText(c.getString(nameColIndex));
            EditText editAge = (EditText) view.findViewById(R.id.age);
            editAge.setText(c.getString(ageColIndex));
            RadioButton rbM = (RadioButton) view.findViewById(R.id.radioButtonMale);
            RadioButton rbF = (RadioButton) view.findViewById(R.id.radioButtonFemale);
            if (c.getString(sexColIndex).equals("Male")) {
                rbM.setChecked(true);
            } else if (c.getString(sexColIndex).equals("Female")) {
                rbF.setChecked(true);
            } else {
                rbM.setChecked(false);
                rbF.setChecked(false);
            }
            EditText editPhone = (EditText) view.findViewById(R.id.phone);
            editPhone.setText(c.getString(phoneColIndex));
            EditText editEMail = (EditText) view.findViewById(R.id.email);
            editEMail.setText(c.getString(emailColIndex));
            EditText editAddress = (EditText) view.findViewById(R.id.address);
            editAddress.setText(c.getString(addressColIndex));
        }
        c.close();
        db.close();

        return view;
    }
}
