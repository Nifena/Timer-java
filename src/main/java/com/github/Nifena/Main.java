package com.github.Nifena;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) throws IOException {

        String line;
        String pidInfo = "";
        String command = "";

        System.out.println("Podaj nazwe procesu jaki chcesz wylaczyc");
        String processName = new Scanner(System.in).nextLine();
        String os = System.getProperty("os.name").toLowerCase();


        if (os.contains("win")){
            command = "taskkill /F /IM " + processName + ".exe"; //polecenie systemowe Win do zamkniecia systemu
        }else if (os.contains("mac") || os.contains("nix") || os.contains("nux")){
            command = "pkill -9 " + processName;
        }else {
            System.out.println("Unsupported operating system");
            System.exit(1);
        }


        Process p;

        if (os.contains("win")){
            p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");
        }else {
            p = Runtime.getRuntime().exec("ps aux");
        }

        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));//odczytywanie wynikow linia po linii
        while ((line = input.readLine()) != null) {
            pidInfo += line;
        }
        input.close();

        boolean contains = pidInfo.contains(processName);
        if (!contains) {
            System.out.println("Podany program nie został znaleziony");
            System.exit(0);
        }


        System.out.println("Enter the countdown time in minutes (maximum 120 minutes):");
        int setTime = new Scanner(System.in).nextInt();
        String numPrefix;
        numPrefix = setTime == 1 ? "minute" : "minutes";
        System.out.println("You set the timer for " + setTime + " " + numPrefix );

        if (setTime > 120 || setTime < 1) {
            System.out.println("Invalid value. Please enter a time between 1 and 120 minutes.");
            System.exit(0);
        }

        String finalPidInfo = pidInfo;
        String finalCommand = command;
        TimerTask task = new TimerTask(){
            final int timeInSec = setTime * 60;
            int i = 1;

            public void run(){
                System.out.println(i++);

                if (i > timeInSec & finalPidInfo.contains(processName)) { //dlaczego zawiera jak jest wylaczony?
                    try {
                        Runtime.getRuntime().exec(finalCommand);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Countdown complete, and " + processName + "has been executed");
                    System.exit(0);
                }
            }
        };

        Timer timer = new Timer("Timer");

        timer.schedule(task, 1000L, 1000L);
    }
}