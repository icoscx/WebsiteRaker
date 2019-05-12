package com.pure.misc;

import com.gargoylesoftware.htmlunit.PluginConfiguration;
import com.gargoylesoftware.htmlunit.WebClient;
import com.pure.logger.Log;

public class Debug {

    static void enumPlugins(WebClient webClient){

        for(PluginConfiguration plugin : webClient.getBrowserVersion().getPlugins()){
            Log.logger.info("desc: " + plugin.getDescription()
                    + " fnma: " + plugin.getFilename() + " name:" + plugin.getName() + " ver:" + plugin.getVersion()
            );
            for(PluginConfiguration.MimeType mimeType : plugin.getMimeTypes()){
                Log.logger.info(mimeType.getDescription() + "||" + mimeType.getSuffixes() + "||" + mimeType.getType());
            }
        }

    }

    static void enumFeatures(WebClient webClient){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(webClient.getBrowserVersion().getUserAgent() + "|\n"
                + webClient.getBrowserVersion().getApplicationCodeName() + "|\n"
                + webClient.getBrowserVersion().getApplicationCodeName() + "|\n"
                + webClient.getBrowserVersion().getApplicationName() + "|\n"
                + webClient.getBrowserVersion().getApplicationMinorVersion() + "|\n"
                + webClient.getBrowserVersion().getBrowserLanguage() + "|\n"
                + webClient.getBrowserVersion().getBuildId()  + "|\n"
                + webClient.getBrowserVersion().getApplicationVersion() + "|\n"
                + webClient.getBrowserVersion().getCpuClass() + "|\n"
                + webClient.getBrowserVersion().getCssAcceptHeader() + "|\n"
                + webClient.getBrowserVersion().getHtmlAcceptHeader() + "|\n"
                + webClient.getBrowserVersion().getImgAcceptHeader() + "|\n"
                + webClient.getBrowserVersion().getNickname() + "|\n"
                + webClient.getBrowserVersion().getPlatform() + "|\n"
                + webClient.getBrowserVersion().getProductSub() + "|\n"
                + webClient.getBrowserVersion().getScriptAcceptHeader() + "|\n"
                + webClient.getBrowserVersion().getSystemLanguage() + "|\n"
                + webClient.getBrowserVersion().getUserLanguage() + "|\n"
                + webClient.getBrowserVersion().getXmlHttpRequestAcceptHeader() + "|\n"
                + webClient.getBrowserVersion().getVendor() + "|\n"
                + webClient.getBrowserVersion().getPlugins().size() + "|\n"
                + webClient.getBrowserVersion().getBrowserVersionNumeric() + "|\n"
                + webClient.getBrowserVersion().getBuildId() + "|\n"
                + webClient.getBrowserVersion().isOnLine() + "|\n"
                + webClient.getBrowserVersion().getPixesPerChar()
        );

        Log.logger.info(stringBuilder.toString());

    }

}
