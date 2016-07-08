package bigheadsman.cvmanager;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ChooseCVTypeDialog extends Dialog {
    ImageButton defaultResume;
    ImageButton traditionalCV;

    public ChooseCVTypeDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_cv_type_dialog);
        setTitle(R.string.choose_type);
        defaultResume = (ImageButton) findViewById(R.id.buttonDefaultResume);
        traditionalCV = (ImageButton) findViewById(R.id.buttonTraditionalCV);
        defaultResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewCVFragment fragment = new NewCVFragment();
                fragment.createCV("default_resume");
                dismiss();
            }
        });
        traditionalCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewCVFragment fragment = new NewCVFragment();
                fragment.createCV("traditional_cv");
                dismiss();
            }
        });

    }


}
