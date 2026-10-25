package source.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class IssueRecord 
{
    private int issueId;
    private final String regNo;
    private final String bookId;
    private final LocalDate issueDate;
    private final LocalTime issueTime;
    private final LocalDate dueDate;
    private LocalDate returnDate;
    private LocalTime returnTime;
    private  IssueStatus status;

    //Constructor for initialize issue
    public IssueRecord(String regNo, String bookId, LocalDate issueDate, LocalTime issueTime, LocalDate dueDate) 
    {
        this.regNo = regNo;
        this.bookId = bookId;
        this.issueDate = issueDate;
        this.issueTime = issueTime;
        this.dueDate = dueDate;

        this.status = IssueStatus.ISSUED;//defaultly set status = ISSEUED when anew record is created
    }

    public IssueRecord(int issueId, String regNo, String bookId, LocalDate issueDate, LocalTime issueTime,LocalDate dueDate, IssueStatus status) 
    {
        this.issueId = issueId;
        this.regNo = regNo;
        this.bookId = bookId;
        this.issueDate = issueDate;
        this.issueTime = issueTime;
        this.dueDate = dueDate;
        this.status = status;
    }

    //setters

    public void setIssueId(int issueId) 
    {
        this.issueId = issueId;
    }
    public void setReturnDate(LocalDate returnDate) 
    {
        this.returnDate = returnDate;
    }
    public void setReturnTime(LocalTime returnTime) 
    {
        this.returnTime = returnTime;
    }
    public void setStatus(IssueStatus status) 
    {
        this.status = status;
    }

    //getters
    
    public int getIssueId() 
    {
        return issueId;
    }
    public String getRegNo() 
    {
        return regNo;
    }
    public String getBookId() 
    {
        return bookId;
    }
    public LocalDate getIssueDate() 
    {
        return issueDate;
    }
    public LocalTime getIssueTime() 
    {
        return issueTime;
    }
    public LocalDate getDueDate() {
        return dueDate;
    }
    public LocalDate getReturnDate() 
    {
        return returnDate;
    }
    public LocalTime getReturnTime() 
    {
        return returnTime;
    }
    public IssueStatus getStatus() 
    {
        return status;
    }
    
    




}
