/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Brand;
import Model.BrandToCat;
import Model.Categories;
import Model.Products;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ileven
 */
@Stateless
public class ProductsSB implements Serializable {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "Ecommerce-1773019PU")
    EntityManager em;

    public List<Categories> getCatData() {
        try {
            Query q = em.createNamedQuery("Categories.findAll");
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Products> getProdData() {
        try {
            Query q = em.createNamedQuery("Products.findAll");
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<Products> getMostViewed() {
        try {
            Query q = em.createNamedQuery("Products.findPopular");
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Products> getCustProdData() {
        try {
            Query q = em.createNamedQuery("Products.findByDiscontinued");
            q.setParameter("discontinued", Boolean.FALSE);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Products> searchCustProdData(String keyword) {
        try {
            Query q = em.createQuery("SELECT p FROM Products p WHERE p.categoryId.name LIKE '%" + keyword + "%'"
                    + "OR p.name LIKE '%" + keyword + "%'"
                    + "OR p.brandId.name LIKE '%" + keyword + "%'");
            q.setParameter("discontinued", Boolean.FALSE);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean insertProduct(Products prod) {
        try {
            Query q = em.createNamedQuery("Brand.findByBrandId");
            q.setParameter("brandId", prod.getBrandId().getBrandId());
            Brand b = (Brand) q.getResultList().get(0);
            prod.setBrandId(b);
            Query p = em.createNamedQuery("Products.findByName");
            p.setParameter("name", prod.getName());
            if (p.getResultList().isEmpty()) {
                em.persist(prod);
            } else {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateProduct(Products prod) {
        try {
            Query q = em.createNamedQuery("Brand.findByBrandId");
            q.setParameter("brandId", prod.getBrandId().getBrandId());
            Brand b = (Brand) q.getResultList().get(0);
            prod.setBrandId(b);
            em.merge(prod);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteProduct(Products prod) {
        try {
            Query q = em.createNamedQuery("Products.findByProductId");
            q.setParameter("productId", prod.getProductId());
            Products produ = (Products) q.getResultList().get(0);
            em.remove(produ);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<BrandToCat> getBrandCatData(Categories c) {
        try {
            Query q = em.createNamedQuery("BrandToCat.findCategory");
            q.setParameter("categoryId", c);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public boolean updateViewCount(Products p) {
        try {
            Products prod = em.find(Products.class, p.getProductId());
            em.lock(prod, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
            prod.setViewCount(p.getViewCount().add(BigInteger.ONE));
            em.merge(prod);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

}
