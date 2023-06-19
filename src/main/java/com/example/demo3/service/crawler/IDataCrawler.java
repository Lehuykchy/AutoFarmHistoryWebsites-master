package com.example.demo3.service.crawler;

import com.example.demo3.model.IDataEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 *  IDataCrawler interface contains crawl method
 */
public interface IDataCrawler {
    default HashMap<String, String> crawlData(String url, HashMap<String, String> config) throws IOException {
        if (config == null) {
            return null;
        }
        HashMap<String, String> data = new HashMap<String, String>();
        Document document = Jsoup.connect(url).get();
        for (String key : config.keySet()) {
            if (config.get(key).isEmpty() || config.get(key) == null){
                continue;
            }
            String text = document.select(config.get(key)).text();
            data.put(key, text);
        }
        return data;
    }

    default HashMap<String, List<String>> crawlListData(String url, HashMap<String, List<String>> config) throws IOException {
        if (config == null) {
            return null;
        }
        HashMap<String, List<String>> data = new HashMap<>();

        Document document = Jsoup.connect(url).get();
        for (String key : config.keySet()) {
            List<String> value = new ArrayList<>();
            if (config.get(key).isEmpty() || config.get(key) == null){
                continue;
            }
            for (String path : config.get(key)) {
                if(path.isEmpty() || path == null){
                    continue;
                }
                String text = document.select(path).text();
                value.add(text);
            }
            data.put(key, value);
        }
        return data;
    }
}
