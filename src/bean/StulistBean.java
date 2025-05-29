package bean;

import java.io.Serializable;

public class StulistBean implements Serializable {
    private int id;
    private String name;
    private String className; // 予約語「class」を避けるため変更

    public int getId() {
        return id;
    }

    public void setId(int id) { // 戻り値をStringからvoidに修正
        this.id = id; // セミコロン追加
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() { // フィールド名に合わせて変更
        return className;
    }

    public void setClassName(String className) { // フィールド名に合わせて変更
        this.className = className;
    }
}