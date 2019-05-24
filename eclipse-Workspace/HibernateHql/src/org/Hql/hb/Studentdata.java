package org.Hql.hb;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQuery (name =  "studentId", query="from Studentdata where studentId = :studentId")
@NamedNativeQuery (name = "studentInfo.byName", query="select * from  StudentInfo where studentName= :studentName", resultClass= Studentdata.class)
@Table (name = "StudentInfo")
public class Studentdata {
	
	@Id @GeneratedValue (strategy = GenerationType.SEQUENCE)
	private int studentId;
	private String studentName;
	private String studentAddress;
	
	
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentAddress() {
		return studentAddress;
	}
	public void setStudentAddress(String studentAddress) {
		this.studentAddress = studentAddress;
	}
	
	

}
