package com.utaharts.app.data.datasource;

import com.utaharts.app.data.SitesList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * This file is used to handle the XML tags. So we need to extends with DefaultHandler.
 * we need to override startElement, endElement & characters method .
 * startElemnt method called when the tag starts.
 * endElemnt method called when the tag ends
 * characres method to get characters inside tag.
 * Created by jacoblafazia on 5/13/14.
 */
public class MyXMLHandler extends DefaultHandler {

        Boolean currentElement = false;
        String currentValue = null;
        public static SitesList sitesList = null;

        public static SitesList getSitesList() {
            return sitesList;
        }

        public static void setSitesList(SitesList sitesList) {
            MyXMLHandler.sitesList = sitesList;
        }

        /**
         * Called when tag starts ( ex:- <name>AndroidPeople</name>
         * -- <name> )
         */
        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws SAXException {

            currentElement = true;

            if (localName.equals("maintag")) {
/** Start */
                sitesList = new SitesList();
            } else if (localName.equals("website")) {
/** Get attribute value */
                String attr = attributes.getValue("category");
                sitesList.setCategory(attr);
            }

        }

        /**
         * Called when tag closing ( ex:- <name>AndroidPeople</name>
         * -- </name> )
         */
        @Override
        public void endElement(String uri, String localName, String qName)
                throws SAXException {

            currentElement = false;

/** set value */
            if (localName.equalsIgnoreCase("name"))
                sitesList.setName(currentValue);
            else if (localName.equalsIgnoreCase("website"))
                sitesList.setWebsite(currentValue);

        }

        /**
         * Called to get tag characters ( ex:- <name>AndroidPeople</name>
         * -- to get AndroidPeople Character )
         */
        @Override
        public void characters(char[] ch, int start, int length)
                throws SAXException {

            if (currentElement) {
                currentValue = new String(ch, start, length);
                currentElement = false;
            }

        }




}
