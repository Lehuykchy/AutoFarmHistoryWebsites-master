package com.example.demo3.service.crawler;

import com.example.demo3.model.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *  Crawler class - other crawler 's parents class
 */
public class Crawler {
    List<Relic> crawlRelic(List<String> urls, HashMap<String, String> config) throws IOException {
        return null;
    }
    List<Figure> crawlFigure(List<String> urls, HashMap<String, String> config, HashMap<String, List<String>> configList) throws IOException {
        return null;
    }
    List<Festival> crawlFestival(List<String> urls, HashMap<String, String> config) throws IOException {
        return null;
    }

    List<Event> crawlEvent(List<String> urls, HashMap<String, String> config) throws IOException {
        return null;
    }

    List<Dynasty> crawlDynasty(List<String> urls, HashMap<String, String> config, HashMap<String, List<String>> configList) throws IOException {
        return null;
    }
}
