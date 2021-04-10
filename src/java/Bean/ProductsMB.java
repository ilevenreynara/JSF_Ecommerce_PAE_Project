/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Controller.ProductsSB;
import Model.Categories;
import Model.Products;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.servlet.http.Part;
import Messages.Notifications;
import Model.Brand;
import Model.BrandToCat;
import java.math.BigInteger;
import java.nio.file.StandardCopyOption;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.management.Notification;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author ileven
 */
@Named(value = "productsMB")
@SessionScoped
public class ProductsMB implements Serializable {

    /**
     * Creates a new instance of ProductsMB
     */
    @EJB
    private ProductsSB psb;
    private Products product;
    protected static Products selectedProduct;
    private Categories category;
    private Brand brand;
    private List<Categories> catData;
    private List<BrandToCat> brandData;
    private List<Products> prodData;
    private List<Products> mostViewedProd;
    private List<Products> custProdData;
    private Part uploadedFile;
    private String search;
    private int filter;
    //path folder harus diubah sesuai dengan path project
    //Belom dapet cara get si path project netbeans
    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    private String folder = "C:\\Users\\ileve\\Documents\\NetBeansProjects\\Ecommerce-1773019\\web\\productImages";
//    private String folder = "C:\\Users\\ileve\\Documents\\NetBeansProjects\\Ecommerce-1773019\\web\\productImages";
    private Notifications message = new Notifications();

    public ProductsMB() {
        product = new Products();
        selectedProduct = new Products();
        category = new Categories();
        catData = new ArrayList<>();
        prodData = new ArrayList<>();
        custProdData = new ArrayList<>();
        mostViewedProd = new ArrayList<>();
        brandData = new ArrayList<>();
        brand = new Brand();

    }

    public void reset() {
        product = new Products();
        selectedProduct = new Products();
        category = new Categories();
        catData = new ArrayList<>();
        custProdData = new ArrayList<>();
        mostViewedProd = new ArrayList<>();
        prodData = new ArrayList<>();
        brand = new Brand();

    }

    public List<Products> getMostViewedProd() {
        if(mostViewedProd.isEmpty()){
            mostViewedProd = psb.getMostViewed();
        }
        return mostViewedProd;
    }

    
    public List<Products> getCustProdData() {
        if (custProdData.isEmpty()) {
            custProdData = psb.getCustProdData();
        }
        return custProdData;
    }

    public boolean saveFile() {
        if (uploadedFile == null) {
            return false;
        } else {
            try (InputStream input = uploadedFile.getInputStream()) {
                System.out.println(folder);
                String fileName = RandomStringUtils.randomAlphabetic(5) + product.getName() + uploadedFile.getSubmittedFileName();
                String x = fileName.replaceAll(" ", "");
                Files.copy(input, new File(folder, x).toPath(), StandardCopyOption.REPLACE_EXISTING);
                this.product.setPicture(x);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public void insertProduct() {
        for (Categories cat : catData) {
            if ((int) cat.getCategoryId() == (int) category.getCategoryId()) {
                product.setCategoryId(cat);
            }
        }
        product.setViewCount(BigInteger.ZERO);
        product.setBrandId(brand);
        if (saveFile()) {
            if (psb.insertProduct(product)) {
                message.showInfo("Success", product.getName() + " inserted");
                System.out.println(folder + "/" + product.getPicture());
            } else {
                message.showError("Failed", product.getName() + " exist");
            }
        } else {
            message.showError("Failed!", "Failed to upload image");
            reset();
        }
        reset();
    }

    public void updateProduct() {
        for (Categories cat : catData) {
            if ((int) cat.getCategoryId() == (int) category.getCategoryId()) {
                selectedProduct.setCategoryId(cat);
            }
        }
        product.setBrandId(brand);
        psb.updateProduct(selectedProduct);
        reset();
    }

    public void deleteProduct() {
        File file = new File(folder + "\\" + selectedProduct.getPicture());
        if (file.delete()) {
            psb.deleteProduct(selectedProduct);
        }
        reset();
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public List<BrandToCat> getBrandData() {
        return brandData;
    }

    public Part getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(Part uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public Products getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Products selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public List<Categories> getCatData() {
        if (catData.isEmpty()) {
            catData = psb.getCatData();
        }
        return catData;
    }

    public List<Products> getProdData() {
        if (prodData.isEmpty()) {
            prodData = psb.getProdData();
        }
        return prodData;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public int getFilter() {
        return filter;
    }

    public void setFilter(int filter) {
        this.filter = filter;
    }

    public String viewProduct(Products p) {
        if (psb.updateViewCount(p)) {
            setSelectedProduct(p);
            return "product";
        }else{
            return "customerHome";
        }
//        setSelectedProduct(p);
//        return "product";
    }

    public String searchListener() {
        return "customerCart";
    }

    public void setBrandForSelectedProd() {
        this.brand = selectedProduct.getBrandId();
    }

    // Change Listener
    public void categoryChangeListener(SelectEvent e) {
        Categories cate = new Categories();
        cate.setCategoryId((int) e.getObject());
        for (Categories c : catData) {
            if ((int) cate.getCategoryId() == (int) c.getCategoryId()) {
                cate = c;
            }
        }

        this.brandData = psb.getBrandCatData(cate);
    }

    public void categoryChange(ValueChangeEvent e) {
        Categories cate = new Categories();
        cate.setCategoryId((int) e.getNewValue());
        for (Categories c : catData) {
            if ((int) cate.getCategoryId() == (int) c.getCategoryId()) {
                cate = c;
            }
        }

        this.brandData = psb.getBrandCatData(cate);
    }

    public void searchProducts() {
        this.custProdData.clear();
        for (Products p : psb.getCustProdData()) {
            if (p.getName().toLowerCase().contains(search.toLowerCase())
                    || p.getBrandId().getName().toLowerCase().contains(search.toLowerCase())
                    || p.getCategoryId().getName().toLowerCase().contains(search.toLowerCase())) {
                custProdData.add(p);
            }
        }
        message.showInfo("searching for " + search, search);
    }
}
