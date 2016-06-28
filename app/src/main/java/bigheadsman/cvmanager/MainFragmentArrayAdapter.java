package bigheadsman.cvmanager;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

public class MainFragmentArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public MainFragmentArrayAdapter(Context context, String[] values) {
        super(context, R.layout.button_main_fragment, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.button_main_fragment, parent, false);
        Button button = (Button) view.findViewById(R.id.buttonMainFragment);
        button.setText(values[position]);
        return view;
    }
}
