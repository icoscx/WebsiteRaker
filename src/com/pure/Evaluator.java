package com.pure;

import com.pure.logger.Log;
import com.pure.matches.Match;

import java.util.List;
import java.util.Queue;

public class Evaluator {

    public void judgment(List<Match> parsedMatches){

        parsedMatches.forEach(match -> Log.logger.info(match.getDescription() + " || " + match.getScore() + "\n"));

        //TODO: WriteJson

    }

}
