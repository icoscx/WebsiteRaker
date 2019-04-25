package com.pure.logger;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {

    public static final Logger logger = Logger.getLogger("allOfEm");
    private static FileHandler fileHandler = null;
    private static ConsoleHandler consoleHandler = null;

    public static void enableLog(){

        /**Disabling the parent handlers (removes default console
        handling) and adding our own handler that will only show the short
        message makes the error output much more manageable.*/

        //see false
        logger.setUseParentHandlers(true);

        try {
            fileHandler = new FileHandler("./allOfEm.log", false);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        LogFormatRaker formatter = new LogFormatRaker();

        fileHandler.setFormatter(formatter);

        logger.addHandler(fileHandler);

        consoleHandler = new ConsoleHandler();
        //consoleHandler.setFormatter(formatter);
        logger.addHandler(consoleHandler);
        consoleHandler.setLevel(Level.SEVERE);
        fileHandler.setLevel(Level.ALL);

        // Default level is ALL, no Filter. 1. logger lvl -> 2. Handler lvl/filter
        logger.setLevel(Level.ALL);
        //Thread.currentThread().getStackTrace()

    }

    /**
    public static void stackTraceToString(){

        Exception exception = new Exception("test");
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        Log.logger.log(Level.WARNING, sw.toString());
    }
     */

}
