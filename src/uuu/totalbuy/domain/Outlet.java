package uuu.totalbuy.domain;

public class Outlet extends Product {
    
    private int discount = 10;
    
    public Outlet(){
    }
    
    public Outlet(int id, String name, double unitPrice) {
        super(id, name, unitPrice);
    }
    
    public Outlet(int id, String name, double unitPrice, boolean free, 
                   int stock, int discount) {
        super(id, name, unitPrice, free, stock);
        this.discount = discount;
    }
    /* 這是測試 p9-29, ...
    public Outlet(int discount) {
        this.discount = discount;
    }
    */
    
    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
    
    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        
        if (obj != null) {
            if (obj instanceof Product) {
                Outlet out = (Outlet) obj;
                
                if ( super.equals(out) && 
                     getDiscount() == out.getDiscount() ) {
                    result = true;
                }
            }
        }
        return result;
    }
    
    @Override
    public String toString() {
        return super.toString() + "\n" + 
               "Discount:   " + getDiscount();
    }
    
    @Override
    public double getUnitPrice() {
        return super.getUnitPrice() * ((100 - getDiscount()) / 100.0);
    }
    
    public double getListPrice() {
        return super.getUnitPrice();
    }
}
