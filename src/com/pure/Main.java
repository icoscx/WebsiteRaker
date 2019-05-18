package com.pure;

import com.pure.logger.Log;
import com.pure.misc.Functions;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

public class Main {

    private static volatile Queue<String> jobs = new ConcurrentLinkedQueue<>();
    private static boolean training = false;
    private static int threads = 0;

    public static void main(String[] args) {

        //htmlunit off
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        //initLogging
        synchronized (Log.logger) {
            //scrambled eggs log for debug only
            Log.enableProgramFlowLog();
        }

        try {
            if(Functions.rakerConfigGetMode().toLowerCase().equals("true")){
                training = true;
            }
            threads = Functions.rakerConfigGetThreads();
        } catch (FileNotFoundException e) {
            Log.logger.log(Level.SEVERE, e.getMessage(), e);
        }

        ExecutorService executor = Executors.newFixedThreadPool(threads);

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
                        WebsiteValidator.setTrainingModeOn = training;
                        WebsiteValidator ws = new WebsiteValidator(uid);
                        ws.rootFunctionsCaller(new URL(job));
                    } catch (Exception e) {
                        Log.logger.log(Level.SEVERE, e.getMessage(), e);
                    }
                });
            }

        }catch (Exception e){
            Log.logger.log(Level.SEVERE, e.getMessage(), e);
        }finally {
            executor.shutdown();
        }

        /*
        Future<WebsiteValidator> tsk1 = executor.submit(() -> {
            System.out.println("Callable and return");
            return null;
        });
        */

    }

}
