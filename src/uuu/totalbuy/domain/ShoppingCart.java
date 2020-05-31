package uuu.totalbuy.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Administrator
 */
public class ShoppingCart {
    private Customer user;
    private Map<Product, Integer> cart;
    public ShoppingCart(){
        cart = new HashMap<Product, Integer>(){
            @Override
            public String toString() {
                StringBuilder info = new StringBuilder();
                for(Product p:this.keySet()){
                    info.append(p.toString());
                    info.append(",\n數量:");
                    info.append(this.get(p));
                    info.append('\n');
                }                
                return info.toString();
            }
        };
    }
    
    public ShoppingCart(Customer user){
        this();
        this.user = user;
    }
    
    public Customer getUser() {
        return user;
    }

    public void setUser(Customer user) {
        this.user = user;
    }
    
    public void add(Product p){
        add(p, 1);
    }
    
    public void add(Product p, int quantity){
        Integer q = cart.get(p);
        if(q!=null){
            cart.put(p, quantity+q);
        }else{
            cart.put(p, quantity);
        }        
    }
    
    public void update(Product p, int quantity){
        cart.put(p, quantity);    
    }
    
    public void remove(Product p){
        cart.remove(p);
    }
    
    public Set<Product> getKeySet(){
        return cart.keySet();
    }
    
    public int getQuentity(Product p){
        Integer q = cart.get(p);
        return (q==null?0:q);
    }

    public double getListTotalAmount(){
        if(cart!=null && !cart.isEmpty()){
            double total=0;
            for(Product p:cart.keySet()){
                total += p.getUnitPrice() * cart.get(p);
            }            
            return total;
        }else{        
            return 0;
        }
    }

    public double getTotalAmount(){
        if (user==null || !(user instanceof VIP)){
            return this.getListTotalAmount();
        }
        if(cart!=null && !cart.isEmpty()){
            double total=0;
            for(Product p:cart.keySet()){
                if(p instanceof Outlet){                
                    total += p.getUnitPrice() * cart.get(p);
                }else{                         
                    total += p.getUnitPrice()*(100-((VIP)user).getDiscount())/100* cart.get(p);
                }                
            }            
            return total;
        }else{        
            return 0;
        }
    }    
    
    @Override
    public String toString() {
        return "購物車{" + "客戶:" + user + "\n,"
                + " 買了[" + cart + "]},\n"
                + " 總金額:" + this.getTotalAmount();
    }
}
