package com.pure.misc;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Functions {

    public static Queue<String> parsedInputFile() throws FileNotFoundException {

        File input = new File("./input.txt");
        Scanner s = new Scanner(input);
        Queue<String> aQueue  = new LinkedList<>();
        while (s.hasNextLine()){
            if(!s.toString().contains("http")){
                aQueue.add("http://" + s.nextLine());
            }else{
                aQueue.add(s.nextLine());
            }
        }
        s.close();

        return aQueue;
    }

    public static String extendBasicConfig(String jobNameAkaFolderName,
                                               String configFileToReadAndExtend) throws Exception {

        File initialFile = new File("./malware-jail/" + configFileToReadAndExtend);
        InputStream is = new FileInputStream(initialFile);
        JSONTokener tokener = new JSONTokener(is);
        JSONObject object = new JSONObject(tokener);
        String oldValue = object.getString("save_files");
        //localhost_10-17-0046
        String outputFolder = oldValue + jobNameAkaFolderName + File.separator + "output" + File.separator;
        object.remove("save_files");
        object.put("save_files", outputFolder);

        boolean mkdirs = (new File("./malware/" + jobNameAkaFolderName + File.separator
                + "output" + File.separator)).mkdirs();
        if(!mkdirs){
            //Log.logger.severe("Failed to create output folder for job");
            throw new Exception("Failed to create output folder for job");
        }

        Path path = Paths.get("./malware-jail/" + "config_" + jobNameAkaFolderName + ".json");

        File file = new File(path.toString());
        file.getParentFile().mkdir();
        FileWriter fr = new FileWriter(file, false);
        fr.write(object.toString());
        fr.close();
        //delete extended temp conf file
        file.deleteOnExit();

        /*
        System.out.println("Name: " + object.getString("fname"));
        JSONArray courses = object.getJSONArray("data");
        for (int i = 0; i < data.length(); i++) {
            System.out.println("  - " + courses.get(i));
        }
         */

       return "config_" + jobNameAkaFolderName + ".json";
    }

    public static void iterateMapOfURIParams(URL url) throws UnsupportedEncodingException {
        Map<String, List<String>> stuff = splitQuery(url);
        Set<String> keys = stuff.keySet();
        for(String key : keys){

            System.out.println(key + stuff.get(key));

        }
    }

    public static Map<String, List<String>> splitQuery(URL url) throws UnsupportedEncodingException {
        final Map<String, List<String>> query_pairs = new LinkedHashMap<String, List<String>>();
        final String[] pairs = url.getQuery().split("&");
        for (String pair : pairs) {
            final int idx = pair.indexOf("=");
            final String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;
            if (!query_pairs.containsKey(key)) {
                query_pairs.put(key, new LinkedList<String>());
            }
            final String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : null;
            query_pairs.get(key).add(value);
        }
        return query_pairs;
    }

}
