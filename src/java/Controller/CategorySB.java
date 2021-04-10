/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Categories;
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
public class CategorySB implements Serializable{

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "Ecommerce-1773019PU")
    EntityManager em;
    
    public List<Categories> getAllCategories(){
        try {
            Query q = em.createNamedQuery("Categories.findAll");
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean insertCategory(Categories cat){
        try {
            em.persist(cat);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateCategory(Categories cat){
        try {
            em.merge(cat);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteCategory(Categories cat){
        try {
            Query q = em.createNamedQuery("Categories.findByCategoryId");
            Query x = em.createNamedQuery("BrandToCat.findCategory");
            x.setParameter("categoryId", cat);
            q.setParameter("categoryId", cat.getCategoryId());
            Categories catu = (Categories) q.getResultList().get(0);
            for (Object btc : x.getResultList()) {
                em.remove(btc);
            }
            em.remove(catu);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
