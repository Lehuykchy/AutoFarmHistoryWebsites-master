package com.example.demo3.model;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.types.ObjectId;

import java.util.HashMap;

/**
 *	Festival Class
 */

public class Festival implements IDataEntity{
	private String name;
	private String location;
	private String firstYear;
	private String anniversary;
	private String significance;
	private String figure;
	private String event;
	private String relic;
	private String url;
	private ObjectId _id;

	// Constructor for mapping MongoDB and Object
	@BsonCreator
	public Festival(){

	}
	// Constructor for mapping data config and Object
	public Festival(HashMap<String, String> data) {
		this.name = data.get("name");
		this.location = data.get("location");
		this.firstYear = data.get("firstYear");
		this.anniversary = data.get("anniversary");
		this.significance = data.get("significance");
		this.figure = data.get("figure");
		this.event = data.get("event");
		this.relic = data.get("relic");
		this.url = data.get("url");
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

	public String getLocation() {
	return location;
	}

	public void setLocation(String location) {
	this.location = location;
	}

	public String getFirstYear() {
	return firstYear;
	}

	public void setFirstYear(String firstYear) {
	this.firstYear = firstYear;
	}

	public String getAnniversary() {
	return anniversary;
	}

	public void setAnniversary(String anniversary) {
	this.anniversary = anniversary;
	}

	public String getSignificance() {
	return significance;
	}

	public void setSignificance(String significance) {
	this.significance = significance;
	}

	public String getFigure() {
	return figure;
	}

	public void setFigure(String figure) {
	this.figure = figure;
	}

	public String getEvent() {
	return event;
	}

	public void setEvent(String event) {
	this.event = event;
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
