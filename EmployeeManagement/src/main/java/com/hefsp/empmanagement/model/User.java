package com.hefsp.empmanagement.model;

// This class just like Entity
public class User {

	//These variables store information about a user.
	private int id;
	private String firstname;
	private String lastname;
	private String contactno;
	private String address;

	//This constructor using fields It is used to initialize object.

	public User(String firstname, String lastname, String contactno, String address) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.contactno = contactno;
		this.address = address;
	}
	public User(int id, String firstname, String lastname, String contactno, String address) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.contactno = contactno;
		this.address = address;
	}

	// There are getter and setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getContactno() {
		return contactno;
	}
	public void setContactno(String contactno) {
		this.contactno = contactno;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
