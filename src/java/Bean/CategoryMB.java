/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Controller.CategorySB;
import Model.Categories;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author ileven
 */
@Named(value = "categoryMB")
@SessionScoped
public class CategoryMB implements Serializable {

    /**
     * Creates a new instance of CategoryMB
     */
    @EJB
    private CategorySB csb;
    private Categories category;
    private List<Categories> catData;
    private boolean editMode = false;
    File aDirectory = new File("C:\\Users\\ileve\\Documents\\NetBeansProjects\\Ecommerce-1773019\\web\\icons");
    String[] filesInDir = aDirectory.list();

    public CategoryMB() {
        editMode = false;
        category = new Categories();
        catData = new ArrayList<>();
    }

    public void reset() {
        editMode = false;
        category = new Categories();
        catData = new ArrayList<>();
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public String[] getFilesInDir() {
        return filesInDir;
    }
    
    public List<Categories> getCatData() {
        if (catData.isEmpty()) {
            catData = csb.getAllCategories();
        }
        return catData;
    }

    public void insertCat() {
        
        if(csb.insertCategory(category)){
            System.out.println("Worked");
        }else{
            System.out.println("Error");
        }
        reset();
    }

    public void updateCat() {
        
        csb.updateCategory(category);
        reset();
    }

    public void deleteCat(Categories c) {
        if(csb.deleteCategory(c)){
            System.out.println("Worked");
        }else{
            System.out.println("Error");
        }
        reset();
    }

    public void setSelectedCat(Categories c) {
        this.category = c;
        editMode = true;
    }

    public boolean isEditMode() {
        return editMode;
    }
    
}
