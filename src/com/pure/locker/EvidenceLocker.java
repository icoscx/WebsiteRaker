package com.pure.locker;

import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlListItem;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.pure.logger.Log;

import java.util.HashMap;
import java.util.List;

public class EvidenceLocker {

    private HashMap simpleResults = new HashMap<String, String>();

    public void simpleParse(HtmlPage htmlPage, WebResponse webResponse) {

        DomNodeList<DomElement> domElements = htmlPage.getElementsByTagName("div");
        Log.logger.info("\nFound Elements" + domElements.getLength() + "\n");
        for(DomElement domElement : domElements){
            if(!domElement.getId().equals("") || !domElement.asText().equals("")){
                simpleResults.put(domElement.getId(), domElement.asText());
            }
        }
        Log.logger.info("");
    }

    public void advancedParse(HtmlPage htmlPage, WebResponse webResponse){

        DomNodeList<DomElement> domElements = htmlPage.getElementsByTagName("body");
        Log.logger.info("\n" + domElements.getLength() + "\n");
        for(DomElement domElement : domElements){
            for(DomElement domElement1 : domElement.getChildElements()) {

                Log.logger.info("Tag: " + domElement1.getTagName()+ " ID: "
                        +domElement1.getId() + " content: " +domElement1.asText() );

            }
        }
        Log.logger.info("");

    }


}
