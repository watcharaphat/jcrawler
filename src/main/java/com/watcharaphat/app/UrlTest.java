package com.watcharaphat.app;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class UrlTest {
    public static void main(String[] args) throws IOException {
        URL url = new URL("https://docs.oracle.com/javase/tutorial/essential/io/pathOps.html");
        String[] paths = url.getPath().split("/");
        System.out.println("Host: " + url.getHost());

        Path dataDir = Paths.get("data/crawlData/test");

        String targetDir = dataDir.toString() + "/" + url.getHost();

        for (int i = 0; i < paths.length; i++) {
            if (!paths[i].isEmpty())
            targetDir += "/" + paths[i];
        }

        String html = "<html>Hello</html>";

        Path targetPath = Paths.get(targetDir);
        Files.createDirectories(targetPath.getParent());

        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(targetPath.toString());
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

        String t = "data/test/test.html";
        Path testPath = Paths.get(t);
        Files.createDirectories(testPath.getParent());
        System.out.println("testPath: " + testPath);
        Files.write(Paths.get(testPath.toString()), html.getBytes());
    }
}
