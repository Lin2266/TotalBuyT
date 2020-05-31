package uuu.totalbuy.model;

import uuu.totalbuy.domain.Customer;
import uuu.totalbuy.domain.TotalBuyException;

public class CustomerService {
    final private CustomersDAO dao = new CustomersDAO();
    
    public void register(Customer customer) throws TotalBuyException {        
        dao.insert(customer);
        
//        int random = getRandomStatus();        
//        customer.setStatus(random);
        //send mail to confirm
        //sendConfirmMail(random);
    }
    
//    private int getRandomStatus(){
//        return -1000;
//    }

    public Customer login(String id, String pwd) throws TotalBuyException{        
        Customer c = dao.get(id);
        if(c!=null && c.getPassword()!=null){
            if(c.getPassword().equals(pwd)){
                return c;
            }
            System.out.println(c.getPassword());
        }        
        throw new TotalBuyException("登入失敗");
    }

    public void update(Customer data) throws TotalBuyException {
        dao.update(data);
    }
}
