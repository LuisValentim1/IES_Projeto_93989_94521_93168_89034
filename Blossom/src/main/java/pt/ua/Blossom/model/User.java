package pt.ua.Blossom.model;

import java.sql.Time;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
//@Table(name = "user")
public class User {
	
	private @Id @GeneratedValue(strategy = GenerationType.AUTO) Long id;
	private String fname;
	private String lname;
	private String mail;
	private Date   dateSubscription;
	private String password;
	private String phoneNumber;
	//private Time lastJoined;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof User))
			return false;
		User user = (User) obj;
		return Objects.equals(this.id, user.getId());
	}
	
	public User(Long id, String fname, String lname, String mail, Date dateSubscription, String password,
			String phoneNumber) {
		super();
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.mail = mail;
		this.dateSubscription = dateSubscription;
		this.password = password;
		this.phoneNumber = phoneNumber;
	}
	
	public User() {}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.fname, this.lname, this.mail, this.dateSubscription, this.phoneNumber);
	}
	
	@Override
	public String toString() {
		return "User{" + "id=" + this.id + 
					         ", name='" + this.fname + " " + this.lname + '\'' + 
					         ", email='" + this.mail + '\'' + 
					         ", date of subscription='" + this.dateSubscription + '\'' + 
					         ", phone='" + this.phoneNumber + '\'' + 
					         '}';
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Long getId() {
		return id;
	}

	public Date getDateSubscription() {
		return dateSubscription;
	}
	
	public void setDateSubscription(Date dateSubscription) {
		this.dateSubscription = dateSubscription;
	}

}
