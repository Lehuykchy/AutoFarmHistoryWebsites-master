package com.example.demo3.model;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.types.ObjectId;

import java.util.HashMap;
import java.util.List;

/**
 *  Figure Class
 */
public class Figure implements IDataEntity{
    private ObjectId _id;
    private String name;
    private String ruleTime;
    private String lifeTime;
    private String burial;
    private List<String> concubine = null;
    private List<String> realName = null;
    private List<String> label = null;
    private String honorName;
    private String swissBrand;
    private String templeBrand;
    private String dynasty;
    private String father;
    private String mother;
    private String religion;
    private String relic;
    private String url;

    // Constructor for mapping MongoDB and Object
    @BsonCreator
    public Figure(){

    }

    // 	// Constructor for mapping data config and Object
    public Figure(HashMap<String, String> config, HashMap<String, List<String>> configList) {
        this.name = config.get("name");
        this.ruleTime = config.get("ruleTime");
        this.lifeTime = config.get("lifeTime");
        this.burial = config.get("burial");
        this.concubine = configList.get("concubine");
        this.realName = configList.get("realName");
        this.label = configList.get("label");
        this.honorName = config.get("honorName");
        this.swissBrand = config.get("swissBrand");
        this.templeBrand = config.get("templeBrand");
        this.dynasty = config.get("dynasty");
        this.father = config.get("father");
        this.mother = config.get("mother");
        this.religion = config.get("religion");
        this.relic = config.get("relic");
        this.url = config.get("url");
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRuleTime() {
        return ruleTime;
    }

    public void setRuleTime(String ruleTime) {
        this.ruleTime = ruleTime;
    }

    public String getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(String lifeTime) {
        this.lifeTime = lifeTime;
    }

    public String getBurial() {
        return burial;
    }

    public void setBurial(String burial) {
        this.burial = burial;
    }

    public List<String> getConcubine() {
        return concubine;
    }

    public void setConcubine(List<String> concubine) {
        this.concubine = concubine;
    }

    public List<String> getRealName() {
        return realName;
    }

    public void setRealName(List<String> realName) {
        this.realName = realName;
    }

    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }

    public String getHonorName() {
        return honorName;
    }

    public void setHonorName(String honorName) {
        this.honorName = honorName;
    }

    public String getSwissBrand() {
        return swissBrand;
    }

    public void setSwissBrand(String swissBrand) {
        this.swissBrand = swissBrand;
    }

    public String getTempleBrand() {
        return templeBrand;
    }

    public void setTempleBrand(String templeBrand) {
        this.templeBrand = templeBrand;
    }

    public String getDynasty() {
        return dynasty;
    }

    public void setDynasty(String dynasty) {
        this.dynasty = dynasty;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getRelic() {
        return relic;
    }

    public void setRelic(String relic) {
        this.relic = relic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
