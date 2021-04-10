/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author ileven
 */
@Entity
@Table(name = "brand_to_cat")
@NamedQueries({
    @NamedQuery(name = "BrandToCat.findAll", query = "SELECT b FROM BrandToCat b"),
    @NamedQuery(name = "BrandToCat.findBrandCategories", query = "SELECT b FROM BrandToCat b WHERE b.brandId = :brandId"),
    @NamedQuery(name = "BrandToCat.check", query = "SELECT b FROM BrandToCat b WHERE b.brandId = :brandId AND b.categoryId = :categoryId"),
    @NamedQuery(name = "BrandToCat.findByBrandcatId", query = "SELECT b FROM BrandToCat b WHERE b.brandcatId = :brandcatId")})
public class BrandToCat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "brandcatId")
    private Integer brandcatId;
    @JoinColumn(name = "brandId", referencedColumnName = "brandId")
    @ManyToOne(optional = false)
    private Brand brandId;
    @JoinColumn(name = "categoryId", referencedColumnName = "categoryId")
    @ManyToOne(optional = false)
    private Categories categoryId;

    public BrandToCat() {
    }

    public BrandToCat(Integer brandcatId) {
        this.brandcatId = brandcatId;
    }

    public Integer getBrandcatId() {
        return brandcatId;
    }

    public void setBrandcatId(Integer brandcatId) {
        this.brandcatId = brandcatId;
    }

    public Brand getBrandId() {
        return brandId;
    }

    public void setBrandId(Brand brandId) {
        this.brandId = brandId;
    }

    public Categories getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Categories categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (brandcatId != null ? brandcatId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BrandToCat)) {
            return false;
        }
        BrandToCat other = (BrandToCat) object;
        if ((this.brandcatId == null && other.brandcatId != null) || (this.brandcatId != null && !this.brandcatId.equals(other.brandcatId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.BrandToCat[ brandcatId=" + brandcatId + " ]";
    }
    
}
