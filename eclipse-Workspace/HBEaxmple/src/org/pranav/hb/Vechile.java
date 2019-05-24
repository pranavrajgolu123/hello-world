package org.pranav.hb;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@Inheritance (strategy= InheritanceType.JOINED)
public class Vechile {
    
	@Id @GeneratedValue(strategy= GenerationType.SEQUENCE)
	private int carId;
	private String carModel;
	private String carName;
	/*@ManyToOne
	private userdetail listuserVechile;*/
	@ManyToMany
	private Collection<userdetail> listManyVechile =new ArrayList<>();
	
	public Collection<userdetail> getListManyVechile() {
		return listManyVechile;
	}
	public void setListManyVechile(Collection<userdetail> listManyVechile) {
		this.listManyVechile = listManyVechile;
	}
	public int getCarId() {
		return carId;
	}
	public void setCarId(int carId) {
		this.carId = carId;
	}
	public String getCarModel() {
		return carModel;
	}
	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}
	public String getCarName() {
		return carName;
	}
	public void setCarName(String carName) {
		this.carName = carName;
	}
/*	public userdetail getListuserVechile() {
		return listuserVechile;
	}
	public void setListuserVechile(userdetail listuserVechile) {
		this.listuserVechile = listuserVechile;
	}*/

}
