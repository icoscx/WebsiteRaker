package com.pure;

import com.pure.misc.Functions;

import java.io.FileNotFoundException;

public class Mdebug {


    public static void main(String[] args) {


        try {
            var strings = Functions.rakerConfigGetYaraTags();

            System.out.println(strings);

            var sdsf = Functions.rakerConfigGetPlaybookName();

            System.out.println(sdsf);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
