package com.github.Nifena;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessInfoExtractor {
    public static Set<String> extractApplicationPaths(String os) throws IOException {
        Process p = null;

        if (os.contains("win")) {
            System.out.println("Unsupported Operating System: " + os);
        } else {
            p = Runtime.getRuntime().exec("ps aux");
        }


        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream())); //czytamy linie
        Set<String> applicationPaths = new HashSet<>();
        String line;

        Pattern pattern = Pattern.compile("/Applications/\\S+\\.app");

        while ((line = input.readLine()) != null){

            Matcher matcher = pattern.matcher(line);

            if (matcher.find()) {
                String fullPath = matcher.group();
                String mainAppPath = extractMainApplicationPath(fullPath);
                applicationPaths.add(mainAppPath);
            }
        }
        input.close();
        return applicationPaths;
    }

    private static String extractMainApplicationPath(String fullPath) throws IOException {
        int index = fullPath.indexOf(".app") + 4;

        return fullPath.substring(0,index);

    }
}