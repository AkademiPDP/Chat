package uz.pdp.DB.entity;


import java.io.Serializable;
import java.time.ZoneId;
import java.util.UUID;

public class  User implements Serializable {

    private UUID id= UUID.randomUUID();
    private String name;
    private String password;
    private String phone;
   private ZoneId zoneId;

    private static int counter=1;

    public User(String name, String password, String phone, ZoneId zoneId) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.zoneId = zoneId;
    }

    public ZoneId getZoneId() {
        return zoneId;
    }

    public void setZoneId(ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    public User(String yangiName, Integer yangiYoshi) {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        User.counter = counter;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
