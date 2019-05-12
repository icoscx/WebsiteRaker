package com.pure.profiles;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.PluginConfiguration;
import com.gargoylesoftware.htmlunit.WebClient;
import com.pure.logger.Log;

public class BaseInteretExplorer extends BaseProfile {

    public BaseInteretExplorer(String uid){
        initiate();
        pluginConfig();
        super.webClientFeatures();
        super.setTransactionMonitor(uid);
    }

    private void initiate(){

        browserVersionBuilder = new BrowserVersion.BrowserVersionBuilder(BrowserVersion.INTERNET_EXPLORER);
        browserVersionBuilder.setOnLine(true);
        webClient = new WebClient(browserVersionBuilder.build());
        Log.logger.info("Default IE Selected: " + webClient.getBrowserVersion().getUserAgent());

    }

    private void pluginConfig(){

        webClient.getBrowserVersion().getPlugins().clear();
        PluginConfiguration pluginConfiguration = new PluginConfiguration(
            "Shockwave Flash", "Shockwave Flash 32.0 r0",
                "32.0.0.171", "Flash.ocx"
        );
        PluginConfiguration.MimeType mimeType_swf = new PluginConfiguration.MimeType(
                "application/x-shockwave-flash", "Shockwave Flash",
                "swf"
        );
        PluginConfiguration.MimeType mimeType_spl = new PluginConfiguration.MimeType(
                "application/futuresplash", "Shockwave Flash",
                "spl"
        );
        pluginConfiguration.getMimeTypes().add(mimeType_swf);
        pluginConfiguration.getMimeTypes().add(mimeType_spl);
        webClient.getBrowserVersion().getPlugins().add(pluginConfiguration);

    }

    public WebClient getWebClient(){
        return this.webClient;
    }


}
