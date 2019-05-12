package com.pure;

import com.pure.logger.Log;
import com.pure.matches.Match;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Scanner {

    private String malwareSamplesPath;
    private List<String> scannerResults = new LinkedList<>();
    private boolean noSignatureHits = false;
    private boolean yaraHadError = false;
    private List<Match> matchQueue = new LinkedList<>();
    private String jobFullPath;
    private String jobid;
    private String playBookName;

    /*
    getYear wscript replace +- 3years
     */

    public Scanner(String malwareSamplesPath, List<String> tags, String jobFullPath, String jobId, String playbookName) throws Exception {
        this.malwareSamplesPath = malwareSamplesPath;
        this.jobFullPath = jobFullPath;
        this.jobid = jobId;
        this.playBookName = playbookName;
        yaraWrapperProcess(tags);
        parseYaraInput();
    }

    private void parseYaraInput() throws Exception {

        if (scannerResults.isEmpty() && !yaraHadError) {

            noSignatureHits = true;

            Match match = new Match(jobFullPath, jobid);
            match.setRuleName("clean");
            match.setDescription("clean");

            matchQueue.add(match);

            Log.logger.info("\n" + malwareSamplesPath + " CLEAN ");

        } else {

            Match match = null;
            boolean acceptResultData = false;

            for (String result : scannerResults) {
                String[] currentLine = null;
                String[] metadata = null;

                if (result.contains(malwareSamplesPath)) {
                    result = result.replace(" [", "--").replace("] ", "--");
                    currentLine = result.split("--");
                    metadata = currentLine[1].replace("\"", "").split(",");

                    if(metadata.length != 2){
                        Log.logger.warning("Violation of rule integrity & ignoring rule:\n" + result);
                        acceptResultData = false;
                        continue;
                    }

                    if(match != null){
                        match.setMatchesFound(match.getMatchedRows().size());
                        matchQueue.add(match);
                    }
                    match = new Match(jobFullPath, jobid);
                    match.setRuleName(currentLine[0]);
                    match.setScore(Integer.valueOf(metadata[0].replace("score=", "")));
                    match.setDescription(metadata[1].replace("description=", ""));
                    acceptResultData = true;

                } else if (result.substring(0, 2).matches("0x") && acceptResultData) { //end index is exclusive

                    match.getMatchedRows().add(result);
                    //add last Match
                    if(scannerResults.get(scannerResults.size()-1).equals(result)){
                        match.setMatchesFound(match.getMatchedRows().size());
                        matchQueue.add(match);
                    }

                }
            }
        }

        //program Flow Ends if yaraHaderror
        if(yaraHadError){
            throw new Exception("^^ YARA failure ^^");
        }
    }

    private void yaraWrapperProcess(List<String> tags) throws IOException {

        Runtime runtime = Runtime.getRuntime();

        List<String> commandList = new ArrayList<>();
        commandList.add("yara");
        commandList.add("-m");
        commandList.add("-s");
        commandList.add("-L");
        commandList.add("-w");
        if(tags.size() != 0){
            for(String tag : tags){
                commandList.add("-t");
                commandList.add(tag);
            }
        }
        commandList.add("./yararules/"+playBookName);
        commandList.add("./malware/" + malwareSamplesPath + File.separator + "results");

        String[] commands = new String[commandList.size()];
        commands = commandList.toArray(commands);
        Log.logger.info("Initializing YARA " + malwareSamplesPath + File.separator + "results/ <all files>");
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

    public List<Match> getMatchQueue() {
        return matchQueue;
    }
}
