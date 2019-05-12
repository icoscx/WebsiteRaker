package com.pure;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.pure.misc.Functions;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class WebsiteValidator {

    public static boolean setTrainingModeOn = false;
    public String thisJobUid = "";

    public WebsiteValidator(String uid){
        thisJobUid = uid;
    }

    public void rootFunctionsCaller(URL uri) throws IOException, Exception {

        HeadlessBrowser headlessBrowser = new HeadlessBrowser(thisJobUid);
        HtmlPage htmlPage = headlessBrowser.httpAgent(uri);
        EvidenceLocker evidenceLocker = new EvidenceLocker();
        evidenceLocker.setUri(uri);
        evidenceLocker.simpleFrameRunner(htmlPage);
        evidenceLocker.createVirtualPage(thisJobUid);
        Composer composer = new Composer("." + evidenceLocker.getCurrentJobFullPath(),
                evidenceLocker.getFileName());
        //case1: onlyMainConfig case2: Wscript only (needs IE)
        String browserType = Functions.rakerConfigGetBrowserProfile();
        String getCurrentConfig = Functions.extendBasicConfig(evidenceLocker.getFolderName(),
                "config.json", browserType);
        // ./config.json
        composer.executor(evidenceLocker.getFolderName(),"./"+getCurrentConfig);
        composer.exportStaticResults(headlessBrowser.getStaticContent());
        List<String> tags = Functions.rakerConfigGetYaraTags();
        String playBookName = Functions.rakerConfigGetPlaybookName();
        Scanner scanner = new Scanner(evidenceLocker.getFolderName(), tags,
                evidenceLocker.getCurrentJobFullPath(), thisJobUid, playBookName);
        Evaluator evaluator = new Evaluator();
        evaluator.judgmentToCSV(scanner.getMatchQueue());

    }

}
