package uuu.totalbuy.test;

import uuu.totalbuy.domain.Product;

public class TestObject {

    public static void main(String[] args) {
        
        Product p = new Product(1, "Mouse", 100);
        Product p2 = new Product(2, "Keyboard", 200);
       
        // 指定參考值
        //p2= p;
        // 自動回收, GC, 清除沒有使用的物件
        p = p2;
        
        p.setUnitPrice(300);
        
        p = new Product(3, "Phone", 500);
        System.out.println( p2.getUnitPrice() );
        
        // 把兩個物件變成請JAVA幫我們回收狀態
        p = null;
        p2 = null;
        
        //System.out.println(p.getDetails());
        //System.out.println(p2.getDetails());
        
        // 物件參考變數不可以使用==比較它們是否一樣
        // 物件參考變數使用==是比較它們的參考值
        //System.out.println( p == p2 );
    }
    
}
