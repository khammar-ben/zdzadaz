import service.AuthService;
import model.*;

public class TestAuth {
    public static void main(String[] args) throws Exception {
        AuthService auth = new AuthService();
        auth.registerClient(new Client(System.currentTimeMillis(), "test@test.com", "1234"));
        System.out.println(auth.login("test@test.com", "1234") != null ? "LOGIN OK" : "FAIL");
    }
}
