package Controller;
import DAO.BorrowRequestDAO;
import DAO.BorrowRecordDAO;
import model.BorrowRequest;
import model.BorrowRecord;
import view.BorrowView;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class BorrowController {
    private final BorrowRequestDAO borrowRequestDAO = new BorrowRequestDAO();
    private final BorrowRecordDAO borrowRecordDAO = new BorrowRecordDAO();
    private final BorrowView borrowView = new BorrowView();
    public boolean requestBorrowBook(BorrowRequest request) {
        return borrowRequestDAO.createBorrowRequest(request);
    }
    public ArrayList<BorrowRequest> BorrowRequests(){
        return borrowRequestDAO.BorrowRequests();
    }
    public BorrowRequest getBorrowRequestById(int requestId){
        return borrowRequestDAO.getBorrowRequestById(requestId);
    }
    public ArrayList<BorrowRequest> getBorrowRequestByUserId(int userId){
        return borrowRequestDAO.getBorrowRequestByUserId(userId);
    }
    public boolean updateBorrowRequestStatus(int requestId, String status){
        return borrowRequestDAO.updateBorrowRequestStatus(requestId, status);
    }
    public void approveBorrowRequest(int requestId, Date borrowDate) {
        BorrowRequest request = borrowRequestDAO.getBorrowRequestById(requestId);
        if (request != null && request.getStatus().equals("Đang chờ duyệt")) {
            if (borrowRequestDAO.updateBorrowRequestStatus(requestId, "Đã được duyệt")) {
                if (borrowDate != null) {
                    BorrowRecord record = new BorrowRecord(request.getUserId(), request.getBookId(), borrowDate, calculateDueDate(borrowDate));
                    if (borrowRecordDAO.createBorrowRecord(record)) {
                        borrowView.displayMessage("Yêu cầu mượn số " + requestId + " đã được duyệt. Phiếu mượn đã được tạo.");
                    } else {
                        borrowView.displayMessage("Lỗi: Không thể tạo phiếu mượn.");
                    }
                } else {
                    borrowView.displayMessage("Ngày mượn không hợp lệ.");
                }
            } else {
                borrowView.displayMessage("Lỗi: Không thể cập nhật trạng thái yêu cầu mượn.");
            }
        } else {
            borrowView.displayMessage("Không thể duyệt yêu cầu mượn số " + requestId + " hoặc yêu cầu không ở trạng thái chờ duyệt.");
        }
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
            borrowView.displayMessage("Không thể từ chối yêu cầu mượn số" + requestId + " hoặc yêu cầu không ở trạng thái chờ duyệt.");
        }
    }

    public ArrayList<BorrowRecord> ListReturnRequests(){
        ArrayList<BorrowRecord> requests = new ArrayList<>();
        ArrayList<BorrowRecord> records = borrowRecordDAO.getAllBorrowRecords();
        for(BorrowRecord record : records){
            if(record.getStatus().equals("Yêu cầu trả")){
                requests.add(record);
            }
        }
        return requests;
    }

    public void requestReturnBook(int recordId){
        BorrowRecord record = borrowRecordDAO.getBorrowRecordById(recordId);
        if(record != null && record.getStatus().equals("Đang được mượn")){
            if(borrowRecordDAO.updateBorrowRecordStatus(recordId, "Yêu cầu trả")){
                borrowView.displayMessage("Đã gửi yêu cầu trả sách cho phiếu mượn số " + recordId + ". Vui lòng chờ xác nhận từ admin.");
            } else {
                borrowView.displayMessage("Lỗi: Không thể cập nhật trạng thái phiếu mượn.");
            }
        } else {
            borrowView.displayMessage("Không thể gửi yêu cầu trả sách cho phiếu mượn số " + recordId + " hoặc phiếu mượn không ở trạng thái đang được mượn.");
        }
    }

    public void markBookAsReturned(int recordId, Date returnDate){
        BorrowRecord record = borrowRecordDAO.getBorrowRecordById(recordId);
        if(record != null && record.getStatus().equals("Yêu cầu trả")){
            record.setStatus(calculateReturnStatus(recordId));
            record.setReturnDate(returnDate);
            if(borrowRecordDAO.updateBorrowRecordStatus(recordId, record.getStatus())){
                borrowView.displayMessage("Phiếu mượn số " + recordId + " đã được đánh dấu là đã trả.");
            } else{
                borrowView.displayMessage("Lỗi: Không thể cập nhật trạng thái phiếu mượn.");
            }
        }
        else{
            borrowView.displayMessage("Không thể đánh dấu đã trả cho phiếu mượn số " + recordId + " hoặc phiếu mượn không ở trạng thái yêu cầu trả.");
        }
    }

    private Date calculateDueDate(Date borrowDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(borrowDate);
        calendar.add(Calendar.DAY_OF_YEAR, 14);
        return calendar.getTime();
    }

    private String calculateReturnStatus(BorrowRecord record){
        if (record.getReturnDate() != null && record.getDueDate() != null && record.getReturnDate().after(record.getDueDate())) {
            return "Trả muộn";
        }
        return "Đã trả";
    }
}
