package sg.edu.rp.theWeatherMan;


import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class customListAdapter extends ArrayAdapter<City> {
	Context context;
	int textViewResourceId;
	ArrayList<City> al;
	
	public customListAdapter(Context context, int textViewResourceId,
			ArrayList<City> al) {
		super(context, textViewResourceId, al);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.textViewResourceId = textViewResourceId;
		this.al = al;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		
		if(row == null) {
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = li.inflate(textViewResourceId, null);
		}
		
		City ali = al.get(position);
		
		if(ali != null) {
			TextView country = (TextView)row.findViewById(R.id.country);
            TextView content = (TextView)row.findViewById(R.id.content);
            ImageView image = (ImageView)row.findViewById(R.id.image);
            
            if (country != null) {
            	country.setText(ali.getCity());
            }
            if (content != null) {
            	content.setText("Temperature: " + ali.getWeather() + "°C\n" + ali.getHumidity() + "\n" + ali.getCondition());
            }
            if (image != null) {
            	image.setImageDrawable(ali.getIcon());
            }
		}
		
		return row;
	}
}
