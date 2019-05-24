package org.pranav.hb;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

	private String homeAdress;
	private String state;
	
	
	public String getHomeAdress() {
		return homeAdress;
	}
	public void setHomeAdress(String homeAdress) {
		this.homeAdress = homeAdress;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
}
