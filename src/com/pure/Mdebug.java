package com.pure;

import java.io.File;
import java.io.FileWriter;
import java.util.function.Consumer;

public class Mdebug {

    public static void fetchFiles(File dir, Consumer<File> fileConsumer) {

        if (dir.isDirectory()) {
            for (File file1 : dir.listFiles()) {
                fetchFiles(file1, fileConsumer);
            }
        } else {
            fileConsumer.accept(dir);
        }
    }

    public static void main(String[] args) {

        try {
            File file = new File("/var/www/html/malware_samples/");
            fetchFiles(file, f -> buildFile(f.getAbsolutePath()));
        }catch (Exception e){
            System.err.println(e.getMessage());
            System.err.println(e.getCause());
            e.printStackTrace();
        }
    }

    static void buildFile(String fullpath){

        try {

            File newFile = new File("/var/www/html/list.txt");
            FileWriter fr = new FileWriter(newFile, true);
            fr.write(fullpath + "\n");
            fr.close();

        }catch (Exception e){
            System.err.println(e.getMessage());
            System.err.println(e.getCause());
            e.printStackTrace();
        }
    }


}
