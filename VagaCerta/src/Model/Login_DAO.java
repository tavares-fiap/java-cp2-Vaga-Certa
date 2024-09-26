package Model;

public class Login_DAO {

    public static boolean login(String username, String password) {
        return username.equals("admin") && password.equals("admin");
    }
}
