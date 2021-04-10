/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Customers;
import Model.ShippingDetails;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ileven
 */
@Stateless
public class CustomerSB implements Serializable {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "Ecommerce-1773019PU")
    EntityManager em;

    public List<Customers> getAllCustomers() {
        try {
            Query q = em.createNamedQuery("Customers.findAll");
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ShippingDetails> getAllShipping(Customers c) {
        try {
            Query q = em.createNamedQuery("ShippingDetails.findByCustomer");
            q.setParameter("customerId", c);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean insertCustomer(Customers cust) {
        try {
            em.persist(cust);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertShippingDetails(ShippingDetails det) {
        try {
            em.persist(det);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCustomer(Customers cust) {
        try {
            em.merge(cust);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateShippingDetails(ShippingDetails det) {
        try {
            em.merge(det);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCustomer(Customers cust) {
        try {
            Query q = em.createNamedQuery("Customers.findByCustomerId");
            q.setParameter("customerId", cust.getCustomerId());
            Customers cus = (Customers) q.getResultList().get(0);
            em.remove(cus);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteShippingDetails(ShippingDetails det) {
        try {
            Query q = em.createNamedQuery("ShippingDetails.findByShippingDetailsId");
            q.setParameter("shippingDetailsId", det.getShippingDetailsId());
            ShippingDetails deta = (ShippingDetails) q.getResultList().get(0);
            em.remove(deta);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean customerLogin(String email, String password) {
        try {
            Query q = em.createNamedQuery("Customers.login");
            q.setParameter("email", email).setParameter("password", password);
            if (q.getResultList().size() == 1) {

                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean adminLogin(String user, String password) {
        try {
            Query q = em.createNamedQuery("Administrators.login");
            q.setParameter("username", user).setParameter("password", password);
            if (q.getResultList().size() == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkExistingEmail(String email) {
        try {
            Query q = em.createNamedQuery("Customers.findByEmail");
            q.setParameter("email", email);
            if (q.getResultList().size() == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Customers setLoggedCustomer(String email) {
        try {
            Query q = em.createNamedQuery("Customers.findByEmail");
            q.setParameter("email", email);
            if (q.getResultList().size() == 1) {
                return (Customers) q.getResultList().get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isEnabled(String email) {
        try {
            Query q = em.createNamedQuery("Customers.findByEmail");
            q.setParameter("email", email);
            Customers c = (Customers) q.getResultList().get(0);
            if (c.getEnabled() == true) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean enableCustomer(String email, String token) {
        try {
            Query q = em.createNamedQuery("Customers.activate");
            q.setParameter("email", email).setParameter("token", token);
            Customers c = (Customers) q.getResultList().get(0);
            c.setEnabled(Boolean.TRUE);
            em.merge(c);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
