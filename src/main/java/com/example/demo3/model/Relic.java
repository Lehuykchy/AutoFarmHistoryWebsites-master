package com.example.demo3.model;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.types.ObjectId;

import java.util.HashMap;

/**
 *	Relic Class
 */

public class Relic implements IDataEntity{
	private String name;
	private String address;
	private String found;
	private String founder;
	private String embellished;
	private String newConstruction;
	private String festivalTime;
	private String url;

	// Constructor for mapping MongoDB and Object
	@BsonCreator
	public Relic(){

	}

	// Constructor for mapping data config and Object
	public Relic( HashMap<String, String> data) {
		this.name = data.get("name");
		this.address = data.get("address");
		this.found = data.get("found");
		this.founder = data.get("founder");
		this.embellished = data.get("embellished");
		this.newConstruction = data.get("newConstruction");
		this.festivalTime = data.get("festivalTime");
		this.url = data.get("url");
	}


	public String getName() {
	return name;
	}

	public void setName(String name) {
	this.name = name;
	}

	public String getAddress() {
	return address;
	}

	public void setAddress(String address) {
	this.address = address;
	}

	public String getFound() {
	return found;
	}

	public void setFound(String found) {
	this.found = found;
	}

	public String getFounder() {
	return founder;
	}

	public void setFounder(String founder) {
	this.founder = founder;
	}

	public String getEmbellished() {
	return embellished;
	}

	public void setEmbellished(String embellished) {
	this.embellished = embellished;
	}

	public String getNewConstruction() {
	return newConstruction;
	}

	public void setNewConstruction(String newConstruction) {
	this.newConstruction = newConstruction;
	}

	public String getFestivalTime() {
	return festivalTime;
	}

	public void setFestivalTime(String festivalTime) {
	this.festivalTime = festivalTime;
	}

	public String getUrl() {
	return url;
	}

	public void setUrl(String url) {
	this.url = url;
	}
}
