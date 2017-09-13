package com.watcharaphat.app;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UrlTest {
    public static void main(String[] args) throws IOException {
        URL url = new URL("http://docs.oracle.com/javase/6/docs/api/java/net/URL.html");
        String [] paths = url.getPath().split("/");
        System.out.println("Host: " + url.getHost());
        for (int i = 0; i < paths.length; i++) {
            System.out.println(paths[i]);
        }
        System.out.println("**************************************************");

        Path dataDir = Paths.get("data/crawlData");
        System.out.println(dataDir.toAbsolutePath());


        if (Files.exists(dataDir)) System.out.println("Exist!");
        else System.out.println("not Exist!");
    }
}
