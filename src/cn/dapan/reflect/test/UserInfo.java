package cn.dapan.reflect.test;

public class UserInfo {

    private int id;
    private String name;

    private UserInfo() {

    }

    public UserInfo(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void log() {
        System.out.println(id + ":" + name);
    }
}
