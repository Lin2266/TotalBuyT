package uuu.totalbuy.test;

import uuu.totalbuy.domain.Product;
import uuu.totalbuy.model.ProductService;

public class TestObject2 {

    public static void main(String[] args) {
        Product p = new Product(1, "Mouse", 100);
        //ProductService ps = new ProductService();
        // 呼叫方法/建構子參數傳遞,如果是物件的時候, 傳送的值是參考值
        ProductService.addPrice( p );
        System.out.println( p.getUnitPrice() );
    }
    
}
