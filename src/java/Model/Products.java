/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ileven
 */
@Entity
@Table(name = "products")
@NamedQueries({
    @NamedQuery(name = "Products.findAll", query = "SELECT p FROM Products p"),
    @NamedQuery(name = "Products.findPopular", query = "SELECT p FROM Products p ORDER BY p.viewCount DESC"),
    @NamedQuery(name = "Products.findByProductId", query = "SELECT p FROM Products p WHERE p.productId = :productId"),
    @NamedQuery(name = "Products.findByName", query = "SELECT p FROM Products p WHERE p.name = :name"),
    @NamedQuery(name = "Products.findByBuyPrice", query = "SELECT p FROM Products p WHERE p.buyPrice = :buyPrice"),
    @NamedQuery(name = "Products.findBySellPrice", query = "SELECT p FROM Products p WHERE p.sellPrice = :sellPrice"),
    @NamedQuery(name = "Products.findByStock", query = "SELECT p FROM Products p WHERE p.stock = :stock"),
    @NamedQuery(name = "Products.findByPicture", query = "SELECT p FROM Products p WHERE p.picture = :picture"),
    @NamedQuery(name = "Products.findByDiscontinued", query = "SELECT p FROM Products p WHERE p.discontinued = :discontinued"),
    @NamedQuery(name = "Products.findByViewCount", query = "SELECT p FROM Products p WHERE p.viewCount = :viewCount")})
public class Products implements Serializable {

    @Column(name = "version")
    @Version
    private Integer version;

    private static final long serialVersionUID = 1L;
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "productId")
    private Integer productId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "buyPrice")
    private double buyPrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sellPrice")
    private double sellPrice;
    @Column(name = "stock")
    private Integer stock;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "specification")
    private String specification;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "picture")
    private String picture;
    @Column(name = "discontinued")
    private Boolean discontinued;
    @Column(name = "view_count")
    private BigInteger viewCount;
    @JoinColumn(name = "brandId", referencedColumnName = "brandId")
    @ManyToOne
    private Brand brandId;
    @JoinColumn(name = "categoryId", referencedColumnName = "categoryId")
    @ManyToOne(optional = false)
    private Categories categoryId;
    
    public Products() {
    }
    
    public Products(Integer productId) {
        this.productId = productId;
    }

    public Products(Integer productId, String name, double buyPrice, double sellPrice, String picture) {
        this.productId = productId;
        this.name = name;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.picture = picture;
    }
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Boolean getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(Boolean discontinued) {
        this.discontinued = discontinued;
    }

    public BigInteger getViewCount() {
        return viewCount;
    }

    public void setViewCount(BigInteger viewCount) {
        this.viewCount = viewCount;
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
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Products)) {
            return false;
        }
        Products other = (Products) object;
        if ((this.productId == null && other.productId != null) || (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Products[ productId=" + productId + " ]";
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
    
}
