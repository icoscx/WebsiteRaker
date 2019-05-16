package com.pure;

import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Consumer;

public class Mdebug {

    public static void fetchFiles(File dir, Consumer<File> fileConsumer) {

        if (dir.isDirectory()) {
            for (File file1 : dir.listFiles()) {
                fetchFiles(file1, fileConsumer);
            }
        } else {
            fileConsumer.accept(dir);
        }
    }

    public static void main(String[] args) {


        WebClient webClient = new WebClient();
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiesEnabled(true);
        webClient.setCookieManager(cookieManager);
        webClient.getOptions().setRedirectEnabled(true);

        try {
            HtmlPage htmlPage = webClient.getPage("http://localhost/1.php");
            WebResponse webResponse = htmlPage.getWebResponse();
            WebRequest webRequest = webResponse.getWebRequest();
            System.out.println(webResponse.getResponseHeaders().toString());
            System.out.println(webResponse.getWebRequest().getAdditionalHeaders().toString());
            System.out.println(webResponse.getResponseHeaderValue("Set-Cookie"));
            System.out.println(webResponse.getResponseHeaders().toString());


        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
        try {
            File file = new File("/var/www/html/malware_samples/");
            fetchFiles(file, f -> buildFile(f.getAbsolutePath()));
        }catch (Exception e){
            System.err.println(e.getMessage());
            System.err.println(e.getCause());
            e.printStackTrace();
        }
         */
    }

    static void buildFile(String fullpath){

        try {

            File newFile = new File("/var/www/html/list.txt");
            FileWriter fr = new FileWriter(newFile, true);
            fr.write(fullpath + "\n");
            fr.close();

        }catch (Exception e){
            System.err.println(e.getMessage());
            System.err.println(e.getCause());
            e.printStackTrace();
        }
    }


}
