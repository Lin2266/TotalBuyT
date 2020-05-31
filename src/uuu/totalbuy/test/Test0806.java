package uuu.totalbuy.test;

import uuu.totalbuy.domain.Outlet;
import uuu.totalbuy.domain.VIP;

public class Test0806 {

    public static void main(String[] args) {
        VIP vip = new VIP("A123456789", "Simon");
        Outlet outlet = new Outlet(101, "Mouse", 100);
        
        System.out.println(outlet.getUnitPrice());
        System.out.println( outlet.getUnitPrice() * vip.getRate() );
    }
    
}
