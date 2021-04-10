/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Cart;
import Model.Customers;
import Model.OrderDetails;
import Model.Orders;
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
public class OrderSB implements Serializable {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "Ecommerce-1773019PU")
    EntityManager em;

    public ShippingDetails getOneShippingDetails(int sdId) {
        ShippingDetails newSd = new ShippingDetails();
        for (ShippingDetails sd : getShippingDetailsData()) {
            if (sdId == (int) sd.getShippingDetailsId()) {
                newSd = sd;
            }
        }
        return newSd;
    }

    public List<ShippingDetails> getShippingDetailsData() {
        try {
            Query q = em.createNamedQuery("ShippingDetails.findAll");
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Cart> getCartData(Customers c) {
        try {
            Query q = em.createNamedQuery("Cart.findByCustomerId");
            q.setParameter("customerId", c);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean insertToCart(Cart c) {
        try {
            Query q = em.createNamedQuery("Cart.findByProductxCustomer");
            q.setParameter("productId", c.getProductId()).setParameter("customerId", c.getCustomerId());
            if (q.getResultList().isEmpty()) {
                em.persist(c);
            } else {
//                Cart car = (Cart) q.getResultList().get(0);
//                car.setQuantity(car.getQuantity()+c.getQuantity());
//                em.merge(car);
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCartItem(Cart c) {
        try {
            
            em.merge(c);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCartItem(Cart c) {
        try {
            Query q = em.createNamedQuery("Cart.findByCartId");
            q.setParameter("cartId", c.getCartId());
            Cart car = (Cart) q.getResultList().get(0);
            em.remove(car);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertOrder(Orders o) {
        try {
            em.persist(o);
            for (OrderDetails od : o.getOrderDetailsList()) {
                od.setOrderId(o);
                em.persist(od);
                od.getProductId().setStock(od.getProductId().getStock()-od.getQuantity());
                em.merge(od.getProductId());
                Query q = em.createNamedQuery("Cart.findByProductxCustomer");
                q.setParameter("productId", od.getProductId()).setParameter("customerId", o.getCustomerId());
                Cart c = (Cart) q.getResultList().get(0);
                em.remove(c);
            }
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
