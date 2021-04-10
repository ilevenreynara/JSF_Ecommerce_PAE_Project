/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Controller.OrderSB;
import Messages.Notifications;
import Model.Cart;
import Model.Customers;
import Model.OrderDetails;
import Model.Orders;
import Model.ShippingDetails;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author ileven
 */
@Named(value = "orderMB")
@SessionScoped
public class OrderMB implements Serializable {

    /**
     * Creates a new instance of OrderMB
     */
    @EJB
    private OrderSB osb;
    private Orders order;
    private OrderDetails od;
    private ShippingDetails sd;
    private List<OrderDetails> odData;
    protected static List<Cart> cartData;
    private List<Cart> selectedCart;
    private List<Cart> selectedCart2;
    private Cart cart;
    private Notifications message = new Notifications();
    private double total;
    private List<String> paymentMethod = new ArrayList<>();

    public ShippingDetails getSd() {
        return sd;
    }

    public void setSd(ShippingDetails sd) {
        this.sd = sd;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public OrderMB() {
        sd = new ShippingDetails();
        order = new Orders();
        cart = new Cart();
        od = new OrderDetails();
        odData = new ArrayList<>();
        cartData = new ArrayList<>();
        selectedCart = new ArrayList<>();
        selectedCart2 = new ArrayList<>();

        paymentMethod.add("Internet Banking");
        paymentMethod.add("Bank Transfer");
        paymentMethod.add("OVO");
        paymentMethod.add("Alfa Mart");
    }

    public List<String> getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(List<String> paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void reset() {
        sd = new ShippingDetails();
        order = new Orders();
        cart = new Cart();
        od = new OrderDetails();
        odData = new ArrayList<>();
        cartData = new ArrayList<>();
        selectedCart = new ArrayList<>();
        selectedCart2 = new ArrayList<>();
    }

    public List<Cart> getCartData() {
        if (cartData.isEmpty()) {
            cartData = osb.getCartData(LoginBean.loggedCustomer);
        }
        return cartData;
    }

    public void setCartData(List<Cart> cartData) {
        this.cartData = cartData;
    }

    public List<Cart> getSelectedCart() {
        return selectedCart;
    }

    public void setSelectedCart(List<Cart> selectedCart) {
        this.selectedCart = selectedCart;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public OrderDetails getOd() {
        return od;
    }

    public void setOd(OrderDetails od) {
        this.od = od;
    }

    public List<OrderDetails> getOdData() {
        return odData;
    }

    public void setOdData(List<OrderDetails> odData) {
        this.odData = odData;
    }

    public List<Cart> getSelectedCart2() {
        return selectedCart2;
    }

    public void setSelectedCart2() {
        this.selectedCart2 = this.selectedCart;
    }
    
    public void insertToCart() {
        cart.setCustomerId(LoginBean.loggedCustomer);
        cart.setProductId(ProductsMB.selectedProduct);
        cart.setQuantity(1);
        cart.setSubTotal(ProductsMB.selectedProduct.getSellPrice() * cart.getQuantity());
        if (osb.insertToCart(cart)) {
            message.showInfo("Success!", "This product is updated in your cart");
        } else {
            message.showWarning("Exist!", "Product is in your cart");
        }

        reset();
    }

    public void removeFromCart(Cart c) {
        if (osb.deleteCartItem(c)) {
            message.showInfo("Complete", "Item removed from cart");
        } else {
            message.showError("Failed", "Item wasn't removed");
        }
        reset();
    }

    public void updateCartItem(Cart c) {
        c.setSubTotal(c.getProductId().getSellPrice() * c.getQuantity());
        if (osb.updateCartItem(c)) {
            message.showInfo("Complete", "Item updated");
        } else {
            message.showError("Failed", "Item wasn't updated");
        }
        reset();
    }

    public void onCellEdit(CellEditEvent event) {
        int newQty = (int) event.getNewValue();
        Cart c = cartData.get(event.getRowIndex());
        c.setQuantity(newQty);
        updateCartItem(c);
        cartData.clear();
//        System.out.println(cartData.get(event.getRowIndex()).getProductId().getName());
    }

    public void countTotal() {
        total = 0;
        for (Cart c : selectedCart) {
            total += c.getSubTotal();
        }
    }

    public void makeOrder() {
        for (Cart c : selectedCart2) {
            od = new OrderDetails();
            od.setProductId(c.getProductId());
            od.setDiscount(0.0);
            od.setQuantity(c.getQuantity());
            odData.add(od);
        }
        order.setOrderDetailsList(odData);
        order.setShippingDetail(osb.getOneShippingDetails((int) sd.getShippingDetailsId()));
        order.setCustomerId(LoginBean.loggedCustomer);
        order.setOrderDate(new Date());
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        order.setShippingDate(dt);
        if (osb.insertOrder(order)) {
            reset();
            message.showInfo("Success!", "Purchase Successful!");
        }else{
            message.showError("Failed", "Checkout Canceled");
        }
    }
}
