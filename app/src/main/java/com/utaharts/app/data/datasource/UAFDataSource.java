package com.utaharts.app.data.datasource;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Xml;

import com.utaharts.app.data.Event;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Contains methods for handling data requests including downloading and parsing
 * xml data from web and saving this data to objects.
 * <p/>
 * Created by jacoblafazia on 5/13/14.
 */
public class UAFDataSource {

    // inner

    /**
     * Callback method for the UAFDataSource
     */
    public interface IUAFDatasourceCallback {
        public void dataReceived(String result, String error);
    }

    /**
     * AsyncTask for downloading xml data from a URL.<br>
     * The xml data is returned as a String[] to the
     * callback method. The String[] contains two strings: the first string
     * is the response (true or false) or the raw xml data. The second string contains any error
     * caught while pulling xml from the web. If the error string is null, no
     * errors have occurred.
     * <p/>
     * Uses XmlPullParser
     */
    private class DownloadXMLTask extends AsyncTask<String, Void, String[]> {
        // TAG for logs
        private static final String TAG = "DownloadXMLTask";

        @Override
        protected String[] doInBackground(String... urls) {
            StringBuilder response = new StringBuilder();
            String error = null;
            for (String url : urls) {
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                try {
                    HttpResponse execute = client.execute(httpGet);
                    InputStream in = execute.getEntity().getContent();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response.append(s);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    error = e.toString();
                }
            }
            return new String[]{response.toString(), error};
        }

        @Override
        protected void onPostExecute(String[] response) {
            // read the response to parse xml
            UAFDataSource.this.readResponse(response);
        }
    }


    // static

    private static final String TAG = "UAFDataSource";
    private static final String NAMESPACES = null;
    // Definition of xml tags of interest
    private static final String FEED = "UtahArtsFestival2011";
    private static final String ENTRY = "event";
    private static final String NAME = "name";
    private static final String DAY = "day";
    private static final String START_TIME = "starttime";
    private static final String END_TIME = "endtime";
    private static final String STAGE = "stage";
    private static final String GENRE = "genre";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";

    // instance

    private IUAFDatasourceCallback callback;
    private List<Event> events = new ArrayList<Event>();
    private String parseError = null;
    // temp hash map for testing
    private HashMap<String, String> dataHashMap = new HashMap<String, String>();
    // Single JSON object containing all Events as JSON objects
    JSONObject jsonAllEvents = new JSONObject();
    // ArrayList of all events as JSONObjects
    List<JSONObject> jsonEvents = new ArrayList<JSONObject>();


    /**
     * Gets all data from xml pull from url. Data is then stored
     * into objects that can be accessed throughout the app.
     * <p/>
     * NOTE: Only call this method if no data currently exists as
     * it will retrieve and parse all xml data from the web and
     * can take some time to complete. To update current data call
     * updateAllDataFromWeb().
     *
     * @param callback IUAFDatasourceCallback
     */
    public void getAllData(IUAFDatasourceCallback callback) {
        this.callback = callback;

        DownloadXMLTask task = new DownloadXMLTask();
        task.execute("http://uaf.org/uaf_iphone.xml");
    }

    /**
     * @return List of Events represented as JSON Objects
     */
    public List<JSONObject> getJsonEventList() {
        return this.jsonEvents;
    }

    /**
     * @return Single JSON object containing all events from xml
     */
    public JSONObject getJson() {
        return this.jsonAllEvents;
    }

    /**
     * @return the current list of Events.
     */
    public List<Event> getEventList() {
        return this.events;
    }

    /**
     * This version creates a list of events which can then be parsed separately.
     * <p/>
     * Parses the xml string into a list of events.
     *
     * @param response String[] {xml data string, error}
     */
    private void readResponse(String[] response) {
        // check for errors (response[1])
        if (response[1] != null) {
            // error has occurred, return empty data string and error string
            if (this.callback != null) {
                this.callback.dataReceived("", response[1]);
            }
            return;
        }

        // Create events from xml
        this.events = this.parseXmlString(response[0]);
        // convert events into Json
        this.createJsonFromEvents();
        if (this.callback != null) {
            // Notify calling method that data has been received without error.
            this.callback.dataReceived("true", null);
        }
    }

