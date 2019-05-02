package com.pure;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.pure.locker.EvidenceLocker;
import com.pure.logger.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;

public class Composer {

    //GetBrowserProfileFrom Profiles
    public void setBrowserProfile(BrowserVersion firefox52) {}

    public void executor(String folder, String path) {

        Runtime runtime = Runtime.getRuntime();
        String[] commands  = {"node", "./malware-jail/jailme.js", "--h404",
                "./malware/" + folder + File.separator + path};
        Process process = null;
        try {
            process = runtime.exec(commands);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader lineReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        lineReader.lines().forEach(Log.logger::info);

        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        errorReader.lines().forEach(Log.logger::severe);

    /*wscript_only*/
    }
}
