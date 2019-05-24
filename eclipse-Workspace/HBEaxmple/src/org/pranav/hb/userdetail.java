package org.pranav.hb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GeneratorType;

@Entity
@Table(name = "detail")
public class userdetail {	
	@Id @GeneratedValue (strategy = GenerationType.SEQUENCE)
	private int id;
	
	private String firstName;

	private String lastName;
	
	@Transient 
	private String PhoneNo;
	
	@Temporal (TemporalType.DATE)
	private Date Joindate;
	
	/*@Embedded
	private Address address;*/
	
	@ElementCollection
	@JoinTable ( name = "userAddress", joinColumns=@JoinColumn(name = "User_ID"))
	private Collection<Address> listAdresss=new ArrayList<Address>();
	
	/*@OneToOne
	@JoinColumn (name ="Car_ID")
	private Vechile vechileData;*/
	
	@OneToMany
	@JoinTable (name = "listVechile", joinColumns = @JoinColumn(name = "User_ID"),
				inverseJoinColumns = @JoinColumn(name = "Vechile_ID"))
	private Collection<Vechile>  listVechile = new  ArrayList<Vechile>();
	
	
	public userdetail() {
		super();
	}
	
	
	
	/*public Vechile getVechileData() {
		return vechileData;
	}


	public void setVechileData(Vechile vechileData) {
		this.vechileData = vechileData;
	}*/


	
	
	
/*	
	public Address getAddress() {
		return address;
	}



	public void setAddress(Address address) {
		this.address = address;
	}*/



	public Collection<Vechile> getListVechile() {
		return listVechile;
	}



	public void setListVechile(Collection<Vechile> listVechile) {
		this.listVechile = listVechile;
	}



	public Collection<Address> getListAdresss() {
		return listAdresss;
	}


	public void setListAdresss(Collection<Address> listAdresss) {
		this.listAdresss = listAdresss;
	}


	//@Id
	//@Column (name = "User_id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	//@Column (name = "FirstName")
	public String getFirstName() {
		return "Your FirstName:-  "+firstName;
	}
	public void setFirstName(String name) {
		this.firstName = name;
	}
	
	//@Column (name = "LastName")
	public String getLastName() {
		return "Your LastName:-  "+lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	//@Column (name = "PhoneNo")
	public String getPhoneNo() {
		return "Your PhoneNo:-  "+PhoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		PhoneNo = phoneNo;
	}
	public Date getJoindate() {
		return Joindate;
	}
	public void setJoindate(Date joindate) {
		Joindate = joindate;
	}	
	
	
	
}
