package com.utaharts.app.data.datasource;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by jacoblafazia on 5/13/14.
 */
public class UAFDataSource {

    // inner

    public interface IUAFDatasourceCallback {
        public void dataReceived(String result, String error);
    }

    private class DownloadXMLTask extends AsyncTask<String, Void, String[]> {
        @Override
        protected String[] doInBackground(String... urls) {
            String response = "";
            String error = null;
            for (String url : urls) {
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                try {
                    HttpResponse execute = client.execute(httpGet);
                    InputStream content = execute.getEntity().getContent();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    error = e.toString();
                }
            }
            return new String[]{response, error};
        }

        @Override
        protected void onPostExecute(String[] response) {
            UAFDataSource self = UAFDataSource.this;
            if (self.callback != null) {
                self.callback.dataReceived(response[0], response[1]);
            }
        }
    }

    // static

    private static final String TAG = "UAFDataSource";

    // instance

    private IUAFDatasourceCallback callback;

//    public void getScheduleFeed() {
//        HttpGet uri = new HttpGet("http://uaf.org/uaf_iphone.xml");
//
//        DefaultHttpClient client = new DefaultHttpClient();
//        HttpResponse resp = null;
//        try {
//            resp = client.execute(uri);
//        } catch (Exception e) {
//            // todo handle exceptions
//        }
//
//        if (resp == null) {
//            // todo catch null error
//        }
//        StatusLine status = resp.getStatusLine();
//        if (status.getStatusCode() != 200) {
//            Log.d(TAG, "HTTP error, invalid server status code: " + resp.getStatusLine());
//        }
//
////        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
////        DocumentBuilder builder = factory.newDocumentBuilder();
////        Document doc = builder.parse(resp.getEntity().getContent());
//    }


//    public String getFeeds(IUAFDatasourceCallback callback) {
//        this.callback = callback;
//        String result = "none";
//
//        SitesList list = new AsyncTask<Void, Void, SitesList>() {
//
//            public SitesList sitesList;
//
//            @Override
//            protected SitesList doInBackground(Void... params) {
//
//                try {
//
//                    /** Send URL to parse XML Tags */
//                    URL sourceUrl = new URL("http://uaf.org/uaf_iphone.xml");
//
//                    /** Handling XML */
//                    // Create a URL we want to load some xml-data from
//                    // Get SAX parser from factory
//                    SAXParserFactory spf = SAXParserFactory.newInstance();
//                    SAXParser sp = spf.newSAXParser();
//                    // Get the XMLReader of the SAXParser we created
//                    XMLReader xr = sp.getXMLReader();
//
//                    /** Create handler to handle XML Tags ( extends DefaultHandler ) */
//                    MyXMLHandler myXMLHandler = new MyXMLHandler();
//                    xr.setContentHandler(myXMLHandler);
//
//                    /* Parse the xml-data from our URL */
//                    xr.parse(new InputSource(sourceUrl.openStream()));
//
//                    /** Get result from MyXMLHandler SitlesList Object */
//                    // could have method getParsedData()
//                    sitesList = MyXMLHandler.sitesList;
//
//                    this.textView.setTitleText(sitesList.toString());
//
//                } catch (Exception e) {
//                    System.out.println("XML Pasing Excpetion = " + e);
//                    Log.d(TAG, "XML Parsing Exception: " + e);
//
//                    this.textView.setTitleText("XML Parsing Exception: " + e);
//                }
//
//                return sitesList;
//            }
//
//            @Override
//            protected void onPostExecute(SitesList result) {
//                super.onPostExecute(result);
//            }
//        };
//
//
//        if (list != null) {
//            result = list.toString();
//        }
//
//        return result;
//    }

    public void getScheduleFeed(IUAFDatasourceCallback callback) {
        this.callback = callback;

        DownloadXMLTask task = new DownloadXMLTask();
        task.execute("http://uaf.org/uaf_iphone.xml");
    }



}
