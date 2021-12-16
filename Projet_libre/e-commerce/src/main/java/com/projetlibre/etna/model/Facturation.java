package com.projetlibre.etna.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "facturation")
public class Facturation {

	public enum Payement{
		VALIDE,
		REJECT
	}
	
	public enum Livraison {
		EN_COURS,
		LIVREE,
		NON_LIVREE
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable = false)
	private Integer totalQt;
	
	@Column(nullable = false)
	private Double totalPrix;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "payement")
	private Payement payement = Payement.VALIDE;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "livraison")
	private Livraison livraison = Livraison.EN_COURS;
	
	@OneToOne
	@JoinColumn(name ="id_panier", nullable = false)
	private Panier panier;
	
	@OneToOne
	@JoinColumn(name = "id_address", nullable = false)
	private Address address;
	
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

	public Facturation() {};
	
	public Facturation(Integer id, Integer total_qt, Double total_prix, Payement payement, Livraison livraison, Panier panier, Address address, Date creation_Date, Date updated_Date) {
		this.id = id;
		this.totalQt = total_qt;
		this.totalPrix = total_prix;
		this.payement = payement;
		this.livraison = livraison;
		this.panier = panier;
		this.address = address;
		this.creationDate = creation_Date;
		this.updatedDate = updated_Date;
	}
	
	public Facturation(Integer total_qt, Double total_prix, Payement payement, Livraison livraison, Panier panier, Address address, Date creation_Date, Date updated_Date) {
		this.totalQt = total_qt;
		this.totalPrix = total_prix;
		this.payement = payement;
		this.livraison = livraison;
		this.panier = panier;
		this.address = address;
		this.creationDate = creation_Date;
		this.updatedDate = updated_Date;
	}
	
	public Facturation(Integer total_qt, Double total_prix, Payement payement, Livraison livraison, Panier panier, Address address) {
		this.totalQt = total_qt;
		this.totalPrix = total_prix;
		this.payement = payement;
		this.livraison = livraison;
		this.panier = panier;
		this.address = address;
	}
	
	public Facturation(Integer total_qt, Double total_prix, Panier panier, Address address) {
		this.totalQt = total_qt;
		this.totalPrix = total_prix;
		this.panier = panier;
		this.address = address;
	}

	//GETTER
	public Integer getId() {
		return id;
	}
	
	public Integer getTotalQt() {
		return totalQt;
	}
	
	public Double getTotalPrix() {
		return totalPrix;
	}
	
	public Payement getPayement() {
		return payement;
	}
	
	public Livraison getLivraison() {
		return livraison;
	}
	
	public Panier getPanier() {
		return panier;
	}


	public Address getAddress() {
		return address;
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

	public void setTotalQt(Integer total_qt) {
		this.totalQt = total_qt;
	}

	public void setTotalPrix(Double total_prix) {
		this.totalPrix = total_prix;
	}

	public void setPayement(Payement payement) {
		this.payement = payement;
	}

	public void setLivraison(Livraison livraison) {
		this.livraison = livraison;
	}

	public void setPanier(Panier panier) {
		this.panier = panier;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Override
	public String toString() {
		String toString = String.format("\n[id=%s, totQt=%s, totPrix=%s, payement=%s, livraison=%s, panier=%s, adresse=%s, creationDate=%s, updatedDate=%s", id, totalQt, totalPrix, payement, livraison, panier.toString(), address.toString(), creationDate, updatedDate);
		
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
		if(!(object instanceof Facturation)) {
			return false;
		}
		
		Facturation other = (Facturation) object;
		
		if((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		
		return true;
	}
}

