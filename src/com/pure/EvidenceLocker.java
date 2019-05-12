package com.pure;

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
    private List<String> htmlTagsList = Arrays.asList("div","p", "object");
    private HashMap<Integer, List<String>> simpleResultsDOMbyId = new HashMap<>();
    private HashMap<Integer, List<String>> simpleResultsDOMbyClass = new HashMap<>();
    private HashMap<String, String> simpleResultsJS = new HashMap<>();
    private static String rootPath = System.getProperty("user.dir");
    private URL uri = null;
    private int posOfJsPointer = 0;
    private int posOfDomPointerById = 0;
    private int posOfDomPointerByClass = 0;
    private String folderName = null;
    private String fileName = null;
    private String currentJobFullPath = null;

    /*if class name not empty create class*/

    public void simpleParse(HtmlPage htmlPage)throws IOException {

        for(String tag : htmlTagsList) {
            DomNodeList<DomElement> domElements = htmlPage.getElementsByTagName(tag);
            for (DomElement domElement : domElements) {
                //asText = parseFrameContent
                if (domElement.getId().length() >0) {
                    //simpleFrameRunner
                    simpleResultsDOMbyId.put(posOfDomPointerById, Arrays.asList(domElement.getId(),
                            StringEscapeUtils.escapeEcmaScript(domElement.getTextContent())));
                    posOfDomPointerById++;
                }else if(domElement.getAttribute("class").length() > 0 &&
                domElement.getChildElementCount() == 0 && domElement.getTextContent().trim().length() >0){
                    //domElement.getAttribute("class"); domElement.getTextContent();
                    simpleResultsDOMbyClass.put(posOfDomPointerByClass, Arrays.asList(domElement.getAttribute("class"),
                            StringEscapeUtils.escapeEcmaScript(domElement.getTextContent())));
                    posOfDomPointerByClass++;
                }
            }
        }

        //Special case: Script htmlTagsList with ID
        DomNodeList<DomElement> domElements = htmlPage.getElementsByTagName("script");
        for (DomElement domElement : domElements) {
            if (domElement.getId().length() >0) {
                simpleResultsDOMbyId.put(posOfDomPointerById, Arrays.asList(domElement.getId(),
                        StringEscapeUtils.escapeEcmaScript(domElement.getTextContent())));
                posOfDomPointerById++;
            }
        }
        //Iterate script pieces, domelements from previous loop "script"
        for (DomElement domElement : domElements) {
            if(domElement.getTextContent().length() >0) {
                String stringToSeek = domElement.getTextContent();
                //Push js excluding whitelist
                if(exceptions.parallelStream().noneMatch(stringToSeek::contains)) {
                    simpleResultsJS.put("//" + posOfJsPointer, domElement.getTextContent());
                    posOfJsPointer++;
                }
            }
        }

    }
    /*Iterate all htmlTagsList**/
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

    public void createVirtualPage(String uid) throws IOException, Exception{

        if(simpleResultsDOMbyId.isEmpty() && simpleResultsJS.isEmpty() && simpleResultsDOMbyClass.isEmpty()){
            throw new Exception("Cannot create VirtualPage without content");
        }
        String timeStamp = new SimpleDateFormat("HH-mm-ssss").format(Calendar.getInstance().getTime());
        String folderStamp = new SimpleDateFormat("MM-dd_HH-mm-ssss").format(Calendar.getInstance().getTime());

        this.folderName = uri.getHost().replace(".","_")+"_"+folderStamp+"_"+uid;
        this.fileName = uri.getHost().replace(".","_") +"_"+ timeStamp +".js";
        this.currentJobFullPath = File.separator + "malware" + File.separator +
                folderName + File.separator;

        Path path = Paths.get(rootPath, File.separator + "malware" + File.separator +
                folderName + File.separator + fileName);

        Log.logger.info("Creating virtual page file: " + path.toString());
        File file = new File(path.toString());
        file.getParentFile().mkdir();
        FileWriter fr = new FileWriter(file, false);

        for (Map.Entry<Integer, List<String>> entry : simpleResultsDOMbyId.entrySet()) {
            //String key = entry.getKey(); //String value = entry.getValue();
            String key = entry.getValue().get(0);
            String value = entry.getValue().get(1);
            fr.write("document._addElementById(\"" + key + "\",\"" + value + "\");\n");
        }

        for (Map.Entry<Integer, List<String>> entry : simpleResultsDOMbyClass.entrySet()) {
            //String key = entry.getKey(); //String value = entry.getValue();
            String key = entry.getValue().get(0);
            String value = entry.getValue().get(1);
            fr.write("document._addElementByClass(\"" + key + "\",\"" + value + "\");\n");
        }

        for (Map.Entry<String, String> entry : simpleResultsJS.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            fr.write("\n" + key + "\n"+value + "\n");
        }

        fr.close();

    }

    //2n deep, no recursion, all frames
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

    public void parsePCAP(){}

    public void setUri(URL uri) {
        this.uri = uri;

    }

    public String getFolderName() {
        return folderName;
    }

    public String getFileName() {
        return fileName;
    }

    public String getCurrentJobFullPath() {
        return currentJobFullPath;
    }
}
