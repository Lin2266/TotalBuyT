package uuu.totalbuy.test;

public enum Season {
    // 基礎
    //SPRING, SUMMER, FALL, WINTER
    
    // (1)
    // 成員名稱後面使用左右刮號, 裡面設定對應的資料
    // 使用這種寫法最後要加;
    SPRING("Spring"), SUMMER("Summer"), FALL("Fall"), WINTER("Winter");
    
    // (2) 宣告儲存成員對應資料的屬性
    private String name;
    
    // (3) 接收成員對應資料的建構子
    private Season(String name) {
        this.name = name;
    }
    
    // (4)可有可無
    public String getName() {
        return name;
    }
    
}
