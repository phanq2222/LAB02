package USER;

import java.io.IOException;

public class User {
    public static void main(String[] args) {
        try {
            new UserLoginView().render();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
