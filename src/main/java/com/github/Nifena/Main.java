package com.github.Nifena;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) {

        System.out.println("Enter the countdown time in minutes (maximum 120 minutes):");
        int setTime = new Scanner(System.in).nextInt();
        String numPrefix;
        numPrefix = setTime == 1 ? "minute" : "minutes";
        System.out.println("You set the timer for " + setTime + " " + numPrefix );

        if (setTime > 120 || setTime < 1) {
            System.out.println("Invalid value. Please enter a time between 1 and 120 minutes.");
            System.exit(0);
        }

        TimerTask task = new TimerTask(){
            final int timeInSec = setTime * 60;
            int i = 1;

            public void run(){
                System.out.println(i++);

                if (i > timeInSec) {
                    System.out.println("Countdown complete.");
                    System.exit(0);
                }
            }
        };

        Timer timer = new Timer("Timer");

        timer.schedule(task, 1000L, 1000L);

    }
}