package com.pure;

import com.pure.logger.Log;
import com.pure.matches.Match;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class Evaluator {

    private int completeScore = 0;

    public void judgment(List<Match> parsedMatches) throws Exception{

        File file = new File("./results.csv");
        FileWriter fr = new FileWriter(file, true);

        for(Match match : parsedMatches){
            completeScore += match.getScore();
        }
        String verdict = "CLEAN";
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
    }

    public void fullWriteToJson(List<Match> parsedMatches){

        for(Match match : parsedMatches){

            System.out.println("found " + match.getMatchesFound());
            System.out.println("desc " + match.getDescription());
            System.out.println("score: " + match.getScore());
            System.out.println("ruleName: " + match.getRuleName());
            System.out.println("jobPath " + match.getCurrentJobFull());

            for(String s : match.getMatchedRows()){
                System.out.print("[" + s + "]");
            }
        }
    }

}
