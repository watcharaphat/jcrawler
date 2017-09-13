package com.watcharaphat.app;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UrlTest {
    public static void main(String[] args) throws IOException {
        URL url = new URL("https://docs.oracle.com/javase/tutorial/essential/io/pathOps.html");
        String[] paths = url.getPath().split("/");
        System.out.println("Host: " + url.getHost());

        Path dataDir = Paths.get("data/crawlData/test");

        if (Files.exists(dataDir)) System.out.println("Exist!");
        else System.out.println("not Exist!");

        String targetDir = dataDir.toString() + "/" + url.getHost();

        for (int i = 0; i < paths.length; i++) {
            if (!paths[i].isEmpty())
            targetDir += "/" + paths[i];
        }

        System.out.println(targetDir);
        Path targetPath = Paths.get(targetDir);
        Files.createDirectories(targetPath.getParent());

        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(targetPath.toString());
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    }
}
