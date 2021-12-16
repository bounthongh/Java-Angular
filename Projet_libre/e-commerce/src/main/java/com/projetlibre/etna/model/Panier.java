package com.projetlibre.etna.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "panier")
public class Panier {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable = false)
	private Integer quantite;
	
	@OneToOne
	@JoinColumn(name = "id_user", nullable = false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "id_produit", nullable = false)
	private Produit produit;
	
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
	
	public Panier() {};
	
	public Panier(Integer id, Integer quantite, User id_user, Produit id_produit, Date creation_Date, Date updated_Date) {
		this.id = id;
		this.quantite = quantite;
		this.user = id_user;
		this.produit = id_produit;
		this.creationDate = creation_Date;
		this.updatedDate = updated_Date;
	}
	
	public Panier(Integer quantite, User id_user, Produit id_produit, Date creation_Date, Date updated_Date) {
		this.quantite = quantite;
		this.user = id_user;
		this.produit = id_produit;
		this.creationDate = creation_Date;
		this.updatedDate = updated_Date;
	}
	
	public Panier(Integer quantite, User id_user, Produit id_produit) {
		this.quantite = quantite;
		this.user = id_user;
		this.produit = id_produit;
	}

	//GETTER
	public Integer getId() {
		return id;
	}

	public Integer getQuantite() {
		return quantite;
	}

	public User getUser() {
		return user;
	}

	public Produit getProduit() {
		return produit;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}
	
	//SETTER
	public void setId(Integer id) {
		this.id = id;
	}

	public void setQuantite(Integer quantite) {
		this.quantite = quantite;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public void setCreationDate(Date creation_Date) {
		this.creationDate = creation_Date;
	}

	public void setUpdatedDate(Date updated_Date) {
		this.updatedDate = updated_Date;
	}
	
	@Override
	public String toString() {
		String toString = String.format("\n[id=%s, quantite=%s, user=%s, produit=%s, creationDate=%s, updateUser=%s ", id, quantite, user.toString(), produit.toString(), creationDate, updatedDate);
		
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
		if(!(object instanceof Panier)) {
			return false;
		}
		
		Panier other = (Panier) object;
		
		if((this.id == null && other.id != null) || (this.id != null  && !this.id.equals(other.id))) {
			return false;
		}
		
		return true;
	}

}

