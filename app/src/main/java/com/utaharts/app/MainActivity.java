package com.utaharts.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.utaharts.app.data.SitesList;
import com.utaharts.app.data.datasource.UAFDataSource;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "MainActivity";

    private TextView textView;
    private SitesList sitesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.textView = (TextView) this.findViewById(R.id.text_view);

//        UAFDataSource dataSource = new UAFDataSource();
//        dataSource.getScheduleFeed(new UAFDataSource.IUAFDatasourceCallback() {
//            @Override
//            public void dataReceived(String result, String error) {
//                if (error == null) {
//                    MainActivity.this.textView.setText(result);
//                } else {
//                    MainActivity.this.textView.setText(error);
//                }
//            }
//        });



//        try {
//
//            /** Send URL to parse XML Tags */
//            URL sourceUrl = new URL("http://uaf.org/uaf_iphone.xml");
//
//            /** Handling XML */
//            // Create a URL we want to load some xml-data from
//            // Get SAX parser from factory
//            SAXParserFactory spf = SAXParserFactory.newInstance();
//            SAXParser sp = spf.newSAXParser();
//            // Get the XMLReader of the SAXParser we created
//            XMLReader xr = sp.getXMLReader();
//
//            /** Create handler to handle XML Tags ( extends DefaultHandler ) */
//            MyXMLHandler myXMLHandler = new MyXMLHandler();
//            xr.setContentHandler(myXMLHandler);
//
//            /* Parse the xml-data from our URL */
//            xr.parse(new InputSource(sourceUrl.openStream()));
//
//            /** Get result from MyXMLHandler SitlesList Object */
//            // could have method getParsedData()
//            sitesList = MyXMLHandler.sitesList;
//
//            this.textView.setTitleText(sitesList.toString());
//
//        } catch (Exception e) {
//            System.out.println("XML Pasing Excpetion = " + e);
//            Log.d(TAG, "XML Parsing Exception: " + e);
//
//            this.textView.setTitleText("XML Parsing Exception: " + e);
//        }

//        /** Assign textview array length by arraylist size */
//        name = new TextView[sitesList.getName().size()];
//        website = new TextView[sitesList.getName().size()];
//        category = new TextView[sitesList.getName().size()];
//
//        /** Set the result text in textview and add it to layout */
//        for (int i = 0; i < sitesList.getName().size(); i++) {
//            name[i] = new TextView(this);
//            name[i].setTitleText("Name = " + sitesList.getName().get(i));
//            website[i] = new TextView(this);
//            website[i].setTitleText("Website = " + sitesList.getWebsite().get(i));
//            category[i] = new TextView(this);
//            category[i].setTitleText("Website Category = " + sitesList.getCategory().get(i));
//
//            layout.addView(name[i]);
//            layout.addView(website[i]);
//            layout.addView(category[i]);
//        }


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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
