/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uuu.totalbuy.test;

import java.util.logging.Level;
import java.util.logging.Logger;
import uuu.totalbuy.domain.Customer;
import uuu.totalbuy.domain.TotalBuyException;
import uuu.totalbuy.model.CustomerService;

/**
 *
 * @author Administrator
 */
public class TestCustomerService {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CustomerService service = new CustomerService();
        try {
            Customer c1 = service.login("A123456789", "123456");
            System.out.println(c1);
        } catch (TotalBuyException ex) {
            System.out.println(ex);
        }
        
    }
    
}
