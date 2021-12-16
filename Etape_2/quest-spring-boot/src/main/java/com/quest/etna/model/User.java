package com.quest.etna.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;




@Entity
@Table(name = "user")
public class User {
	
	public enum UserRole {
		ROLE_USER,
		ROLE_ADMIN
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, unique=true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column()
    private UserRole role = UserRole.ROLE_USER;

    @Column()
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
    
    @Column()
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    
    
    @PrePersist
    public void prePersit() { creationDate = new Date(System.currentTimeMillis());}
    
    @PreUpdate
    public void preUpdate() { updatedDate = new Date(System.currentTimeMillis());}
	
	public User() {};
	
	public User(Integer id, String name, String pass, UserRole role, Date post, Date put) {
		this.id = id;
		this.username = name;
		this.password = pass;
		this.role = role;
		this.creationDate = post;
		this.updatedDate = put;
	}
	
	public User(String name, String pass, UserRole role, Date post, Date put) {
		this.username = name;
		this.password = pass;
		this.role = role;
		this.creationDate = post;
		this.updatedDate = put;
	}
	
	public User(String name, String pass) {
		this.username = name;
		this.password = pass;
	}

	//GET
	public Integer getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}

	public UserRole getRole() {
		return role;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public Date getUpdatedDate() {
		return updatedDate;
	}
	
	//SET
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	@Override
	public String toString() {
		String toString = String.format("\n[id=%s, username=%s, password=%s, role=%s, creationDate=%s, updateUser=%s ", id,username,password,role,creationDate, updatedDate);
				
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
		
		if(!(object instanceof User)) {
			return false;
		}
		
		User other = (User) object;
		
		if((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		
		return true;
	}

}
