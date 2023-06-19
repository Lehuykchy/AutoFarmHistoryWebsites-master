package com.example.demo3.service.scrap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *  Scrap Url class includes extract url functions
 */
public class Scrap {
    // Web wiki
    public static List<String> scrapUrl(String url, String parentTag){
        try {
            List<String> lst = new ArrayList<>();
            Document document = Jsoup.connect(url).get();
            Elements linksOnPage = document.select(parentTag);
            for (Element link: linksOnPage){
                lst.add(link.attr("href"));
            }
            return lst;
        } catch (IOException e) {
            System.out.println("Url '" + url + "': " + e.getMessage());
        }
        return null;
    }


    // web nguoikesu
    public static List<String> scrapUrlv2(String _url, String start, String end, String step, String parentTag){
        try {
            List<String> lst = new ArrayList<>();
            for (int i = Integer.parseInt(start); i <= Integer.parseInt(end); i+= Integer.parseInt(step)){
                String url = _url;
                url = url.replace("%s", Integer.toString(i));
                Document document = Jsoup.connect(url).get();
                Elements linksOnPage = document.select(parentTag);
                for (Element link: linksOnPage){
                    lst.add(link.attr("href"));
                }
            }
            return lst;
        } catch (IOException e) {
            System.out.println("Url '" + _url + "': " + e.getMessage());
        }
        return null;
    }
}
