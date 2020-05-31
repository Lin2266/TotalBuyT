package uuu.totalbuy.test;

import uuu.totalbuy.domain.Customer;
import uuu.totalbuy.domain.Outlet;
import uuu.totalbuy.domain.Product;
import uuu.totalbuy.domain.VIP;

public class Test0919 {

    public static void main(String[] args) {

        Product p = new Product(101, "Mouse");
        System.out.println(p);
        
        Outlet o = new Outlet(102, "Keyboard", 100);
        System.out.println(o);
        
        //VIP vip = new VIP("123456", "Simon");
        //System.out.println(vip);
        
        //Customer c = new Customer("123456", "Simon");
        // 直接顯示物件參考變數, 會自動加上.toString()
        //System.out.println( c );
        // 字串連接物件參考變數, 會自動加上.toString()
        //String s = "Customer: " + c;        
        //System.out.println( c.toString() );
        
    }
    
}
