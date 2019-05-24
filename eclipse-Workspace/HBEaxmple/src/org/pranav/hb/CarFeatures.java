package org.pranav.hb;

import javax.persistence.Entity;

@Entity
public class CarFeatures extends Vechile {

	private String carAc;
	private String wheelDetail;
	public String getCarAc() {
		return carAc;
	}
	public void setCarAc(String carAc) {
		this.carAc = carAc;
	}
	public String getWheelDetail() {
		return wheelDetail;
	}
	public void setWheelDetail(String wheelDetail) {
		this.wheelDetail = wheelDetail;
	}
	
	
}
