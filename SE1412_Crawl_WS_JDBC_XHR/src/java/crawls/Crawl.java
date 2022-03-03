/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawls;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import sax.CrawlSAX;

/**
 *
 * @author PC
 */
public class Crawl {
    public String readContent(String urlString) throws Exception {
        String content = "";
        URL url = new URL(urlString);
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        String inputLine;
        while((inputLine = br.readLine()) != null) {
            content += inputLine;
        }
        return content;
    }
    
    public static void main(String[] args) {
        try {
            String urlString = "https://vnexpress.net/rss/thoi-su.rss";
            Crawl crawl = new Crawl();
            CrawlSAX handler = new CrawlSAX();
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser parser = spf.newSAXParser();
            parser.parse(new InputSource(new StringReader(crawl.readContent(urlString))), handler);
            System.out.println("finished");
        } catch (Exception e) {
        }
    }
}
