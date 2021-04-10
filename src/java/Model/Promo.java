/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author ileven
 */
@Entity
@Table(name = "promo")
@NamedQueries({
    @NamedQuery(name = "Promo.findAll", query = "SELECT p FROM Promo p"),
    @NamedQuery(name = "Promo.findByPromoId", query = "SELECT p FROM Promo p WHERE p.promoId = :promoId"),
    @NamedQuery(name = "Promo.findByName", query = "SELECT p FROM Promo p WHERE p.name = :name"),
    @NamedQuery(name = "Promo.findByStartDate", query = "SELECT p FROM Promo p WHERE p.startDate = :startDate"),
    @NamedQuery(name = "Promo.findByEndDate", query = "SELECT p FROM Promo p WHERE p.endDate = :endDate"),
    @NamedQuery(name = "Promo.findByProducts", query = "SELECT p FROM Promo p WHERE p.products = :products"),
    @NamedQuery(name = "Promo.findByCategories", query = "SELECT p FROM Promo p WHERE p.categories = :categories"),
    @NamedQuery(name = "Promo.findByDiscounts", query = "SELECT p FROM Promo p WHERE p.discounts = :discounts")})
public class Promo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "promoId")
    private Integer promoId;
    @Size(max = 20)
    @Column(name = "name")
    private String name;
    @Column(name = "startDate")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "endDate")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Size(max = 1000)
    @Column(name = "products")
    private String products;
    @Size(max = 1000)
    @Column(name = "categories")
    private String categories;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "discounts")
    private Double discounts;

    public Promo() {
    }

    public Promo(Integer promoId) {
        this.promoId = promoId;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public Double getDiscounts() {
        return discounts;
    }

    public void setDiscounts(Double discounts) {
        this.discounts = discounts;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (promoId != null ? promoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Promo)) {
            return false;
        }
        Promo other = (Promo) object;
        if ((this.promoId == null && other.promoId != null) || (this.promoId != null && !this.promoId.equals(other.promoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Promo[ promoId=" + promoId + " ]";
    }
    
}
