package uuu.totalbuy.test;

import java.util.List;
import uuu.totalbuy.domain.Customer;
import uuu.totalbuy.domain.Product;
import uuu.totalbuy.domain.ShoppingCart;
import uuu.totalbuy.domain.TotalBuyException;
import uuu.totalbuy.model.CustomerService;
import uuu.totalbuy.model.ProductService;

/**
 *
 * @author Administrator
 */
public class TestShoppingCart {
    public static void main(String[] args) throws TotalBuyException {
        List<Product> list = new ProductService().getAll();
        System.out.println(list);
        
        Customer user = new CustomerService().login("A123456789", "123456");
        ShoppingCart cart2 = new ShoppingCart(user);
        
        cart2.add(list.get(0));//1
        cart2.add(new ProductService().get(1), 2);
        System.out.println(cart2);
        
        cart2.add(list.get(3), 2);
        System.out.println(cart2);
        
        Customer vip = new CustomerService().login("A223456781", "123456");
        System.out.println(vip);
        
        ShoppingCart cart = new ShoppingCart(vip);
        
        cart.add(list.get(0));//1
        cart.add(new ProductService().get(1), 2);
        System.out.println(cart);
        
        cart.add(list.get(3), 2);
        System.out.println(cart.getListTotalAmount());        
        System.out.println(cart.getTotalAmount());        
    }
}
