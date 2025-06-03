package bean;

import java.io.Serializable;

public class UserBean implements Serializable {
    private int id;
    private int schoolId;
    private String password;
    private String name;
    private int privilege;

    // デフォルトコンストラクタ
    public UserBean() {}

    // 内部処理用コンストラクタ
    public UserBean(int id, int schoolId, String password, String name, int privilege) {
        this.id = id;
        this.schoolId = schoolId;
        this.password = password;
        this.name = name;
        this.privilege = privilege;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getSchoolId() { return schoolId; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getPrivilege() { return privilege; }
}