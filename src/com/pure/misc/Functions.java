package com.pure.misc;

import com.pure.matches.Match;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Functions {

    private static final String rakerConfigName = "./rakerConfig.json";

    public static void fullWriteToJson(List<Match> parsedMatches) throws IOException {

        File file = new File("./systemlog/resultsDebugYara_"+ parsedMatches.get(0).getJobid()+".json");
        FileWriter fr = new FileWriter(file, true);

        JSONArray finalJsonArray = new JSONArray();

        for(Match match : parsedMatches){

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("jobID", match.getJobid());
            jsonObject.put("foundMatches" , match.getMatchesFound());
            jsonObject.put("description" , match.getDescription());
            jsonObject.put("totalScore" ,match.getScore());
            jsonObject.put("ruleName", match.getRuleName());
            jsonObject.put("jobPath", match.getCurrentJobFull());

            jsonObject.put("matchedRows", match.getMatchedRows());

            finalJsonArray.put(jsonObject);

        }
        JSONObject finalJsonObject = new JSONObject();
        finalJsonObject.put("match", finalJsonArray);

        fr.write(finalJsonObject.toString());
        fr.close();
    }

    public static JSONObject rakerConfig() throws FileNotFoundException {

        File initialFile = new File(rakerConfigName);
        InputStream is = new FileInputStream(initialFile);
        JSONTokener tokener = new JSONTokener(is);
        JSONObject object = new JSONObject(tokener);

        return object;
    }

    public static String rakerConfigGetBrowserProfile() throws FileNotFoundException {
        JSONObject object = rakerConfig();

        return object.getString("browser_type");
    }

    public static String rakerConfigGetPlaybookName() throws FileNotFoundException {

        JSONObject object = rakerConfig();

        return object.getString("playbook_name");
    }

    public static List<String> rakerConfigGetYaraTags() throws FileNotFoundException {


        JSONObject object = rakerConfig();

        JSONArray array = object.getJSONArray("yara_tags");

        List<String> listOfTags = new ArrayList<>();

        for(int i = 0; i<array.length(); i++){
            listOfTags.add(array.getString(i));
        }

        return listOfTags;
    }

    public static Queue<String> parsedInputFile() throws FileNotFoundException {

        File input = new File("./input.txt");
        Scanner s = new Scanner(input);
        Queue<String> aQueue  = new LinkedList<>();
        while (s.hasNextLine()){

            String parsable = s.nextLine();
            if(!parsable.contains("http:")){
                parsable = "http://" + parsable;
            }
            aQueue.add(parsable);

        }
        s.close();

        return aQueue;
    }

    public static String extendBasicConfig(String jobNameAkaFolderName,
                                               String configFileToReadAndExtend,
                                           String browserType) throws Exception {

        File initialFile = new File("./malware-jail/" + configFileToReadAndExtend);
        InputStream is = new FileInputStream(initialFile);
        JSONTokener tokener = new JSONTokener(is);
        JSONObject object = new JSONObject(tokener);
        String oldValue = object.getString("save_files");
        //localhost_10-17-0046
        String outputFolder = oldValue + jobNameAkaFolderName + File.separator + "output" + File.separator;
        object.remove("save_files");
        object.put("save_files", outputFolder);
        object.remove("browser_type");
        object.put("browser_type", browserType);

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
        JSONArray data = object.getJSONArray("data");
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
