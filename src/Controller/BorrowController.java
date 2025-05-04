package Controller;
import DAO.*;
import model.BorrowRequest;
import model.BorrowRecord;
import view.BorrowView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import model.Violation;
import DAO.BookDAO;
import model.Book;
import java.util.Calendar;

public class BorrowController {
    private final BorrowRequestDAO borrowRequestDAO;
    private final BorrowRecordDAO borrowRecordDAO;
    private final BorrowView borrowView;
    private final BookDAO bookDAO;
    private final ViolationDAO violationDAO;
    public BorrowController(){
        this.borrowRequestDAO = new BorrowRequestDAO();
        this.borrowRecordDAO = new BorrowRecordDAO();
        this.borrowView = new BorrowView(this);
        this.bookDAO = new BookDAO();
        this.violationDAO = new ViolationDAO();
    }
    public void requestBorrowBook(BorrowRequest request) {
        boolean success = borrowRequestDAO.createBorrowRequest(request);
        if (success) {
            borrowView.displayMessage("Gửi yêu cầu mượn sách thành công!");
        } else {
            borrowView.displayMessage("Gửi yêu cầu mượn sách thất bại!");
        }
    }
    public void viewBorrowRequests(int userId){
        ArrayList<BorrowRequest> requests = borrowRequestDAO.getBorrowRequestByUserId(userId);
        borrowView.displayBorrowRequests(requests);
    }

    public void viewBorrowRecords(int userId){
        ArrayList<BorrowRecord> records = borrowRecordDAO.getBorrowRecordsByUserId(userId);
        borrowView.displayBorrowRecords(records);
    }

    public void viewAllBorrowRequests(){
        ArrayList<BorrowRequest> requests = borrowRequestDAO.BorrowRequests();
        borrowView.displayAllBorrowRequests(requests);
    }

    public void viewAllBorrowRecords(){
        ArrayList<BorrowRecord> records = borrowRecordDAO.getAllBorrowRecords();
        borrowView.displayAllBorrowRecords(records);
    }

    public void approveBorrowRequest(int requestId, Date borrowDate) {
        BorrowRequest request = borrowRequestDAO.getBorrowRequestById(requestId);
        if (request != null && request.getStatus().equals("Đang chờ duyệt")) {
            if (borrowDate.before(request.getRequestDate())) {
                borrowView.displayMessage("Lỗi: Ngày mượn không được trước ngày yêu cầu mượn.");
                return;
            }
            Book book = bookDAO.findBookByID(request.getBookId());
            if (book == null || book.getAvailable() <= 0) {
                borrowView.displayMessage("Lỗi: Sách không có sẵn để cho mượn.");
                return;
            }
            if (borrowRequestDAO.updateBorrowRequestStatus(requestId, "Đã được duyệt")) {
                BorrowRecord record = new BorrowRecord(request.getUserId(), request.getBookId(), borrowDate, calculateDueDate(borrowDate));
                if (borrowRecordDAO.createBorrowRecord(record)) {
                    bookDAO.decreaseBookAvailable(request.getBookId());
                    borrowView.displayMessage("Yêu cầu mượn số " + requestId + " đã được duyệt. Phiếu mượn đã được tạo.");
                } else {
                    borrowView.displayMessage("Lỗi: Không thể tạo phiếu mượn.");
                }
            } else {
                borrowView.displayMessage("Lỗi: Không thể cập nhật trạng thái yêu cầu mượn.");
            }
        } else {
            borrowView.displayMessage("Không thể duyệt yêu cầu mượn số " + requestId + " hoặc yêu cầu không ở trạng thái chờ duyệt.");
        }
    }

    private Date calculateDueDate(Date borrowDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(borrowDate);
        calendar.add(Calendar.DAY_OF_YEAR, 14);
        return calendar.getTime();
    }

    public void rejectBorrowRequest(int requestId){
        BorrowRequest request = borrowRequestDAO.getBorrowRequestById(requestId);
        if(request != null && request.getStatus().equals("Đang chờ duyệt")){
            if(borrowRequestDAO.updateBorrowRequestStatus(requestId, "Bị từ chối")){
                borrowView.displayMessage("Yêu cầu mượn số " + requestId + " đã bị từ chối");
            } else{
                borrowView.displayMessage("Lỗi: Không thể cập nhật trạng thái yêu cầu mượn.");
            }
        } else {
            borrowView.displayMessage("Không thể từ chối yêu cầu mượn số " + requestId + " (có thể do không tồn tại hoặc yêu cầu không ở trạng thái chờ duyệt).");
        }
    }

    public void viewAllReturnRequests(){
        ArrayList<BorrowRecord> requests = new ArrayList<>();
        ArrayList<BorrowRecord> records = borrowRecordDAO.getAllBorrowRecords();
        for(BorrowRecord record : records){
            if(record.getStatus().equals("Yêu cầu trả")){
                requests.add(record);
            }
        }
        borrowView.displayReturnRequests(requests);
    }

