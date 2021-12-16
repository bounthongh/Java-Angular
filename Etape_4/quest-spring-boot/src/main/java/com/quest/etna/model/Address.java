package com.quest.etna.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "address")
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable = false, length = 100)
	private String road;
	
	@Column(nullable = false, length = 30)
	private String postalCode;
	
	@Column(nullable = false, length = 50)
	private String city;
	
	@Column(nullable = false, length = 50)
	private String country;
	
	@ManyToOne()
	@JoinColumn(name = "id_user")
    private User User;
	
	@Column()
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	    
	@Column()
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;
	
	@PrePersist
    public void prePersit() { 
		creationDate = new Date(System.currentTimeMillis());
	}
    
    @PreUpdate
    public void preUpdate() { 
    	updatedDate = new Date(System.currentTimeMillis());
    }
	
	public Address() {};
	
	public Address(Integer id, String road, String postal_code, String city, String country, User id_user, Date creation_Date, Date updated_Date) {
		this.id = id;
		this.road = road;
		this.postalCode = postal_code;
		this.city = city;
		this.country = country;
		this.User = id_user;
		this.creationDate = creation_Date;
		this.updatedDate = updated_Date;
	}
	
	public Address(String road, String postal_code, String city, String country, User id_user, Date creation_Date, Date updated_Date) {
		this.road = road;
		this.postalCode = postal_code;
		this.city = city;
		this.country = country;
		this.User = id_user;
		this.creationDate = creation_Date;
		this.updatedDate = updated_Date;
	}
	
	public Address(String road, String postal_code, String city, String country, User id_user) {
		this.road = road;
		this.postalCode = postal_code;
		this.city = city;
		this.country = country;
		this.User = id_user;
	}
	
	public Address(String road, String postal_code, String city, String country) {
		this.road = road;
		this.postalCode = postal_code;
		this.city = city;
		this.country = country;
	}
	
	//Getter
	public Integer getId() {
		return id;
	}
	
	public String getRoad() {
		return road;
	}

	public String getPostalCode() {
		return postalCode;
	}
	
	public String getCity() {
		return city;
	}
	
	public String getCountry() {
		return country;
	}
	
	public User getUser() {
		return User;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public Date getUpdatedDate() {
		return updatedDate;
	}
	
	//Setter
	public void setId(Integer id) {
		this.id = id;
	}

	public void setRoad(String road) {
		this.road = road;
	}

	public void setPostalCode(String postal_code) {
		this.postalCode = postal_code;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setUser(User id_user) {
		this.User = id_user;
	}

	public void setCreationDate(Date creation_Date) {
		this.creationDate = creation_Date;
	}

	public void setUpdatedDate(Date updated_Date) {
		this.updatedDate = updated_Date;
	}
	
	@Override
	public String toString() {
		String toString = String.format("\n[id=%s, road=%s, postalCode=%s, city=%s, country=%s, User=%s, creationDate=%s, updateUser=%s ", id, road, postalCode, city, country, User.toString(), creationDate, updatedDate);
				
		return toString;
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}
	
	@Override
	public boolean equals(Object object) {
		
		if(!(object instanceof Address)) {
			return false;
		}
		
		Address other = (Address) object;
		
		if((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		
		return true;
	}
}
