package com.pure;

import com.pure.logger.Log;
import com.pure.matches.Match;

import java.util.Queue;

public class Evaluator {

    public void judgment(Queue<Match> parsedMatches){

        parsedMatches.forEach(match -> Log.logger.info(match.getDescription() + " || " + match.getScore() + "\n"));

        

    }

}