    public void requestReturnBook(int recordId, int userId) {
        BorrowRecord record = borrowRecordDAO.getBorrowRecordById(recordId);
        if (record != null && record.getStatus().equals("Đang được mượn")) {
            if (record.getUserId() == userId) {
                if (borrowRecordDAO.updateBorrowRecordStatus(recordId, "Yêu cầu trả")) {
                    borrowView.displayMessage("Đã gửi yêu cầu trả sách cho phiếu mượn số " + recordId + ". Vui lòng chờ xác nhận từ admin.");
                } else {
                    borrowView.displayMessage("Lỗi: Không thể cập nhật trạng thái phiếu mượn.");
                }
            } else {
                borrowView.displayMessage("Bạn không có quyền gửi yêu cầu trả sách cho phiếu mượn này.");
            }
        } else {
            borrowView.displayMessage("Không thể gửi yêu cầu trả sách cho phiếu mượn số " + recordId + " hoặc phiếu mượn không ở trạng thái 'Đang được mượn'.");
        }
    }

    public void markBookAsReturned(int recordId, Date returnDate) {
        BorrowRecord record = borrowRecordDAO.getBorrowRecordById(recordId);
        if (record == null || !record.getStatus().equals("Yêu cầu trả")) {
            borrowView.displayMessage("Không thể đánh dấu đã trả cho phiếu mượn số " + recordId + " (có thể do không tồn tại hoặc không ở trạng thái 'Yêu cầu trả').");
            return;
        }
        if (returnDate.before(record.getBorrowDate())) {
            borrowView.displayMessage("Lỗi: Ngày trả sách không được trước ngày mượn.");
            return;
        }
        record.setReturnDate(returnDate);
        String newStatus = calculateReturnStatus(record);
        record.setStatus(newStatus);
        if (!borrowRecordDAO.updateReturnDate(recordId, returnDate)) {
            borrowView.displayMessage("Cập nhât ngày trả sách thất bại!");
            return;
        }
        if (!borrowRecordDAO.updateBorrowRecordStatus(recordId, newStatus)) {
            borrowView.displayMessage("Cập nhật trạng thái trả thất bại!");
            return;
        }
        bookDAO.increaseBookAvailable(record.getBookId());
        borrowView.displayMessage("Cập nhật trạng thái trả thành công!");
        double totalFineAmount = 0;
        StringBuilder violationReason = new StringBuilder();
        if (newStatus.equals("Trả muộn")) {
            long overdueDays = handleLateReturn(record);
            double lateReturnFine = overdueDays * 10000;
            totalFineAmount += lateReturnFine;
            violationReason.append("Trả muộn ");
            violationReason.append(overdueDays);
            violationReason.append(" ngày");
        }
        String bookCondition = borrowView.getBookConditionInput();
        if (!borrowRecordDAO.updateBorrowRecordBookCondition(recordId, bookCondition)) {
            borrowView.displayMessage("Cập nhật trạng thái sách thất bại!");
            return;
        }
        if (bookCondition.equals("Bị hỏng") || bookCondition.equals("Bị mất")) {
            double damageFine = handleDamagedOrLostBook(record, bookCondition);
            totalFineAmount += damageFine;
            if (!violationReason.isEmpty()) violationReason.append(", ");
            if (bookCondition.equals("Bị hỏng")) {
                violationReason.append("Sách bị hỏng");
            } else {
                violationReason.append("Sách bị mất");
            }
        }
        if (totalFineAmount > 0) {
            Violation violation = new Violation(record.getUserId(), record.getRecord_id(), returnDate, violationReason.toString(), totalFineAmount);
            if (violationDAO.createViolation(violation)) {
                borrowView.displayMessage("Đã ghi nhận vi phạm: " + violationReason + ". Tiền phạt: " + String.format("%.2f", totalFineAmount) + " đồng.");
            } else {
                borrowView.displayMessage("Không thể ghi nhận vi phạm.");
            }
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String formattedDate = sdf.format(returnDate);
            borrowView.displayMessage("Phiếu mượn số " + recordId + " đã được đánh dấu là đã trả vào ngày: " + formattedDate + ".");
        }
    }


    private long handleLateReturn(BorrowRecord record) {
        long overDueMs = record.getReturnDate().getTime() - record.getDueDate().getTime();
        long overDueDays = overDueMs / (24 * 60 * 60 * 1000);
        return Math.max(overDueDays, 0);
    }

    private double handleDamagedOrLostBook(BorrowRecord record, String condition) {
        Book book = bookDAO.findBookByID(record.getBookId());
        if (book != null) {
            double fineAmount = book.getPenalty_rate();
            if (condition.equals("Bị mất")) {
                bookDAO.decreaseBookAvailable(record.getBookId());
                bookDAO.decreaseBookQuantity(record.getBookId());
                return fineAmount;
            }
            else{
                bookDAO.decreaseBookAvailable(record.getBookId());
                return fineAmount / 2;
            }
        }
        return 0;
    }

    private String calculateReturnStatus(BorrowRecord record){
        if (record.getReturnDate() != null && record.getDueDate() != null && record.getReturnDate().after(record.getDueDate())) {
            return "Trả muộn";
        }
        return "Đã trả";
    }
}
