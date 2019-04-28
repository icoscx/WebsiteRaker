package com.pure.locker;

import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.*;
import com.pure.logger.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class EvidenceLocker {

    private HashMap<String, String> simpleResultsDOM = new HashMap<>();
    private HashMap<String, String> simpleResultsJS = new HashMap<>();
    private List<String> tags = Arrays.asList("div","p","iframe");
    private static String rootPath = System.getProperty("user.dir");

    public void simpleParse(HtmlPage htmlPage, WebResponse webResponse)throws IOException {

        for(String tag : tags) {
            DomNodeList<DomElement> domElements = htmlPage.getElementsByTagName(tag);
            for (DomElement domElement : domElements) {
                //asText = parseFrameContent
                if (!domElement.getId().equals("") || !domElement.asText().equals("")) {
                    //frameRunner
                    simpleResultsDOM.put(domElement.getId(), domElement.asText());
                }
            }
        }

        DomNodeList<DomElement> domElements = htmlPage.getElementsByTagName("script");
        int pos = 0;
        for (DomElement domElement : domElements) {
            //getTextCOntent = view source
            if(!domElement.getTextContent().equals("")) {
                //Log.logger.info(domElement.getTagName() + "||" + domElement.getTextContent());
                simpleResultsJS.put("//" + pos, domElement.getTextContent());
                pos++;
            }
        }

        createFileForSandbox();
    }
    /*Iterate all tags**/
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

    private void createFileForSandbox() throws IOException{

        Path path = Paths.get(rootPath, File.separator + "malware" + File.separator + "fileCreated.js");
        Log.logger.info("Creating file: " + path.toString());
        File file = new File(path.toString());

        FileWriter fr = new FileWriter(file, true);

        for (Map.Entry<String, String> entry : simpleResultsDOM.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            fr.write("document._addElementById(\"" + key + "\",\"" + value + "\");\n");
        }

        for (Map.Entry<String, String> entry : simpleResultsJS.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            fr.write("\n" + key + "\n"+value + "\n");
        }

        fr.close();

    }

    private void frameRunner(HtmlPage htmlPage){

        List<FrameWindow> window = htmlPage.getFrames();
        Log.logger.info("Found frames: " + window.size());
        for (FrameWindow frame : window) {
            if (frame.getFrameElement().getId().equals("farmozz")) {
                HtmlPage subPage = (HtmlPage) frame.getEnclosedPage();
                Log.logger.info(subPage.asText());
            }
        }

    }


}