    /**
     * The Parser.
     *
     * @param xmlString String containing complete contents of xml file
     * @return List of all Items contained within the xml
     */
    private List<Event> parseXmlString(String xmlString) {
        // parser requires StringReader
        StringReader xmlReader = new StringReader(xmlString);

        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(xmlReader);
            parser.nextTag();
            return readFeed(parser);
        } catch (Exception e) {
            e.printStackTrace();
            // Error has occurred, return empty list
            this.parseError = e.toString();
            return new ArrayList<Event>();
        }
    }

    /**
     * Reads the entire xml feed string and parses all defined xml tags.<br>
     *     NOTE: Change list type based on defined object types.
     *
     * @param parser XmlPullParser
     * @return List of defined tag objects
     */
    private List<Event> readFeed(XmlPullParser parser) {
        List<Event> entries = new ArrayList<Event>();

        try {
            parser.require(XmlPullParser.START_TAG, NAMESPACES, FEED);
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                // Starts by looking for the entry tag as defined in static variables
                if (name.equals(ENTRY)) {
                    // Parse entry tags and store in list
                    entries.add(this.readEntry(parser));
                } else {
                    // skip all other tags
                    skip(parser);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.parseError = e.toString();
        }
        return entries;
    }

    /**
     * <P>Parses the contents of an event. If it encounters a name, day, starttime, endtime, stage,
     * genre, description or image tag, hands them off to their respective "read" methods for
     * processing. Otherwise, skips the tag.</P>
     * <P>
     *      This method is specific to the xml file pulled from web.
     * </P>
     *
     * @param parser XmlPullParser
     * @return List of Event objects
     */
    private Event readEntry(XmlPullParser parser) {
        String name = null;
        String day = null;
        String startTime = null;
        String endTime = null;
        String stage = null;
        String genre = null;
        String description = null;
        String imageURL = null;

        try {
            parser.require(XmlPullParser.START_TAG, NAMESPACES, ENTRY);

            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String tag = parser.getName();
                if (tag.equals(NAME)) {
                    name = readTextTag(parser, NAME);
                } else if (tag.equals(DAY)) {
                    day = readTextTag(parser, DAY);
                } else if (tag.equals(START_TIME)) {
                    startTime = readTextTag(parser, START_TIME);
                } else if (tag.equals(END_TIME)) {
                    endTime = readTextTag(parser, END_TIME);
                } else if (tag.equals(STAGE)) {
                    stage = readTextTag(parser, STAGE);
                } else if (tag.equals(GENRE)) {
                    genre = readTextTag(parser, GENRE);
                } else if (tag.equals(DESCRIPTION)) {
                    description = readTextTag(parser, DESCRIPTION);
                } else if (tag.equals(IMAGE)) {
                    // TODO use method readImageUrl to get image
                    imageURL = readTextTag(parser, IMAGE);
                } else {
                    skip(parser);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.parseError = e.toString();
        }

        return new Event(name, day, startTime, endTime, stage, genre, description, imageURL);
    }

    /**
     * Processes a tag who's value is text.
     *
     * @param parser XmlPullParser
     * @param tag Tag name
     * @return String value of tag
     */
    private String readTextTag(XmlPullParser parser, String tag) {
        String text = null;
        try {
            parser.require(XmlPullParser.START_TAG, NAMESPACES, tag);
            text = readText(parser);
            parser.require(XmlPullParser.END_TAG, NAMESPACES, tag);
        } catch (Exception e) {
            e.printStackTrace();
            this.parseError = e.toString();
        }
        return text;
    }

    /**
     * Extracts the text value from a tag.
     *
     * @param parser XmlPullParser
     * @return Value of xml tag as text
     */
    private String readText(XmlPullParser parser) {
        String result = "";
        try {
            if (parser.next() == XmlPullParser.TEXT) {
                result = parser.getText();
                parser.nextTag();
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.parseError = e.toString();
        }

        return result;
    }

    /**
     * Processes tags that contain image download url. Downloads the image
     * and saves to Bitmap.
     *
     * @param parser XmlPullParser
     * @return Bitmap of downloaded image
     */
    private Bitmap readImageUrl(XmlPullParser parser) {
        Bitmap image = null;
        // TODO create new task to download image from url
        return image;
    }

    /**
     * Skips tags that are not needed.
     *
     * @param parser XmlPullParser
     */
    private void skip(XmlPullParser parser) {
        try {
            // Current event must be a start tag, otherwise throws exception
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                throw new IllegalStateException();
            }
            // Keep track of nesting depth to ensure it stops at the correct END_TAG
            int depth = 1;
            // Consume the START_TAG and all events up to and including the matching END_TAG
            while (depth != 0) {
                switch (parser.next()) {
                    case XmlPullParser.END_TAG:
                        depth--;
                        break;
                    case XmlPullParser.START_TAG:
                        depth++;
                        break;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            this.parseError = e.toString();
        }
    }

    /**
     * Creates JSON objects from all Event objects.
     */
    private void createJsonFromEvents() {
        int counter = 0;
        for (Event e : this.events) {
            try {
                JSONObject jsonEvent = new JSONObject();
                jsonEvent.put(NAME, e.name);
                jsonEvent.put(DAY, e.day);
                jsonEvent.put(START_TIME, e.starttime);
                jsonEvent.put(END_TIME, e.endtime);
                jsonEvent.put(STAGE, e.stage);
                jsonEvent.put(GENRE, e.genre);
                jsonEvent.put(DESCRIPTION, e.description);
                jsonEvent.put(IMAGE, e.imageURL);

                // Add JSON object to list
                this.jsonEvents.add(jsonEvent);
                // Add JSON event into main JSON object
                this.jsonAllEvents.put(ENTRY + counter, jsonEvent);
                counter++;

            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }
















//    /**
//     * This version uses a hashmap to store entries which can then be parsed separately.
//     * <p/>
//     * Parses the xml string data into usable data objects.
//     * If any errors have occurred when pulling xml data from the web this method
//     * will notify the calling activity through the UAFDatasource callback.
//     *
//     * @param response String[] {xml data string, error}
//     */
//    private void readResponseHash(String[] response) {
//        // check for errors (response[1])
//        if (response[1] != null) {
//            // error has occurred, return empty data string and error string
//            if (this.callback != null) {
//                this.callback.dataReceived("", response[1]);
//            }
//            return;
//        }
//
//        // test code
//        this.callback.dataReceived(response[0], response[1]);
//
//        // TODO read string of xml and parse data
//        // for now, store xml data as key/value pair
//        dataHashMap = new HashMap<String, String>();
//
//        String parseError = null;
//        try {
//
//            String elementName = "";
//            String elementValue = "";
//            String nameSpace = "";
//
//            StringReader xmlReader = new StringReader(response[0]);
//
//            // The XmlPullParser responds to XML "events". As it steps through
//            // the passed XML, each different element it encounters triggers an
//            // event.
//
//            // The developer's job is to respond appropriately to the desired
//            // events.
//
//            XmlPullParser parser = Xml.newPullParser();
//            parser.setInput(xmlReader);
//
//            int eventType = parser.getEventType();
//
//            while (eventType != XmlPullParser.END_DOCUMENT) {
//
//                switch (eventType) {
//
//                    case XmlPullParser.START_TAG:
//
//                        elementName = parser.getName();
//                        elementValue = parser.getAttributeValue(nameSpace, DATA);
//
//                        dataHashMap.put(elementName, elementValue);
//
//                }
//
//                eventType = parser.next();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            parseError = e.toString();
//        }
//
//        if (this.callback != null) {
//            this.callback.dataReceived("", parseError);
//        }
//
//        if (dataHashMap.containsKey(NAME)) {
//
//            String name = dataHashMap.get(NAME);
//
//            // TODO example on how to download an image from url found in xml data
////            String googleChart = GOOGLE_URL + chartURL;
////
////            ImageView ivChart = (ImageView) findViewById(R.id.img_chart);
////
////            try {
////                Log.i(TAG, "Chart bitmap from URL");
////
////                URL googleChartURL = new URL(googleChart);
////                HttpURLConnection conn = (HttpURLConnection) googleChartURL.openConnection();
////                conn.setDoInput(true);
////                conn.connect();
////
////                InputStream is = conn.getInputStream();
////
////                Bitmap bmImg = BitmapFactory.decodeStream(is);
////
////                ivChart.setImageBitmap(bmImg);
////
////            } catch (Exception e) {
////                Log.e(TAG, e.getMessage(), e);
////
////            }
//
//        }
//    }


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
}
