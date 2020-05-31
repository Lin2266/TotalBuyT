package uuu.totalbuy.test;

import uuu.totalbuy.domain.Product;
import uuu.totalbuy.model.ProductService;

public class TestProduct {

    public static void main(String[] args) {        
        Product p = new Product(101, "MP3");
        
        p.setFree(false);
        p.incStock(2);
        
        //ProductService ps = new ProductService();
        System.out.println( ProductService.calculateProductInventory( p ) );
    }
    
}
