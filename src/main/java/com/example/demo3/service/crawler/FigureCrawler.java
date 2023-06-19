package com.example.demo3.service.crawler;

import com.example.demo3.model.Figure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * FigureCrawler class
 */
public class FigureCrawler extends Crawler implements IDataCrawler{
    @Override
    public List<Figure> crawlFigure(List<String> urls, HashMap<String, String> config, HashMap<String, List<String>> configList) throws IOException {
        List<Figure> res = new ArrayList<>();
        for (String url : urls) {
            HashMap<String, String> data = crawlData(url, config);
            HashMap<String, List<String>> dataList = crawlListData(url, configList);
            data.put("url", url);
            Figure figure = new Figure(data, dataList);
            res.add(figure);
        }
        return res;
    }
}
