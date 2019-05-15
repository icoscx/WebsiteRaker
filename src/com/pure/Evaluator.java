package com.pure;

import com.pure.logger.Log;
import com.pure.matches.Match;
import com.pure.misc.Functions;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Evaluator {

    private int completeScore = 0;

    public void judgmentToCSV(List<Match> parsedMatches) throws Exception{

        File file = new File("./results.csv");
        FileWriter fr = new FileWriter(file, true);

        for(Match match : parsedMatches){
            completeScore += match.getScore();
        }
        String verdict = "no_matches";
        if(completeScore <= 50 && completeScore > 1){
            verdict = "Undetermined";
        }else if(completeScore <= 74 && completeScore > 51){
            verdict = "Suspicious";
        }else if(completeScore >= 75){
            verdict = "Malicious";
        }

        String writeable = parsedMatches.get(0).getJobid() + "," + parsedMatches.get(0).getCurrentJobFull()+ "results/ <all files> "+
                "," + completeScore + "," + verdict + "\n";

        String writeableLog = parsedMatches.get(0).getJobid() + "," + parsedMatches.get(0).getCurrentJobFull()+ "results/ <all files> "+
                "," + completeScore + "," + verdict;

        fr.write(writeable);
        fr.close();
        Log.logger.info(writeableLog);

        if(WebsiteValidator.setTrainingModeOn){
            Log.logger.warning("Yara Debugger is enabled, massive log flood. USE for training a playbook!");
            Functions.fullWriteToJson(parsedMatches);
        }else{
            Runtime runtime = Runtime.getRuntime();
            List<String> commandList = new ArrayList<>();
            commandList.add("rm");
            commandList.add("-rf");
            commandList.add("." + parsedMatches.get(0).getCurrentJobFull());
            String[] commands = new String[commandList.size()];
            commands = commandList.toArray(commands);
            Process process;
            process = runtime.exec(commands);
            Log.logger.info("Delete: " + process.info().commandLine());
        }
    }

}
