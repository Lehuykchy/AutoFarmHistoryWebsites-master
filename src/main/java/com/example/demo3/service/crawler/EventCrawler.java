package com.example.demo3.service.crawler;

import com.example.demo3.model.Event;
import com.example.demo3.model.Festival;
import com.example.demo3.model.IDataEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *  EventCrawler class
 */
public class EventCrawler extends Crawler implements IDataCrawler{
    @Override
    public List<Event> crawlEvent(List<String> urls, HashMap<String, String> config) throws IOException {
        List<Event> res = new ArrayList<>();
        for (String url : urls) {
            HashMap<String, String> data = crawlData(url, config);
            data.put("url", url);
            Event event = new Event(data);
            res.add(event);
        }
        return res;
    }

}
