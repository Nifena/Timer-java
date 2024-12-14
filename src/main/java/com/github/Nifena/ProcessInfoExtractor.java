package com.github.Nifena;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessInfoExtractor {

    public static Set<String> extractApplicationNames(String os) throws IOException {
        Set<String> appPaths = extractApplicationPaths(os);
        Set<String> appNames = new HashSet<>();

        for (String appPath : appPaths) {
            appNames.add(extractAppName(appPath, os));
        }

        return appNames;
    }

    public static Set<String> extractApplicationPaths(String os) throws IOException {
        Process p = null;

        if (os.contains("win")) {
            p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");
        } else {
            p = Runtime.getRuntime().exec("ps aux");
        }

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

    public static void runnedApplications(String os) throws IOException {
        System.out.println("List of running processes: ");
        Set<String> appPaths = ProcessInfoExtractor.extractApplicationPaths(os);
        for (String appPath : appPaths) {
            System.out.println(appPath);
        }
    }

    private static String extractAppName(String appPath, String os) {
        if (os.contains("win")) {
            return appPath.substring(appPath.lastIndexOf("\\") + 1).replace(".exe", "");
        } else if (os.contains("mac")) {
            return appPath.substring(appPath.lastIndexOf("/") + 1).replace(".app", "");
        } else {
            return appPath; // Dla innych systemów zwróć pełną nazwę
        }
    }

}