package com.pure;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.pure.locker.EvidenceLocker;
import com.pure.logger.Log;
import com.pure.profiles.BaseInteretExplorer;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;

public class Main {

    public static void main(String[] args) {

        //htmlunit off
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        //initLogging
        Log.enableLog();
        //simpleArgs parser
        if(args.length > 0){
            for(int ii = 0; ii < args.length; ii++){
                switch (args[ii]){
                    case "-h":
                        printHelp();
                        break;
                    default:
                        System.exit(0);
                }
            }
        }
        //String hostToVisit = args[0];
        String hostToVisit = "http://localhost/angler.html";
        try {
            URL url = new URL(hostToVisit);
            httpAgentz(url);
        }catch (Exception e){
            Log.logger.log(Level.SEVERE,e.getCause() + e.getMessage(), e);
        }
    }
    //edge: if (typeof document == "undefined")

    public static void httpAgentz(URL uri) throws IOException, Exception {

        BaseInteretExplorer interetExplorer = new BaseInteretExplorer();
        //Debug.enumPlugins(interetExplorer.getWebClient());
        //Debug.enumFeatures(interetExplorer.getWebClient());
        Log.logger.info("Starting job " + uri);
        WebClient webClient = interetExplorer.getWebClient();
        HtmlPage htmlPage = webClient.getPage(uri);
        WebResponse webResponse = htmlPage.getWebResponse();
        WebRequest webRequest = webResponse.getWebRequest();

        webClient.close();
        Log.logger.info("Last page Navigated to " + htmlPage.getTitleText() + " status: "
                + htmlPage.getWebResponse().getStatusCode() + " date: " +
                htmlPage.getWebResponse().getResponseHeaderValue("Date"));

        EvidenceLocker evidenceLocker = new EvidenceLocker();
        evidenceLocker.setUri(uri);
        evidenceLocker.simpleFrameRunner(htmlPage);
        evidenceLocker.createVirtualPage();
        Integer jobId = 1;
        Composer composer = new Composer();
        composer.executor(evidenceLocker.getFolderName(),
                evidenceLocker.getFileName());


    }

    public static void crawler(URL hostToVisit) throws IOException, ScriptException {

        /*
         List<TopLevelWindow> lastWindow = webClient.getTopLevelWindows();
        History history = lastWindow.get(0).getHistory();
        Log.logger.info(String.valueOf(history.getLength()));


        ContentExtractor contentExtractor = new ContentExtractor();
        Composer composer = new Composer();
        composer.setBrowserProfile(FIREFOX_52);
        JSfile jSfile = composer.createTargetJavascriptFile("malware_" + hostToVisit + ".js");
        //this._addElementById = function(id, content)
        //this._addElementByClass = function(cls, content)
        jSfile.appendDOM(contentExtractor.getDOMList());
        composer.createVirtualPage(jSfile);
        //Process p=Runtime.getRuntime().exec("")
        composer.execute("node jailme.js -b FF_W10 -c config.json -t 10000 --down=y " + jSfile.getNameOfFile());
        */
    }

    public static void printHelp(){
        System.out.println("Usage: \n java -j raker.jar [full url] [Options -a -b -s]\n Options: ");
    }

}
