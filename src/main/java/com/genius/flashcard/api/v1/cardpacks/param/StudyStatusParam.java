package com.genius.flashcard.api.v1.cardpacks.param;

import javax.persistence.Entity;

@Entity
public class StudyStatusParam {
	String[] rights;

	String[] wrongs;

	String current;

	public String[] getRights() {
		return rights;
	}

	public void setRights(String[] rights) {
		this.rights = rights;
	}

	public String[] getWrongs() {
		return wrongs;
	}

	public void setWrongs(String[] wrongs) {
		this.wrongs = wrongs;
	}

	public String getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;
	}

}
