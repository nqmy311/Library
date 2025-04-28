package Controller;

import DAO.AccountDAO;
import model.User;
import java.util.ArrayList;

public class AccountController {
    private final AccountDAO accountDAO = new AccountDAO();

    public ArrayList<User> listUser() {
        return accountDAO.listUser();
    }

    public boolean addUser(User user) {
        return accountDAO.addUser(user);
    }

    public User findUser(int user_id) {
        return accountDAO.findUser(user_id);
    }
}
