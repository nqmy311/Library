package view;

import Controller.AuthController;
import model.User;
import java.util.Scanner;

public class AuthView {
    private final AuthController controller = new AuthController();
    private final Scanner scanner = new Scanner(System.in);
    private final AdminView adminView = new AdminView();
    private final UserView userView = new UserView();

    private String checkInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Không được để trống!");
                continue;
            }
            return input;
        }
    }

    private void register() {
        User user = new User();
        user.setUsername(checkInput("Username: "));
        user.setPassword(checkInput("Password: "));
        user.setName(checkInput("Họ tên: "));
        user.setEmail(checkInput("Email: "));
        user.setPhone(checkInput("Số điện thoại: "));
        user.setAddress(checkInput("Địa chỉ: "));
        boolean success = controller.register(user);
        if (success) {
            System.out.println("Đăng ký thành công!");
        } else {
            System.out.println("Thất bại. Username có thể đã tồn tại.");
        }
    }

    private void login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        User user = controller.login(username, password);
        if (user != null) {
            System.out.println("Chào " + user.getName() + ", bạn đã đăng nhập thành công!");
            if (user.getRole().equals("admin")) {
                adminView.showMenu(user);
            } else {
                userView.showMenu(user);
            }
        } else {
            System.out.println("Sai thông tin đăng nhập.");
        }
    }

    public void showMenu() {
        while (true) {
            System.out.println("==== ĐĂNG NHẬP HỆ THỐNG ====");
            System.out.println("1. Đăng ký");
            System.out.println("2. Đăng nhập");
            System.out.println("0. Thoát");
            System.out.println("============================");
            System.out.print("Chọn chức năng: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
}
