package com.github.Nifena;

import javax.sound.sampled.*;
import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException{
        Scanner scanner = new Scanner(System.in);
        String os = System.getProperty("os.name").toLowerCase();

        Set<String> appPaths = ProcessInfoExtractor.extractApplicationPaths(os);
        for (String appPath : appPaths) {
            System.out.println(appPath);
        }

        System.out.println("Enter the name of the process you want to terminate:");
        String processName = scanner.nextLine();

        String command = setupKillCommand(os, processName);
        String pidInfo = getProcessInfo(os);

        searchingForProcess(pidInfo, processName);
        int setTime = setTimer(scanner);

        TimerTask task = new TimerTask() {
            final int timeInSec = setTime * 60;
            int i = 1;

            public void run() {
                System.out.println(i++);
                if (i > timeInSec && pidInfo.contains(processName)) {
                    try {
                        Runtime.getRuntime().exec(command);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Countdown complete, and " + processName + " has been terminated.");
                    System.exit(0);
                }

                try {
                    MusicPlayer.reminder(setTime,i);
                } catch (UnsupportedAudioFileException e) {
                    throw new RuntimeException(e);
                } catch (LineUnavailableException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Timer timer = new Timer("Timer");
        timer.schedule(task, 1000L, 1000L);

        Thread userInputThread = new Thread(() -> {
            while (true) {
                String userInput = scanner.nextLine().trim();
                if (userInput.equalsIgnoreCase("stop")) {
                    task.cancel();
                    System.out.println("Countdown stopped by user.");
                    System.exit(0);
                }
            }
        });

        userInputThread.setDaemon(true);
        userInputThread.start();
    }

    public static String setupKillCommand(String os, String processName) {
        if (os.contains("win")) {
            return "taskkill /F /IM " + processName + ".exe";
        } else if (os.contains("mac") || os.contains("nix") || os.contains("nux")) {
            return "pkill -9 " + processName;
        } else {
            System.out.println("Unsupported operating system");
            System.exit(1);
            return ""; // Bezpieczny return (nigdy nie zostanie osiągnięty)
        }
    }

    public static String getProcessInfo(String os) throws IOException {
        Process p;
        if (os.contains("win")) {
            p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");
        } else {
            p = Runtime.getRuntime().exec("ps aux");
        }

        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        StringBuilder processInfo = new StringBuilder();
        String line;
        while ((line = input.readLine()) != null) {
            processInfo.append(line).append(System.lineSeparator());
        }
        input.close();
        return processInfo.toString();
    }

    public static void searchingForProcess(String pidInfo, String processName) {
        if (!pidInfo.contains(processName)) {
            System.out.println("The specified process was not found.");
            System.exit(0);
        }
    }

    public static int setTimer(Scanner scanner) {
        int setTime = 0;

        while (true){
            System.out.println("Enter the countdown time in minutes (maximum 120 minutes):");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please try again.");
                scanner.next();
                continue;
            }

            setTime = scanner.nextInt();

            if (setTime < 0 || setTime > 120) {
                System.out.println("Invalid value. Please enter a time between 1 and 120 minutes.");
            }else {
                System.out.println("You set the timer for " + setTime + (setTime == 1 ? " minute." : " minutes."));
                System.out.println("Type 'stop' at any time to terminate the countdown.");
                break;
            }
        }
        return setTime;
    }
}
