package com.pure;

import com.pure.logger.Log;
import com.pure.misc.Functions;

import java.net.URL;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

public class Main {

    public static volatile Queue<String> jobs = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) {

        //htmlunit off
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        //initLogging
        synchronized (Log.logger) {
            //scrambled eggs log for debug only
            Log.enableProgramFlowLog();
        }

        ExecutorService executor = Executors.newFixedThreadPool(1);

        try {

            jobs.addAll(Functions.parsedInputFile());

            while(!jobs.isEmpty()) {
                UUID idOne = UUID.randomUUID();
                String[] shortUID = idOne.toString().split("-");
                String uid = shortUID[0].toUpperCase();
                String job = jobs.remove();
                executor.submit(() -> {
                    try {
                        //For Training mode use 1 thread!
                        WebsiteValidator.setTrainingModeOn = true;
                        WebsiteValidator ws = new WebsiteValidator(uid);
                        ws.rootFunctionsCaller(new URL(job));
                    } catch (Exception e) {
                        Log.logger.log(Level.SEVERE, e.getMessage(), e);
                    }
                });
            }

        }catch (Exception e){
            Log.logger.log(Level.SEVERE, e.getMessage(), e);
        }

        /*
        Future<WebsiteValidator> tsk1 = executor.submit(() -> {
            System.out.println("Callable and return");
            return null;
        });
        */
        executor.shutdown();
    }

}
