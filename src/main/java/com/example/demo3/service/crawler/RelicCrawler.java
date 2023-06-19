package com.example.demo3.service.crawler;

import com.example.demo3.model.IDataEntity;
import com.example.demo3.model.Relic;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 *  RelicCrawler class
 */
public class RelicCrawler extends Crawler implements IDataCrawler {
    @Override
    public List<Relic> crawlRelic(List<String> urls, HashMap<String, String> config) throws IOException {
        List<Relic> res = new ArrayList<>();
        for (String url : urls) {
            HashMap<String, String> data = crawlData(url, config);
            data.put("url", url);
            Relic relic = new Relic(data);
            res.add(relic);
        }
        return res;
    }
}
