/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Controller.BrandSB;
import Messages.Notifications;
import Model.Brand;
import Model.BrandToCat;
import Model.Categories;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author ileven
 */
@Named(value = "brandMB")
@SessionScoped
public class BrandMB implements Serializable {

    /**
     * Creates a new instance of BrandMB
     */
    @EJB
    private BrandSB bsb;
    private Brand brand;
    private Brand selectedBrand;
    private BrandToCat brandCat;
    private Categories cat;
    private List<Brand> brandData;
    private List<Categories> catData;
    private List<BrandToCat> brandCatData;
    private boolean editMode = false;
    private Notifications message = new Notifications();

    public BrandMB() {
        brand = new Brand();
        selectedBrand = new Brand();
        brandData = new ArrayList<>();
        catData = new ArrayList<>();
        brandCatData = new ArrayList<>();
        brandCat = new BrandToCat();
        cat = new Categories();
        editMode = false;
    }
    public void reset() {
        brand = new Brand();
        brandData = new ArrayList<>();
        catData = new ArrayList<>();
        brandCatData = new ArrayList<>();
        brandCat = new BrandToCat();
        cat = new Categories();
        editMode = false;
    }
    public void resetBrandCatData(){
        brandCatData.clear();
    }
    public BrandToCat getBrandCat() {
        return brandCat;
    }

    public void setBrandCat(BrandToCat brandCat) {
        this.brandCat = brandCat;
    }

    public Categories getCat() {
        return cat;
    }

    public void setCat(Categories cat) {
        this.cat = cat;
    }
    
    public List<Categories> getCatData() {
        if(catData.isEmpty()){
            catData = bsb.getCategories();
        }
        return catData;
    }

    public List<BrandToCat> getBrandCatData() {
        if(brandCatData.isEmpty()){
            brandCatData = bsb.getBrandCategories(selectedBrand);
        }
        return brandCatData;
    }

    public Brand getSelectedBrand() {
        return selectedBrand;
    }

    public void setSelectedBrand(Brand selectedBrand) {
        this.selectedBrand = selectedBrand;
    }
    
    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
    public void setSelected(Brand brand){
        this.brand = brand;
        this.editMode = true;
    }

    public List<Brand> getBrandData() {
        if(brandData.isEmpty()){
            brandData = bsb.getAllBrand();
        }
        return brandData;
    }
    
    public void insertBrand() {
        if (bsb.insertBrand(brand)) {
            message.showInfo("Success!", "Insert Successful");
        }else{
            message.showFailed("Failed", "Insert Failed");
        }
        reset();
    }
    public void updateBrand(){
        if (bsb.updateBrand(brand)) {
            message.showInfo("Success!", "Update Successful");
        }else{
            message.showFailed("Failed", "Update Failed");
        }
        reset();
    }
    public void deleteBrand(Brand b){
        if (bsb.deleteBrand(b)) {
            message.showInfo("Success!", "Delete Successful");
        }else{
            message.showFailed("Failed", "Delete Failed");
        }
        reset();
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }
    public void addBrandCategory(){
        for (Categories c : catData) {
            if((int)c.getCategoryId() == (int)cat.getCategoryId()){
                brandCat.setCategoryId(c);
            }
        }
        brandCat.setBrandId(selectedBrand);
        if(bsb.insertBrandCat(brandCat)){
            message.showInfo("Success", "Category " + cat.getName() + "added to brand" + selectedBrand.getName());
        }else{
            message.showFailed("Failed", "Category Exist!");
        }
    }
    public void deleteBrandCategory(BrandToCat bc){
        if(bsb.removeBrandCat(bc)){
            message.showInfo("Success", "Category deleted");
        }else{
            message.showFailed("Failed", "Fail to remove category");
        }
    }

}
