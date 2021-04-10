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
import javax.validation.constraints.Size;

/**
 *
 * @author ileven
 */
@Entity
@Table(name = "shipping_details")
@NamedQueries({
    @NamedQuery(name = "ShippingDetails.findAll", query = "SELECT s FROM ShippingDetails s"),
    @NamedQuery(name = "ShippingDetails.findByShippingDetailsId", query = "SELECT s FROM ShippingDetails s WHERE s.shippingDetailsId = :shippingDetailsId"),
    @NamedQuery(name = "ShippingDetails.findByCustomer", query = "SELECT s FROM ShippingDetails s WHERE s.customerId = :customerId"),
    @NamedQuery(name = "ShippingDetails.findByDescription", query = "SELECT s FROM ShippingDetails s WHERE s.description = :description"),
    @NamedQuery(name = "ShippingDetails.findByReceiverName", query = "SELECT s FROM ShippingDetails s WHERE s.receiverName = :receiverName"),
    @NamedQuery(name = "ShippingDetails.findByAddress", query = "SELECT s FROM ShippingDetails s WHERE s.address = :address"),
    @NamedQuery(name = "ShippingDetails.findByPhone", query = "SELECT s FROM ShippingDetails s WHERE s.phone = :phone")})
public class ShippingDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "shippingDetailsId")
    private Integer shippingDetailsId;
    @Size(max = 20)
    @Column(name = "description")
    private String description;
    @Size(max = 50)
    @Column(name = "receiverName")
    private String receiverName;
    @Size(max = 200)
    @Column(name = "address")
    private String address;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 15)
    @Column(name = "phone")
    private String phone;
    @JoinColumn(name = "customerId", referencedColumnName = "customerId")
    @ManyToOne
    private Customers customerId;

    public ShippingDetails() {
    }

    public ShippingDetails(Integer shippingDetailsId) {
        this.shippingDetailsId = shippingDetailsId;
    }

    public Integer getShippingDetailsId() {
        return shippingDetailsId;
    }

    public void setShippingDetailsId(Integer shippingDetailsId) {
        this.shippingDetailsId = shippingDetailsId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Customers getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customers customerId) {
        this.customerId = customerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (shippingDetailsId != null ? shippingDetailsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ShippingDetails)) {
            return false;
        }
        ShippingDetails other = (ShippingDetails) object;
        if ((this.shippingDetailsId == null && other.shippingDetailsId != null) || (this.shippingDetailsId != null && !this.shippingDetailsId.equals(other.shippingDetailsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.ShippingDetails[ shippingDetailsId=" + shippingDetailsId + " ]";
    }
    
}
