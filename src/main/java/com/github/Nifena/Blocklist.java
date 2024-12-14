package com.github.Nifena;

import java.io.IOException;
import java.util.Set;

public class Blocklist{
    private final Set<String> blockedApps = Set.of("Battlenet","Steam","Safari");

    public void blockApplications(String os) throws IOException {

        Thread checkApps = new Thread(() -> {
            while (true) {
                try {
                    Set<String> appNames = ProcessInfoExtractor.extractApplicationNames(os);

                    for (String appName : appNames) {
                        if (blockedApps.contains(appName)) {
                            System.out.println("Blocked app detected: " + appName);
                            //dodajmy wylaczenie tej aplikacji po wykryciu
                            String command = Main.setupKillCommand(os, appName);
                            Runtime.getRuntime().exec(command);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        checkApps.setDaemon(true);
        checkApps.start();
    }
}
