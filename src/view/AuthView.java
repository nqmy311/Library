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

    private String checkNameInput() {
        while (true) {
            System.out.print("Họ tên: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Không được để trống!");
                continue;
            }
            boolean valid = true;
            for (char c : input.toCharArray()) {
                if (!Character.isLetter(c) && c != ' ') {
                    valid = false;
                    break;
                }
            }
            if (!valid) {
                System.out.println("Tên chỉ được chứa chữ cái và khoảng trắng!");
                continue;
            }
            return input;
        }
    }

    private String checkPhoneInput() {
        while (true) {
            System.out.print("Số điện thoại: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Không được để trống!");
                continue;
            }
            if (!input.matches("\\d+")) {
                System.out.println("Số điện thoại chỉ được chứa số!");
                continue;
            }
            if (input.length() != 10) {
                System.out.println("Số điện thoại phải có đúng 10 chữ số!");
                continue;
            }
            return input;
        }
    }

    private String checkUsernameInput() {
        while (true) {
            System.out.print("Username: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Không được để trống!");
            } else if (input.length() <= 6) {
                System.out.println("Username phải lớn hơn 6 ký tự!");
            } else {
                return input;
            }
        }
    }

    private String checkPasswordInput() {
        while (true) {
            System.out.print("Password: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Không được để trống!");
            } else if (input.length() <= 6) {
                System.out.println("Password phải lớn hơn 6 ký tự!");
            } else {
                return input;
            }
        }
    }

    private String checkEmailInput() {
        while (true) {
            System.out.print("Email: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Không được để trống!");
                continue;
            }
            String regex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
            if (!input.matches(regex)) {
                System.out.println("Email không đúng định dạng!");
                continue;
            }
            return input;
        }
    }

    private void register() {
        User user = new User();
        user.setUsername(checkUsernameInput());
        user.setPassword(checkPasswordInput());
        user.setName(checkNameInput());
        user.setEmail(checkEmailInput());
        user.setPhone(checkPhoneInput());
        user.setAddress(checkInput("Địa chỉ: "));
        boolean success = controller.register(user);
        if (success) {
            System.out.println("Đăng ký thành công!");
        } else {
            System.out.println("Thất bại. Username hoặc Email có thể đã tồn tại.");
        }
    }

    private void login() {
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        User user = controller.login(username, password);
        if (user != null) {
            System.out.println("Xin chào " + user.getName() + ", bạn đã đăng nhập thành công!");
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
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
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
            } catch (Exception e) {
                System.out.println("Đã xảy ra lỗi: " + e.getMessage());
            }
        }
    }
}
