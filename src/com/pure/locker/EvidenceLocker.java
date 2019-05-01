package com.pure.locker;

import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.*;
import com.pure.logger.Log;
import org.apache.commons.text.StringEscapeUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

public class EvidenceLocker {

    private List<String> exceptions = Arrays.asList("jQuery", "google-analytics", "schema.org", "CDATA"
    , "/themes/");
    private List<String> tags = Arrays.asList("div","p");
    //private HashMap<String, String> simpleResultsDOM = new HashMap<>();
    private HashMap<Integer, List<String>> simpleResultsDOM = new HashMap<>();
    private HashMap<String, String> simpleResultsJS = new HashMap<>();
    private static String rootPath = System.getProperty("user.dir");
    private URL uri = null;
    private int posOfJsPointer = 0;
    private int posOfDomPointer = 0;

    public void simpleParse(HtmlPage htmlPage)throws IOException {

        for(String tag : tags) {
            DomNodeList<DomElement> domElements = htmlPage.getElementsByTagName(tag);
            for (DomElement domElement : domElements) {
                //asText = parseFrameContent
                if (!domElement.getId().equals("")) {
                    //simpleFrameRunner
                    simpleResultsDOM.put(posOfDomPointer, Arrays.asList(domElement.getId(),
                            StringEscapeUtils.escapeEcmaScript(domElement.getTextContent())));
                    posOfDomPointer++;
                }
            }
        }
        //Special case: Script tags with ID
        DomNodeList<DomElement> domElements = htmlPage.getElementsByTagName("script");
        for (DomElement domElement : domElements) {
            if (!domElement.getId().equals("")) {
                simpleResultsDOM.put(posOfDomPointer, Arrays.asList(domElement.getId(),
                        StringEscapeUtils.escapeEcmaScript(domElement.getTextContent())));
                posOfDomPointer++;
            }
        }
        //Iterate script pieces
        for (DomElement domElement : domElements) {
            if(!domElement.getTextContent().equals("")) {
                String stringToSeek = domElement.getTextContent();
                //Push js excluding whitelist
                if(!exceptions.parallelStream().anyMatch(stringToSeek::contains)) {
                    simpleResultsJS.put("//" + posOfJsPointer, domElement.getTextContent());
                    posOfJsPointer++;
                }
            }
        }

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

    public void createVirtualPage() throws IOException, Exception{

        if(simpleResultsDOM.isEmpty() && simpleResultsJS.isEmpty()){
            throw new Exception("Cannot create VirtualPage without content");
        }
        String timeStamp = new SimpleDateFormat("MM-dd_HH-mm-ssss").format(Calendar.getInstance().getTime());
        Path path = Paths.get(rootPath, File.separator + "malware" + File.separator +
                uri.getHost().replace(".","_") +"_"+ timeStamp +".js");
        Log.logger.info("Creating file: " + path.toString());
        File file = new File(path.toString());

        FileWriter fr = new FileWriter(file, false);

        for (Map.Entry<Integer, List<String>> entry : simpleResultsDOM.entrySet()) {
            //String key = entry.getKey(); //String value = entry.getValue();
            String key = entry.getValue().get(0);
            String value = entry.getValue().get(1);
            fr.write("document._addElementById(\"" + key + "\",\"" + value + "\");\n");
        }

        for (Map.Entry<String, String> entry : simpleResultsJS.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            fr.write("\n" + key + "\n"+value + "\n");
        }

        fr.close();

    }

    //2n deep, no recursion
    public void simpleFrameRunner(HtmlPage htmlPage) throws IOException{

        List<FrameWindow> window = htmlPage.getFrames();
        Log.logger.info("Found frames: " + window.size());
        simpleParse(htmlPage);
        if(window.size() > 0){
            for (FrameWindow frameWindow : window) {
                HtmlPage subpage = (HtmlPage) frameWindow.getFrameElement().getEnclosedPage();
                simpleParse(subpage);
            }
        }
    }

    public void setUri(URL uri) {
        this.uri = uri;

    }
}
