package com.watcharaphat.app;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Set;
import java.util.regex.Pattern;

import static org.apache.commons.codec.CharEncoding.UTF_8;

public class MyCrawler extends WebCrawler {

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
            + "|png|mp3|mp4|zip|gz))$");

    /**
     * This method receives two parameters. The first parameter is the page
     * in which we have discovered this new url and the second parameter is
     * the new url. You should implement this function to specify whether
     * the given url should be crawled or not (based on your crawling logic).
     * In this example, we are instructing the crawler to ignore urls that
     * have css, js, git, ... extensions
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches() && url.getDomain().matches("ku.ac.th");
    }

    /**
     * This function is called when a page is fetched and ready
     * to be processed by your program.
     */
    @Override
    public void visit(Page page) {
        URL url = null;
        try {
            url = new URL(page.getWebURL().getURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        URL robotsTxtUrl = null;
        try {
            robotsTxtUrl = new URL(
                    url.getProtocol() + "://" + url.getHost() + "/robots.txt"
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        };

        /** Check robots.txt **/

        HttpURLConnection huc = null;
        Path SitesContainRobotsTxt = Paths.get("data/sites_contain_robots.txt");
        try {
            huc = (HttpURLConnection) robotsTxtUrl.openConnection();
            huc.setRequestMethod("HEAD");
            if (!App.HostCheckedRobotsTxt.contains(url.getHost())) {
                if (huc.getResponseCode() == 200) {
                    Files.write(
                            Paths.get(SitesContainRobotsTxt.toString()),
                            (url.getHost() + System.lineSeparator()).getBytes(UTF_8),
                            StandardOpenOption.CREATE,StandardOpenOption.APPEND
                    );
                }
                App.HostCheckedRobotsTxt.add(url.getHost());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        App.HostCheckedRobotsTxt.add(url.getHost());

        System.out.println("URL: " + url);

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();

            System.out.println("Text length: " + text.length());
            System.out.println("Html length: " + html.length());
            System.out.println("Number of outgoing links: " + links.size());

            /** Save File to disk **/

            Path dataDir = Paths.get("data/crawlData/");

            String targetFile = dataDir.toString() + "/" + url.getHost();
            String[] paths = url.getPath().split("/");
            if (paths.length == 0) {
                targetFile += "/index.html";
            }
            for (String path : paths) {
                if (!path.isEmpty()) {
                    targetFile += "/" + path;
                }
            }

            Path targetDir = Paths.get(targetFile);
            try {
                Files.createDirectories(targetDir.getParent());
                Files.write(Paths.get(targetDir.toString()), html.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("******************************************");
        }
    }
}
