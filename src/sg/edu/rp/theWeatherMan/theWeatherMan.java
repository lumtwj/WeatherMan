package sg.edu.rp.theWeatherMan;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.ListActivity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

public class theWeatherMan extends ListActivity {
	ListView listView;
	ArrayList<City> cityData = new ArrayList<City>();
    ArrayList<String> city = new ArrayList<String>();
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        city.add("Singapore");
        city.add("Tokyo");
        city.add("New%20York");
        city.add("New%20Delhi");
        city.add("Kuala%20Lumpur");
        city.add("Jakarta");
        
        Thread t = new Thread(doXmlRetrieval, "XML");
        t.start();
    }
    
    private Runnable doXmlRetrieval = new Runnable() {
        @Override
	    public void run() {
            for(int i = 0; i < city.size(); i++) {
            	XMLParser(city.get(i));
            }
	        
        	xmlHandler.sendEmptyMessage(0);
	    }
    };
    
    private Handler xmlHandler = new Handler(){
    	@Override
		public void handleMessage(Message msg) {
            listView = getListView();
            
            customListAdapter aa = new customListAdapter(theWeatherMan.this, R.layout.joelist, cityData);
            
            listView.setAdapter(aa);
    	}
	};

    
    private void XMLParser(String cityname){
        //Get the XML
        URL url;
        try{
        	
          //Get the city name and construct the full URL to get the XML file
        	String StringUrl="http://www.google.com/ig/api?weather=" + cityname;
        	url = new URL(StringUrl);
        		
        	URLConnection connection;
        	connection=url.openConnection();
        		
        	//Starts a HTTP connection
          HttpURLConnection httpConnection = (HttpURLConnection)connection;
        	int responseCode = httpConnection.getResponseCode();
        		
        	// Standard response for successful HTTP requests
          if (responseCode == HttpURLConnection.HTTP_OK){
        		InputStream in = httpConnection.getInputStream();
        			
        		// A factory API that enables applications to obtain a parser that produces DOM object trees from XML documents
                DocumentBuilderFactory dbf=
                DocumentBuilderFactory.newInstance();
        		DocumentBuilder db=dbf.newDocumentBuilder();
        			
        		//Parse the RSS feed
        		Document dom=db.parse(in);
        		Element docEle=dom.getDocumentElement();
        			
        		//Get the current weather condition by the Tag Name - current_conditions
        		NodeList nl=docEle.getElementsByTagName("current_conditions");
        			
        		//Retrieve the child elements in *current_conditions* 
        		if (nl != null && nl.getLength() > 0) {
        		   //If there are more than 1 *current_conditions*, it will go through each of the XML tree of *current_conditions* and retrieve the content from there. This will be more applicable for *weather_forecase* 
                   for (int i = 0 ; i < nl.getLength(); i++) {
                	   	Element entry=(Element)nl.item(i);
                	   
	       				Element condition =(Element)entry.getElementsByTagName("condition").item(0);
	       				
	    				String content = "";
	    			    content = condition.getAttributeNode("data").getValue();	
	    			    
	       				Element temperature =(Element)entry.getElementsByTagName("temp_c").item(0);
	       				
	    				String temp = "";
	    			    temp = temperature.getAttributeNode("data").getValue();	
	    			    
	       				Element imageURL =(Element)entry.getElementsByTagName("icon").item(0);
	       				
	    				String link = "";
	    			    link = "http://www.google.com" + imageURL.getAttributeNode("data").getValue();	
	    			    
	    			    
	    				InputStream is = (InputStream) new 
                        URL(link).getContent();
	    				Drawable imgDrawable = Drawable.createFromStream(is, link);
	    				
	       				Element humidity =(Element)entry.getElementsByTagName("humidity").item(0);
	       				
	    				String humid = "";
	    			    humid = humidity.getAttributeNode("data").getValue();
	    			    
	    			    String [] test = cityname.split("%20");
	    			    
	    			    for(int g = 0; g < test.length; g++) {
	    			    	if(g == 0) {
	    			    		cityname = test[g];
	    			    	} else {
	    			    		cityname += " " + test[g];
	    			    	}
	    			    }

	    				cityData.add(new City(cityname, temp, content, humid,imgDrawable));
        				}
        			}
        		}
        	}catch (MalformedURLException e) {
        		e.printStackTrace();
        		} catch (IOException e) {
        			e.printStackTrace();
        			} catch (ParserConfigurationException e) {
        				e.printStackTrace();
        				} catch (SAXException e) {
        					e.printStackTrace();
        					}
        				finally {					
        						
        				}
        				}	

}