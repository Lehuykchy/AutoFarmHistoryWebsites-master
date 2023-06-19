package com.example.demo3.service.crawler;

import com.example.demo3.model.Dynasty;
import com.example.demo3.model.Figure;
import com.example.demo3.model.IDataEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * DynastyCrawler class
 */
public class DynastyCrawler extends Crawler implements IDataCrawler{
    public List<Dynasty> crawlDynasty(List<String> urls, HashMap<String, String> config, HashMap<String, List<String>> configList) throws IOException {
        List<Dynasty> res = new ArrayList<>();
        for (String url : urls) {
            HashMap<String, String> data = crawlData(url, config);
            HashMap<String, List<String>> dataList = crawlListData(url, configList);
            data.put("url", url);
            Dynasty dynasty = new Dynasty(data, dataList);
            res.add(dynasty);
        }
        return res;
    }
}
