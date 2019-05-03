package com.pure;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.pure.locker.EvidenceLocker;
import com.pure.logger.Log;

import java.io.*;
import java.util.List;
import java.util.logging.Level;

public class Composer {

    private String fullPathWithDot;
    private String fileName;
    private String dataToWrite;

    public Composer(String fullPathWithDot, String fileName) {
        this.fullPathWithDot = fullPathWithDot;
        this.fileName = fileName;
    }

    //GetBrowserProfileFrom Profiles
    public void setBrowserProfile(BrowserVersion firefox52) {}

    public void executor(String folder, String filename) {

        Runtime runtime = Runtime.getRuntime();
        String[] commands  = {"node", "./malware-jail/jailme.js", "--h404",
                "./malware/" + folder + File.separator + filename};
        Process process = null;
        boolean jobFailed = false;
        try {
            process = runtime.exec(commands);
            BufferedReader lineReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            lineReader.lines().forEach(dataLine -> caseWriter(dataLine));
            //gatherData FOR YARA and to jobNotFailed

            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            errorReader.lines().forEach(Log.logger::severe);
        } catch (IOException e) {
            Log.logger.severe("#############################\nSandBox job: " +
                    folder + File.separator + filename + " - FAILED: "
                    + e.getCause() + "||" + e.getMessage() + "#############################\n");

            jobFailed = true;
        }
        //TODO:
        if(!jobFailed){
            exportCaseResults();
        }
    /*wscript_only*/
    }

    private void exportCaseResults(){

        File file = new File(fullPathWithDot + "results" + File.separator +
                fileName.replace(".js", ".log"));
        file.getParentFile().mkdir();
        try {
            FileWriter fr = new FileWriter(file, false);
            fr.write(dataToWrite);
            fr.close();
        } catch (IOException e) {
            Log.logger.severe(e.getCause() + " - " + e.getMessage());
        }
    }

    private void caseWriter(String dataLine){
        dataToWrite += dataLine + "\n";
    }
}
