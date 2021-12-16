package com.projetlibre.etna.model;

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
@Table(name = "produit")
public class Produit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable = false, length = 100)
	private String reference;
	
	@Column(nullable = false)
	private Double prix;
	
	@Column(nullable = false, length = 100)
	private String nom;
	
	@Column(nullable = false, length = 100)
	private String description;
	
	@Column(nullable = false)
	private Integer stock;
	
	@Column(nullable = false)
	private String url = "https://www.idealstandard.co.uk/-/media/project/ideal-standard/commerce-websites/shared-website/default-fallback-images/product-tile/product_image_placeholder.png";
	
	@ManyToOne
	@JoinColumn(name = "id_user", nullable = false)
	private User user;
	
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
	
	public Produit() {};
	
	public Produit (Integer id, String reference, Double prix, String nom, String description, Integer stock, String url, User id_user, Date creation_Date, Date updated_Date) {
		this.id = id;
		this.reference = reference;
		this.prix = prix;
		this.nom = nom;
		this.description = description;
		this.stock = stock;
		this.url = url;
		this.user = id_user;
		this.creationDate = creation_Date;
		this.updatedDate = updated_Date;
	}
	
	public Produit (String reference, Double prix, String nom, String description, Integer stock, String url, User id_user, Date creation_Date, Date updated_Date) {
		this.reference = reference;
		this.prix = prix;
		this.nom = nom;
		this.description = description;
		this.stock = stock;
		this.url = url;
		this.user = id_user;
		this.creationDate = creation_Date;
		this.updatedDate = updated_Date;
	}
	
	public Produit (String reference, Double prix, String nom, String description, Integer stock, String url, User id_user) {
		this.reference = reference;
		this.prix = prix;
		this.nom = nom;
		this.description = description;
		this.stock = stock;
		this.url = url;
		this.user = id_user;
	}
	
	public Produit (String reference, Double prix, String nom, String description, Integer stock, String url) {
		this.reference = reference;
		this.prix = prix;
		this.nom = nom;
		this.description = description;
		this.stock = stock;
		this.url = url;
	}
	
	public Produit (String reference, Double prix, String nom, String description, Integer stock) {
		this.reference = reference;
		this.prix = prix;
		this.nom = nom;
		this.description = description;
		this.stock = stock;
	}

	//GETTER
	public Integer getId() {
		return id;
	}

	public String getReference() {
		return reference;
	}
	
	public Double getPrix() {
		return prix;
	}

	public String getNom() {
		return nom;
	}

	public String getDescription() {
		return description;
	}

	public Integer getStock() {
		return stock;
	}

	public User getUser() {
		return user;
	}

	public String getUrl() {
		return url;
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

	public void setReference(String reference) {
		this.reference = reference;
	}

	public void setPrix(Double prix) {
		this.prix = prix;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setCreationDate(Date creation_Date) {
		this.creationDate = creation_Date;
	}

	public void setUpdatedDate(Date updated_Date) {
		this.updatedDate = updated_Date;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		String toString = String.format("\n[id=%s, reference=%s, prix=%s, nom=%s, description=%s, stock=%s, url=%s, user=%s, creationDate=%s, updateUser=%s ", id, reference, prix, nom, description, stock, url ,user.toString(), creationDate, updatedDate);
		
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
		 
		if(!(object instanceof Produit)) {
			return false;
		}
		
		Produit other = (Produit) object;
		
		if((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		
		return true;
	}	
}
