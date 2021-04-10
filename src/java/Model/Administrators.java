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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ileven
 */
@Entity
@Table(name = "administrators")
@NamedQueries({
    @NamedQuery(name = "Administrators.findAll", query = "SELECT a FROM Administrators a"),
    @NamedQuery(name = "Administrators.login", query = "SELECT a FROM Administrators a WHERE a.username = :username AND a.password = :password"),
    @NamedQuery(name = "Administrators.findByAdministratorId", query = "SELECT a FROM Administrators a WHERE a.administratorId = :administratorId"),
    @NamedQuery(name = "Administrators.findByName", query = "SELECT a FROM Administrators a WHERE a.name = :name"),
    @NamedQuery(name = "Administrators.findByPhone", query = "SELECT a FROM Administrators a WHERE a.phone = :phone"),
    @NamedQuery(name = "Administrators.findByAddress", query = "SELECT a FROM Administrators a WHERE a.address = :address"),
    @NamedQuery(name = "Administrators.findByUsername", query = "SELECT a FROM Administrators a WHERE a.username = :username"),
    @NamedQuery(name = "Administrators.findByPassword", query = "SELECT a FROM Administrators a WHERE a.password = :password")})
public class Administrators implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "administratorId")
    private Integer administratorId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "name")
    private String name;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 20)
    @Column(name = "phone")
    private String phone;
    @Size(max = 100)
    @Column(name = "address")
    private String address;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "password")
    private String password;

    public Administrators() {
    }

    public Administrators(Integer administratorId) {
        this.administratorId = administratorId;
    }

    public Administrators(Integer administratorId, String name, String username, String password) {
        this.administratorId = administratorId;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public Integer getAdministratorId() {
        return administratorId;
    }

    public void setAdministratorId(Integer administratorId) {
        this.administratorId = administratorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (administratorId != null ? administratorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Administrators)) {
            return false;
        }
        Administrators other = (Administrators) object;
        if ((this.administratorId == null && other.administratorId != null) || (this.administratorId != null && !this.administratorId.equals(other.administratorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Administrators[ administratorId=" + administratorId + " ]";
    }
    
}
