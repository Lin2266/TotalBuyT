package uuu.totalbuy.test;

import java.util.Date;
import uuu.totalbuy.domain.Customer;
import uuu.totalbuy.domain.Outlet;
import uuu.totalbuy.domain.Product;

public class Test0923 {
    
    public static void main(String[] args) {
        String ageStr = "35.2";
        double age = Double.parseDouble(ageStr);
        System.out.println(age);
        System.out.println(age * 12);
    
        /*
        Product p = new Product(101, "Mouse", 50, false, 10);
        p.equals(new Integer(3));
        
        Product p2 = new Product(101, "Mouse", 50, false, 10);
        System.out.println("p.equals(p2): " + p.equals(p2));
        
        Outlet o = new Outlet(102, "Keyboard", 100, false, 50, 20);
        Outlet o2 = new Outlet(102, "Keyboard", 100, false, 50, 20);
        System.out.println("o.equals(o2): " + o.equals(o2));
        */
        /*
        Date d = new Date();
        
        Customer c = new Customer("123457", "Simon", "Taipei", d, 'M', true, "simon@google.com");
        Customer c2 = new Customer("123456", "Simon", "Taipei", d, 'M', true, "simon6@google.com");
        
        System.out.println( c.equals(c2) );
        //System.out.println( c.equals(d) );
        //System.out.println( c.equals(null) );
        */
    }
    
}
