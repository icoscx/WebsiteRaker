package com.pure;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.pure.logger.Log;
import com.pure.profiles.BaseInteretExplorer;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;

public class HeadlessBrowser {

    public String thisJobUid = "";
    private Queue<String> staticContent = new LinkedList<>();

    public HeadlessBrowser(String uid){
        thisJobUid = uid;
    }

    public HtmlPage httpAgent(URL uri) throws IOException {


        BaseInteretExplorer interetExplorer = new BaseInteretExplorer(thisJobUid);
        //Debug.enumPlugins(interetExplorer.getWebClient());
        //Debug.enumFeatures(interetExplorer.getWebClient());
        Log.logger.info("(" + thisJobUid + ")" + "Starting job " + uri);
        WebClient webClient = interetExplorer.getWebClient();
        HtmlPage htmlPage = webClient.getPage(uri);
        WebResponse webResponse = htmlPage.getWebResponse();
        WebRequest webRequest = webResponse.getWebRequest();
        webClient.close();
        this.staticContent = interetExplorer.getStaticContent();
        Log.logger.info("(" + thisJobUid + ")" + "Last page Navigated to " + webRequest.getUrl() + " status: "
                + webResponse.getStatusCode() +" "+ webResponse.getStatusMessage() + " date: " +
                webResponse.getResponseHeaderValue("Date"));

        return htmlPage;
    }

    public Queue<String> getStaticContent() {
        return staticContent;
    }

    public void setStaticContent(Queue<String> staticContent) {
        this.staticContent = staticContent;
    }
}
