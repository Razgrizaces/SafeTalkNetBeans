package com.drexel.duca.backend;

import java.util.ArrayList;

public class STUser {
	
	private int stID;
	private String fbID;
	private String name;
	private ArrayList<Long> contactstIDlist;
	private ArrayList<Integer> STcontactsList;
	private String username;
	private String password;
	private String accessToken;
	private String ip;
	private boolean online;
	private boolean available;
	
	public STUser(int stID, ArrayList<Long> contactstIDlist, String username, String password, ArrayList<Integer> STcontactsList, String ip, boolean available) {
		this.stID = stID;
		this.contactstIDlist = contactstIDlist;
		this.username = username;
		this.password = password;
		this.online = false;
		this.STcontactsList = STcontactsList;
		this.ip = ip;
		this.available = available;
		
	}

	public int getStID() {
		return stID;
	}

	public void setStID(int stID) {
		this.stID = stID;
	}

	public String getFbID() {
		return fbID;
	}

	public void setFbID(String string) {
		this.fbID = string;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Long> getContactstIDlist() {
		return contactstIDlist;
	}

	public void setContactstIDlist(ArrayList<Long> contactstIDlist) {
		this.contactstIDlist = contactstIDlist;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public ArrayList<Integer> getSTcontactsList() {
		return STcontactsList;
	}

	public void setSTcontactsList(ArrayList<Integer> sTcontactsList) {
		STcontactsList = sTcontactsList;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public boolean getAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	
}
