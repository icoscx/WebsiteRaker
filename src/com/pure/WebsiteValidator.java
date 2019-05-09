package com.pure;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.pure.locker.EvidenceLocker;
import com.pure.logger.Log;
import com.pure.misc.Functions;
import com.pure.profiles.BaseInteretExplorer;

import java.io.IOException;
import java.net.URL;

public class WebsiteValidator {

    public String thisJobUid = "";

    public WebsiteValidator(String uid){
        thisJobUid = uid;
    }

    public void httpAgent(URL uri) throws IOException, Exception {

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
        EvidenceLocker evidenceLocker = new EvidenceLocker();
        evidenceLocker.setUri(uri);
        evidenceLocker.simpleFrameRunner(htmlPage);
        evidenceLocker.createVirtualPage(thisJobUid);
        Composer composer = new Composer("." + evidenceLocker.getCurrentJobFullPath(),
                evidenceLocker.getFileName());
        //case1: onlyMainConfig case2: Wscript only (needs IE)
        String getCurrentConfig = Functions.extendBasicConfig(evidenceLocker.getFolderName(),
                "config.json");
        // ./config.json
        composer.executor(evidenceLocker.getFolderName(),"./"+getCurrentConfig);
        String[] tags = {"location", "ansip"};
        Scanner scanner = new Scanner(evidenceLocker.getFolderName(), tags,
                evidenceLocker.getCurrentJobFullPath(), thisJobUid);
        Evaluator evaluator = new Evaluator();
        evaluator.judgment(scanner.getMatchQueue());

    }

}
