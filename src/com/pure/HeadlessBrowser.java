package com.pure;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.pure.logger.Log;
import com.pure.profiles.BaseInteretExplorer;

import java.io.IOException;
import java.net.URL;

public class HeadlessBrowser {

    public String thisJobUid = "";

    public HeadlessBrowser(String uid){
        thisJobUid = uid;
    }

    public HtmlPage httpAgent(URL uri) throws IOException {


        BaseInteretExplorer interetExplorer = new BaseInteretExplorer();
        //Debug.enumPlugins(interetExplorer.getWebClient());
        //Debug.enumFeatures(interetExplorer.getWebClient());
        Log.logger.info("(" + thisJobUid + ")" + "Starting job " + uri);
        WebClient webClient = interetExplorer.getWebClient();
        HtmlPage htmlPage = webClient.getPage(uri);
        WebResponse webResponse = htmlPage.getWebResponse();
        WebRequest webRequest = webResponse.getWebRequest();
        webClient.close();
        Log.logger.info("(" + thisJobUid + ")" + "Last page Navigated to " + htmlPage.getTitleText() + " status: "
                + htmlPage.getWebResponse().getStatusCode() + " date: " +
                htmlPage.getWebResponse().getResponseHeaderValue("Date"));


        return htmlPage;
    }

}
