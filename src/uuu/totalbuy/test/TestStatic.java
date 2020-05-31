package uuu.totalbuy.test;

public class TestStatic {

    // JVM 呼叫 main()
    // public: 
    // static: 
    // java TestStatic Hello Simon "Hi Mary"
    //  args[0] = "Hello"
    //  args[1] = "Simon"
    //  args[2] = "Hi Mary"
    public static void main(String[] args) {
        
        MyStatic ms = new MyStatic();
        MyStatic ms2 = new MyStatic();
        //MyStatic.counter = 100;
        MyStatic ms3 = new MyStatic();
        MyStatic ms4 = new MyStatic();
        MyStatic ms5 = new MyStatic();
        
        System.out.println(ms.getSerial());
        System.out.println(ms2.getSerial());
        System.out.println(ms3.getSerial());
        System.out.println(ms4.getSerial());
        System.out.println(ms5.getSerial());
        
        /*
        System.out.println(ms.sv);
        System.out.println(ms2.sv);
        System.out.println(ms3.sv);
        
        ms2.sv = 3;
        
        System.out.println(ms.sv);
        System.out.println(ms2.sv);
        System.out.println(ms3.sv);
        */
    }
    
}

