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
        String processName = "notepad.exe";
        String command = "taskkill /F /IM " + processName; //polecenie systemowe Win do zamkniecia systemu

        Process p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");//uruchamia tasklist.exe,
        // zwraca lokalizacje katalogu windows, do tej sciezki dodawane sa podkatalogi

        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));//odczytywanie wynikow linia po linii
        while ((line = input.readLine()) != null) {
            pidInfo+=line;
        }
        input.close();



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
        TimerTask task = new TimerTask(){
            final int timeInSec = setTime * 60;
            int i = 1;

            public void run(){
                System.out.println(i++);

                if (i > timeInSec && finalPidInfo.contains(processName)) {
                    try {
                        Runtime.getRuntime().exec(command);
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