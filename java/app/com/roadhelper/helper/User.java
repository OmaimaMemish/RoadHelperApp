package app.com.roadhelper.helper;

/**
 * Created by Luminance on 2/27/2018.
 */

public class User {
    private String user_id , name , user_type ,phone , password , email;

    public User(String user_id, String name, String user_type, String phone, String password, String email) {
        this.user_id = user_id;
        this.name = name;
        this.user_type = user_type;
        this.phone = phone;
        this.password = password;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
