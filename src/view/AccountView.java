package view;

import java.util.*;

import Controller.AccountController;
import model.TablePrinter;
import model.User;

public class AccountView {
    private final AccountController controller = new AccountController();
    private final Scanner scanner = new Scanner(System.in);

    public void showAccountMenu() {
        while (true) {
            System.out.println("==== QUẢN LÝ TÀI KHOẢN ====");
            System.out.println("1. Xem thông tin tài khoản");
            System.out.println("2. Thêm mới tài khoản");
            System.out.println("3. Tìm kiếm tài khoản");
            System.out.println("0. Thoát");
            System.out.println("===========================");
            System.out.print("Chọn chức năng: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1:
                        listUsers();
                        break;
                    case 2:
                        addUser();
                        break;
                    case 3:
                        findUser();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Lựa chọn không hợp lệ! Hãy chọn lại.");
                        break;
                }
            } catch (Exception e) {
                // e.printStackTrace();
                System.out.println("Đã xảy ra lỗi!");
            }
        }
    }

    private String checkStrInput(String prompt) {
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

    private int checkIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Không được để trống!");
                continue;
            }
            try {
                return Integer.parseInt(input);
            } catch (Exception e) {
                System.out.println("Phải nhập một số nguyên hợp lệ!");
            }
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

    private void addUser() {
        User user = new User();
        System.out.println("Nhập thông tin tài khoản muốn thêm:");
        user.setUsername(checkUsernameInput());
        user.setPassword(checkPasswordInput());
        user.setName(checkNameInput());
        user.setEmail(checkEmailInput());
        user.setPhone(checkPhoneInput());
        user.setAddress(checkStrInput("Địa chỉ: "));
        boolean success = controller.addUser(user);
        if (success) {
            System.out.println("Thêm mới tài khoản thành công!");
        } else {
            System.out.println("Thêm mới tài khoản thất bại!");
        }
    }

    private void listUsers() {
        ArrayList<User> list = controller.listUser();
        System.out.printf("%50s\n", " DANH SÁCH TÀI KHOẢN NGƯỜI DÙNG ");
        String[] headers = {"ID", "Tên đăng nhập", "Tên chủ tài khoản", "Email", "Số điện thoại", "Địa chỉ", "Vai trò"};
        ArrayList<Object[]> rows = new ArrayList<>();
        for (User user : list) {
            rows.add(new Object[]{user.getUser_Id(), user.getUsername(), user.getName(), user.getEmail(), user.getPhone(), user.getAddress(), user.getRole()});
        }
        TablePrinter.printTable(headers, rows);
    }

    private void findUser() {
        int id = checkIntInput("Nhập ID tài khoản cần tìm (Nếu muốn thoát vui lòng nhập số 0): ");
        User user = controller.findUser(id);
        if (user == null) {
            System.out.println("Không thể tìm thấy tài khoản!");
            return;
        }
        System.out.println("======== THÔNG TIN TÀI KHOẢN ========");
        System.out.println("ID tài khoản:           " + user.getUser_Id());
        System.out.println("Tên đăng nhập:          " + user.getUsername());
        System.out.println("Tên chủ tài khoản:      " + user.getName());
        System.out.println("Email:                  " + user.getEmail());
        System.out.println("Số điện thoại:          " + user.getPhone());
        System.out.println("Địa chỉ:                " + user.getAddress());
        System.out.println("======================================");
    }
}
