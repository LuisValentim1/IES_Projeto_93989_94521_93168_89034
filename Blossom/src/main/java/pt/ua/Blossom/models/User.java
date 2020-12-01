package pt.ua.Blossom.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	
	private @Id @GeneratedValue Long id;
	private String fname;
	private String lname;
	private String dmail;
	private Date   dateSubscription;

}
