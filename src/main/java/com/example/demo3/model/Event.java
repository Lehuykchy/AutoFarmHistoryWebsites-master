package com.example.demo3.model;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.types.ObjectId;

import java.util.HashMap;

/*
	Event Class
 */

public class Event implements IDataEntity{
	private String name;
	private String time;
	private String address;
	private String result;
	private String skateholders;
	private String url;
	private ObjectId _id;
	public ObjectId get_id() {
		return _id;
	}

	// Constructor for mapping MongoDB and Object
	@BsonCreator
	public Event(){

	}
	// Constructor for mapping data config and Object
	public Event(HashMap<String, String> data){
		this.name = data.get("name");
		this.time = data.get("time");
		this.address = data.get("address");
		this.result = data.get("result");
		this.skateholders = data.get("skateholders");
		this.url = data.get("url");
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

	public String getTime() {
	return time;
	}

	public void setTime(String time) {
	this.time = time;
	}

	public String getAddress() {
	return address;
	}

	public void setAddress(String address) {
	this.address = address;
	}

	public String getResult() {
	return result;
	}

	public void setResult(String result) {
	this.result = result;
	}

	public String getSkateholders() {
	return skateholders;
	}

	public void setSkateholders(String skateholders) {
	this.skateholders = skateholders;
	}

	public String getUrl() {
	return url;
	}

	public void setUrl(String url) {
	this.url = url;
	}
}
