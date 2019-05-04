package com.pure;

import com.pure.logger.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class Scanner {

    private String malwareSamplesPath;
    private List<String> scannerResults = new LinkedList<>();
    private boolean noSignatureHits = false;
    private boolean yaraHadError = false;

    public Scanner(String malwareSamplesPath) throws IOException {
        this.malwareSamplesPath = malwareSamplesPath;
        yaraWrapperProcess();
        parseYaraInput();
    }

    private void parseYaraInput() {

        if (scannerResults.isEmpty() && !yaraHadError) {
            noSignatureHits = true;
            Log.logger.info("\n" + malwareSamplesPath + " CLEAN ");
        } else {
            String results = null;
            for (String result : scannerResults){
                results += result + "\n";
            }
            Log.logger.info(results);
        }
    }

    /**
     * eval_*.js
     * urls.json
     * >> out
     */

    /*
    getYear
    replace
     */
    private void yaraWrapperProcess() throws IOException {

        Runtime runtime = Runtime.getRuntime();
        String tag = "";
        String[] commands = {"yara", "-m","-s","-L","-w","./yararules/run.yar",
                "./malware/" + malwareSamplesPath + File.separator + "results"};
        Log.logger.info("Initializing YARA " + malwareSamplesPath);
        Process process;

        process = runtime.exec(commands);
        Log.logger.info("Process at " + process.pid() + " | CMD: " + process.info().commandLine());
        BufferedReader lineReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        lineReader.lines().forEach(scannerResults::add);

        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        errorReader.lines().forEach(line -> catchErrorStreamAndLog(line));

    }

    private void catchErrorStreamAndLog(String line){

        Log.logger.severe(line);
        yaraHadError = true;
    }

    public boolean isClean() {
        return noSignatureHits;
    }
}
