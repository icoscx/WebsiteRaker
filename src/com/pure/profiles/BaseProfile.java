package com.pure.profiles;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.util.WebConnectionWrapper;
import com.pure.logger.Log;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

class BaseProfile{

    protected BrowserVersion.BrowserVersionBuilder browserVersionBuilder = null;
    protected WebClient webClient = null;
    private Queue<String> staticContent = new LinkedList<>();

    protected void webClientFeatures(){

        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setAppletEnabled(true);
        webClient.getOptions().setCssEnabled(true);
        webClient.getOptions().setRedirectEnabled(true);
        webClient.getOptions().setUseInsecureSSL(true);
        //not needed, malware jail does dynamic part
        //webClient.waitForBackgroundJavaScript(1000);
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiesEnabled(true);
        webClient.setCookieManager(cookieManager);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setTimeout(5000);

    }

    protected void setTransactionMonitor(String uid){

        Log.logger.info("[" + uid + "]" + "Injecting Transaction Monitor");

        webClient.setWebConnection(new WebConnectionWrapper(webClient) {
            @Override
            public WebResponse getResponse(final WebRequest request) throws IOException {
                WebResponse response = super.getResponse(request);

                staticContent.add(response.getResponseHeaders().toString() + "\n"
                + response.getStatusCode() + " "+ response.getStatusMessage() + "\n"
                        + "CurrentURL: " + request.getUrl() + "\n"
                        + response.getContentAsString() + "\n"
                );

                return response;
            }
        });

    }

    public Queue<String> getStaticContent() {
        return staticContent;
    }

    public void setStaticContent(Queue<String> staticContent) {
        this.staticContent = staticContent;
    }

}
