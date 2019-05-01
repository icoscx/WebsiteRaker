package com.pure;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import java.io.IOException;
import java.util.List;

public class Composer {
    //GetBrowserProfileFrom Profiles
    public void setBrowserProfile(BrowserVersion firefox52) {}

    public void executor() throws IOException, InterruptedException {
        Process p=Runtime.getRuntime().exec("ls");
        p.waitFor();
    }
}
