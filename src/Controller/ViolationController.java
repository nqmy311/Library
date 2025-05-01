package Controller;

import DAO.ViolationDAO;
import view.ViolationView;
import model.Violation;
import java.util.List;

public class ViolationController {
    private final ViolationDAO violationDAO;
    private final ViolationView violationView;

    public ViolationController() {
        this.violationDAO = new ViolationDAO();
        this.violationView = new ViolationView(this);
    }

    public void viewViolationsByUserId(int userId) {
        List<Violation> violations = violationDAO.getViolationsByUserId(userId);
        violationView.displayViolations(violations);
    }

    public void viewAllViolations() {
        List<Violation> violations = violationDAO.getAllViolations();
        violationView.displayAllViolationsAdmin(violations);
    }

    public void markViolationAsPaid(int violationId) {
        Violation violation = violationDAO.getViolationById(violationId);
        if (violation != null && !violation.isPaid()) {
            violationDAO.markViolationAsPaid(violationId);
            violationView.displayMessage("Vi phạm số " + violationId + " đã được đánh dấu là đã thanh toán.");
        } else {
            violationView.displayMessage("Không thể đánh dấu vi phạm số " + violationId + " là đã thanh toán hoặc vi phạm không tồn tại/đã được thanh toán.");
        }
    }
}