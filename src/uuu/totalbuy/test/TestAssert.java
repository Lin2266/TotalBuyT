package uuu.totalbuy.test;

public class TestAssert {

    public static void main(String[] args) {
        
        int age = -2;
        
        // assert 布林值;
        // assert 布林值 : 訊息;
        //assert age >= 0;
        //assert age >= 0 : "Age less than zero";                
        
        assert age >= 0;
        
        assert age++ >= 0;
        
    }
    
}
