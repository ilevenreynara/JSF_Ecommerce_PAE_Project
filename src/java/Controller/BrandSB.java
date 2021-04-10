/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Brand;
import Model.BrandToCat;
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
public class BrandSB implements Serializable{

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName="Ecommerce-1773019PU")
    EntityManager em;
    
    public List<Brand> getAllBrand(){
        try {
            Query q = em.createNamedQuery("Brand.findAll");
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<Categories> getCategories(){
        try {
            Query q = em.createNamedQuery("Categories.findAll");
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<BrandToCat> getBrandCategories(Brand b){
        try {
            Query q = em.createNamedQuery("BrandToCat.findBrandCategories");
            q.setParameter("brandId", b);        
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean insertBrand(Brand brand){
        try {
            em.persist(brand);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateBrand(Brand brand){
        try {
            em.merge(brand);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteBrand(Brand brand){
        try {
            Query q = em.createNamedQuery("Brand.findByBrandId");
            q.setParameter("brandId", brand.getBrandId());
            Brand b = (Brand) q.getResultList().get(0);
            em.remove(b);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean insertBrandCat(BrandToCat btc){
        try {
            Query q = em.createNamedQuery("BrandToCat.check");
            q.setParameter("brandId", btc.getBrandId()).setParameter("categoryId", btc.getCategoryId());
            if(q.getResultList().isEmpty()){
                em.persist(btc);
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    public boolean removeBrandCat(BrandToCat btc){
        try {
            Query q = em.createNamedQuery("BrandToCat.findByBrandcatId");
            q.setParameter("brandcatId", btc.getBrandcatId());
            BrandToCat bc = (BrandToCat) q.getResultList().get(0);
            em.remove(bc);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
