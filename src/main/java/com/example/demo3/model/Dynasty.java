package com.example.demo3.model;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.types.ObjectId;

import java.util.HashMap;
import java.util.List;

/**
 * 	Dynasty Class
 */
public class Dynasty implements IDataEntity{
	private String name;
	private String country;
	private String ruleTime;
	private List<String> capital = null;
	private String popularLanguage;
	private String mainReligion;
	private String government;
	private List<String> king = null;
	private List<String> event = null;
	private String currencyUnit;
	private String url;
	private ObjectId _id;

	// Constructor for mapping MongoDB and Object
	@BsonCreator
	public Dynasty(){

	}

	// Constructor for mapping data config and Object
	public Dynasty(HashMap<String, String> data, HashMap<String, List<String>> dataList) {
		this.name = data.get("name");
		this.url = data.get("url");
		this.country = data.get("country");
		this.ruleTime = data.get("ruleTime");
		this.popularLanguage = data.get("popularLanguage");
		this.mainReligion = data.get("mainReligion");
		this.government = data.get("government");
		this.currencyUnit = data.get("currencyUnit");
		this.capital = dataList.get("capital");
		this.king = dataList.get("king");
		this.event = dataList.get("event");
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

	public String getCountry() {
	return country;
	}

	public void setCountry(String country) {
	this.country = country;
	}

	public String getRuleTime() {
	return ruleTime;
	}

	public void setRuleTime(String ruleTime) {
	this.ruleTime = ruleTime;
	}

	public List<String> getCapital() {
	return capital;
	}

	public void setCapital(List<String> capital) {
	this.capital = capital;
	}

	public String getPopularLanguage() {
	return popularLanguage;
	}

	public void setPopularLanguage(String popularLanguage) {
	this.popularLanguage = popularLanguage;
	}

	public String getMainReligion() {
	return mainReligion;
	}

	public void setMainReligion(String mainReligion) {
	this.mainReligion = mainReligion;
	}

	public String getGovernment() {
	return government;
	}

	public void setGovernment(String government) {
	this.government = government;
	}

	public List<String> getKing() {
	return king;
	}

	public void setKing(List<String> king) {
	this.king = king;
	}

	public List<String> getEvent() {
	return event;
	}

	public void setEvent(List<String> event) {
	this.event = event;
	}

	public String getCurrencyUnit() {
	return currencyUnit;
	}

	public void setCurrencyUnit(String currencyUnit) {
	this.currencyUnit = currencyUnit;
	}

	public String getUrl() {
	return url;
	}

	public void setUrl(String url) {
	this.url = url;
	}
}
