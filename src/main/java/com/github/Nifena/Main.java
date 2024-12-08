package com.github.Nifena;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        String line = "";
        String pidInfo = "";
        String command = "";
        Process p = null;
        String numPrefix = "";
        String finalPidInfo = pidInfo;
        String finalCommand = command;

        System.out.println("Enter the name of the process you want to terminate:");
        String processName = scanner.nextLine();
        String os = System.getProperty("os.name").toLowerCase();

        checkProcess(os,command,processName,p);

        searchingForProcess(line,pidInfo,processName,p);

        setTimer(scanner, numPrefix);

        TimerTask task = new TimerTask() {
            final int timeInSec = setTime * 60;
            int i = 1;

            public void run() {
                System.out.println(i++);

                if (i > timeInSec && finalPidInfo.contains(processName)) {
                    try {
                        Runtime.getRuntime().exec(finalCommand);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Countdown complete, and " + processName + " has been terminated.");
                    System.exit(0);
                }
            }
        };

        Timer timer = new Timer("Timer");
        timer.schedule(task, 1000L, 1000L);

        Thread userInputThread = new Thread(() -> {
            while (true) {
                String userInput = scanner.nextLine().trim();
                if (userInput.equals("stop")) {
                    task.cancel();
                    System.exit(0);
                }
            }
        });

        userInputThread.setDaemon(true);
        userInputThread.start();
    }

    public static void checkProcess(String os, String command, String processName, Process p) throws IOException {
        if (os.contains("win")) {
            command = "taskkill /F /IM " + processName + ".exe"; // Windows system command to terminate the process
        } else if (os.contains("mac") || os.contains("nix") || os.contains("nux")) {
            command = "pkill -9 " + processName;
        } else {
            System.out.println("Unsupported operating system");
            System.exit(1);
        }

        if (os.contains("win")) {
            p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");
        } else {
            p = Runtime.getRuntime().exec("ps aux");
        }
    }

    public static void searchingForProcess(String line, String pidInfo, String processName, Process p) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream())); // Reading output line by line
        while ((line = input.readLine()) != null) {
            pidInfo += line;
        }
        input.close();

        boolean contains = pidInfo.contains(processName);
        if (!contains) {
            System.out.println("The specified process was not found.");
            System.exit(0);
        }
    }

    public static int setTimer(Scanner scanner, String numPrefix) {
        System.out.println("Enter the countdown time in minutes (maximum 120 minutes):");
        int setTime = scanner.nextInt();
        numPrefix = setTime == 1 ? "minute" : "minutes";
        System.out.println("You set the timer for " + setTime + " " + numPrefix);
        System.out.println("Type 'stop' at any time to terminate the countdown.");

        if (setTime > 120 || setTime < 1) {
            System.out.println("Invalid value. Please enter a time between 1 and 120 minutes.");
            System.exit(0);
        }
        return setTime;
    }
}

