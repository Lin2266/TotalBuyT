/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uuu.totalbuy.test;
import uuu.totalbuy.domain.BloodType;
import uuu.totalbuy.domain.Customer;
import uuu.totalbuy.model.CustomersDAO;

/**
 *
 * @author Administrator
 */
public class TestCustomersDAO {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        CustomersDAO dao = new CustomersDAO();
        try{
            Customer c = new Customer(
                    "A123456761", "李四", "123456", "four_lee@uuu.com.tw");
            c.setBirthDate("1988/5/6");
            c.setBloodType(BloodType.AB);
            dao.insert(c);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        
        try {            
            System.out.println(dao.getAll());        
            
            Customer c = dao.get("A123456789"); //block variable
            System.out.println(c);
            
            c.setAddress("台北市復興北路101號12F");
            dao.update(c);
            System.out.println(dao.get("A123456789"));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        try {
            Customer c = dao.get("A223456761");
            System.out.println(c);
        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println(ex.getCause());
        }
        
        try{
            Customer c = dao.get("A123456770");
            dao.delete(c);
            System.out.println(dao.get("A123456770"));
        }catch (Exception ex) {
            System.out.println(ex);
            System.out.println(ex.getCause());
        }
    }

}
