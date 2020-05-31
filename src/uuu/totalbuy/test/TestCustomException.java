package uuu.totalbuy.test;

import java.util.Date;
import uuu.totalbuy.domain.Customer;
import uuu.totalbuy.domain.Product;
import uuu.totalbuy.model.OrderService;
import uuu.totalbuy.model.OutOfStockException;

public class TestCustomException {

    public static void main(String[] args) {
        Customer c = new Customer("101", "Simon", "123456");//, new Date(), 'M', true, "simon@google.com");
        System.out.println(c);
        Product p = new Product(999, "TV", 1000, false, 10);
        
        try {
            OrderService.order(c, p, 50);
        }
        catch (OutOfStockException e) {
            System.out.println(e.toString());
        }
        
    }
    
}
