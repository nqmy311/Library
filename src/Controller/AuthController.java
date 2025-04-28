package Controller;

import DAO.UserDAO;
import model.User;

public class AuthController {
    private final UserDAO userDAO = new UserDAO();

    public boolean register(User user) {
        return userDAO.register(user);
    }

    public User login(String username, String password) {
        return userDAO.login(username, password);
    }
}
