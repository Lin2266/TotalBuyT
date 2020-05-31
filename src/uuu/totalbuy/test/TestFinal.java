package uuu.totalbuy.test;

public class TestFinal {
    //
    final int v;
    
    //
    public static final int sv;
    
    static {
        // 讀取並設定SV的值
        sv = 5;
    }
        
    public TestFinal() {
        //v = 0;
        this(3);
    }
    
    public TestFinal(int v) {
        this.v = v;
    }
    
}
