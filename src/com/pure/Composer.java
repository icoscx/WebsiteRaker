package com.pure;

import com.pure.logger.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Composer {

    private String fullPathWithDot;
    private String fileName;
    private String dataToWrite = "";
    private boolean jobFailed = false;

    public Composer(String fullPathWithDot, String fileName) {
        this.fullPathWithDot = fullPathWithDot;
        this.fileName = fileName;
    }

    public void executor(String folder, String configFile) throws IOException {

        Runtime runtime = Runtime.getRuntime();

        List<String> commandList = new ArrayList<>();
        commandList.add("node");
        commandList.add("./malware-jail/jailme.js");
        commandList.add("--h404");
        commandList.add("-t");
        commandList.add("15000");
        if(configFile.length() != 0) {
            commandList.add("-c");
            commandList.add(configFile);
        }
        commandList.add("./malware/" + folder + File.separator + fileName);

        String[] commands = new String[commandList.size()];
        commands = commandList.toArray(commands);
        /**
        String[] commands  = {"node", "./malware-jail/jailme.js", "--h404",
                "./malware/" + folder + File.separator + fileName};
        */
        Log.logger.info("Processing command for NodeJS in folder " + folder + " and file " + fileName);
        Process process = null;

        process = runtime.exec(commands);
        Log.logger.info("Process at " + process.pid() + " | CMD: " + process.info().commandLine());

        BufferedReader lineReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        lineReader.lines().forEach(dataLine -> caseWriter(dataLine));

        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        errorReader.lines().forEach(line -> catchErrorStreamAndLog(line));

        if(!jobFailed){
            exportCaseResults();
        }else{
            //catch JS failures only, the sandbox may continiue
            Log.logger.warning("Uncaught Exception occured, scanning results might be innacurate: \n"
                    + dataToWrite);
            exportCaseResults();
        }
    }

    private void catchErrorStreamAndLog(String line){

        Log.logger.severe(line);
        jobFailed = true;
    }


    private void caseWriter(String dataLine){
        if(dataLine.contains("Uncaught Exception") || dataLine.contains("Exception occured")){
            jobFailed = true;
        }
        dataToWrite += dataLine + "\n";
    }

    private void exportCaseResults() throws IOException {

        File file = new File(fullPathWithDot + "results" + File.separator +
                fileName.replace(".js", ".dynamic.log"));
        Log.logger.info("Exporting results to: /" + "results" + File.separator +
                fileName.replace(".js", ".dynamic.log"));
        file.getParentFile().mkdir();

        FileWriter fr = new FileWriter(file, true);
        fr.write(dataToWrite);
        fr.close();

    }

    public void exportStaticResults(Queue<String> staticQueue) throws IOException {

        File file = new File(fullPathWithDot + "results" + File.separator +
                fileName.replace(".js", ".static.log"));
        Log.logger.info("Exporting results to: /" + "results" + File.separator +
                fileName.replace(".js", ".static.log"));
        file.getParentFile().mkdir();
        FileWriter fr = new FileWriter(file, true);

        while (!staticQueue.isEmpty()){
            fr.write(staticQueue.remove());
        }

        fr.close();

    }

    public void exportURLaddressesFromDynamicAnal(String folder) throws Exception {

        File f = new File("./malware/" + folder + File.separator + "output" + File.separator + "urls.json");
        if(f.exists() && !f.isDirectory()) {

            File newFile = new File("./malware/" + folder + File.separator + "results" + File.separator + "urls.json");
            java.util.Scanner s = new Scanner(f);
            FileWriter fr = new FileWriter(newFile, true);

            while (s.hasNextLine()){
                fr.write(s.nextLine()+"\n");
            }
            s.close();
            fr.close();

            Log.logger.info("Dynamic URLs extracted from " + folder + File.separator + fileName + " and saved to "
            + f.getPath());
        }else{
            Log.logger.info("No Dynamic URLs extracted from: " + folder + File.separator + fileName);
        }

    }

}
