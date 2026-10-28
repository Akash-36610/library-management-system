
package source.model;

import java.time.LocalDate;
import java.time.LocalTime;

// Model class that represents a book issue record in the library system.
public class IssueRecord
{
    // Stores the unique ID of the issue record.
    private int issueId;

    // Stores the registration number of the student who borrowed the book.
    // final means this value cannot be reassigned after initialization.
    private final String regNo;

    // Stores the ID of the borrowed book.
    private final String bookId;

    // Stores the date on which the book was issued.
    private final LocalDate issueDate;

    // Stores the time at which the book was issued.
    private final LocalTime issueTime;

    // Stores the deadline for returning the book.
    private final LocalDate dueDate;

    // Stores the actual date on which the book was returned.
    // Remains null until the book is returned.
    private LocalDate returnDate;

    // Stores the actual time at which the book was returned.
    // Remains null until the book is returned.
    private LocalTime returnTime;

    // Stores the current status of the issue record.
    // Example: ISSUED, RETURNED.
    private IssueStatus status;


    // Constructor for creating a new issue record.
    // The issue ID is not provided here.
    // The status is initialized to ISSUED by default.
    public IssueRecord(String regNo, String bookId,
                       LocalDate issueDate, LocalTime issueTime,
                       LocalDate dueDate)
    {
        // Assigns the provided values to the object's fields.
        this.regNo = regNo;
        this.bookId = bookId;
        this.issueDate = issueDate;
        this.issueTime = issueTime;
        this.dueDate = dueDate;

        // Sets the initial status when a new book is issued.
        this.status = IssueStatus.ISSUED;
    }


    // Constructor for creating an issue record with existing data.
    // Useful when retrieving an existing record from the database.
    public IssueRecord(int issueId, String regNo, String bookId,
                       LocalDate issueDate, LocalTime issueTime,
                       LocalDate dueDate, IssueStatus status)
    {
        // Initializes all the fields using the provided values.
        this.issueId = issueId;
        this.regNo = regNo;
        this.bookId = bookId;
        this.issueDate = issueDate;
        this.issueTime = issueTime;
        this.dueDate = dueDate;
        this.status = status;
    }


    // Setters: methods used to update fields that can change.

    // Sets the issue record ID.
    public void setIssueId(int issueId)
    {
        this.issueId = issueId;
    }

    // Sets the date on which the book was returned.
    public void setReturnDate(LocalDate returnDate)
    {
        this.returnDate = returnDate;
    }

    // Sets the time at which the book was returned.
    public void setReturnTime(LocalTime returnTime)
    {
        this.returnTime = returnTime;
    }

    // Updates the current status of the issue record.
    public void setStatus(IssueStatus status)
    {
        this.status = status;
    }


    // Getters: methods used to retrieve field values.

    // Returns the unique issue record ID.
    public int getIssueId()
    {
        return issueId;
    }

    // Returns the registration number of the student.
    public String getRegNo()
    {
        return regNo;
    }

    // Returns the ID of the borrowed book.
    public String getBookId()
    {
        return bookId;
    }

    // Returns the date on which the book was issued.
    public LocalDate getIssueDate()
    {
        return issueDate;
    }

    // Returns the time at which the book was issued.
    public LocalTime getIssueTime()
    {
        return issueTime;
    }

    // Returns the book's due date.
    public LocalDate getDueDate()
    {
        return dueDate;
    }

    // Returns the actual return date, or null if not yet returned.
    public LocalDate getReturnDate()
    {
        return returnDate;
    }

    // Returns the actual return time, or null if not yet returned.
    public LocalTime getReturnTime()
    {
        return returnTime;
    }

    // Returns the current issue status.
    public IssueStatus getStatus()
    {
        return status;
    }
}
