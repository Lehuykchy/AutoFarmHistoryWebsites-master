package com.example.demo3.service.crawler;

import com.example.demo3.model.Festival;
import com.example.demo3.model.IDataEntity;
import com.example.demo3.model.Relic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *  FestivalCrawler class
 */
public class FestivalCrawler extends Crawler implements IDataCrawler{
    @Override
    public List<Festival> crawlFestival(List<String> urls, HashMap<String, String> config) throws IOException{
        List<Festival> res = new ArrayList<>();
        for (String url : urls) {
            HashMap<String, String> data = crawlData(url, config);
            data.put("url", url);
            Festival festival = new Festival(data);
            res.add(festival);
        }
        return res;
    }
}
