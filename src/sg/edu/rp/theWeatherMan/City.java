package sg.edu.rp.theWeatherMan;

import android.graphics.drawable.Drawable;

public class City {
	String city;
	String weather;
	String condition;
	String humidity;
	Drawable icon;
	
	public City(String city, String weather, String condition, String humidity, Drawable icon) {
		super();
		this.city = city;
		this.weather = weather;
		this.condition = condition;
		this.icon = icon;
		this.humidity = humidity;
	}
	
	public String getCity() {
		return city;
	}
	public String getWeather() {
		return weather;
	}
	public String getCondition() {
		return condition;
	}
	public String getHumidity() {
		return humidity;
	}
	public Drawable getIcon() {
		return icon;
	}
}
