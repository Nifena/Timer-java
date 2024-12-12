package com.github.Nifena;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessInfoExtractor {
    public static Set<String> extractApplicationPaths(String os) throws IOException {


        Process p = null;

        if (os.contains("win")) {
            p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");
        } else {
            p = Runtime.getRuntime().exec("ps aux");
        }

        System.out.println("List of running processes: ");

        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        Set<String> applicationPaths = new HashSet<>();
        String line;

        Pattern patternMacOS = Pattern.compile("Applications/\\S+\\.app");
        Pattern patternWindows = Pattern.compile("\\S+\\.exe");

        if (os.contains("win")) {
            while ((line = input.readLine()) != null){
                Matcher matcher = patternWindows.matcher(line);
                if (matcher.find()) {
                    String fullPath = matcher.group();
                    String mainAppPath = extractMainApplicationPathWindows(fullPath);
                    applicationPaths.add(mainAppPath);
                }
            }
        }else if (os.contains("mac")) {
            while ((line = input.readLine()) != null){
                Matcher matcher = patternMacOS.matcher(line);
                if (matcher.find()) {
                    String fullPath = matcher.group();
                    String mainAppPath = extractMainApplicationPathMacOS(fullPath);
                    applicationPaths.add(mainAppPath);
                }
            }
        }
        input.close();
        return applicationPaths;
    }

    private static String extractMainApplicationPathMacOS(String fullPath) throws IOException {
        int index1 = fullPath.indexOf("/") + 1;
        int index2 = fullPath.indexOf(".app") + 4;

        return fullPath.substring(index1,index2);
    }

    private static String extractMainApplicationPathWindows(String fullPath) throws IOException {
        int index1 = fullPath.indexOf("/") + 1;
        int index2 = fullPath.indexOf(".exe") + 4;

        return fullPath.substring(index1,index2);
    }
}