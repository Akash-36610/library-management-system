package source.dao;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import source.util.ConsoleUtil;
import source.util.DBConnection;
import source.model.Book;
import source.model.Student;
import source.model.IssueRecord;
import source.model.IssueStatus;
import source.util.ErrorUtil;

public class IssueRecordDao 
{
    
    public static boolean hasIssueRecordForBookId(String bookId)//helper
    {
        String sql ="SELECT 1 FROM issued_books WHERE book_id=? LIMIT 1";

        try(Connection con = DBConnection.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql))
        {
            pstmt.setString(1, bookId);

            try(ResultSet rs = pstmt.executeQuery())
            {
                return rs.next();
            }
        }
        catch(Exception e)
        {
            ErrorUtil.printError("Check Book Issue Records",e);
        }

        return false;
    }
    public static boolean hasIssueRecordForRegNo(String regNo)//helper
    {
        String sql ="SELECT 1 FROM issued_books WHERE reg_no=? LIMIT 1";

        try(Connection con = DBConnection.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql))
        {
            pstmt.setString(1, regNo);

            try(ResultSet rs = pstmt.executeQuery())
            {
                return rs.next();
            }
        }
        catch(Exception e)
        {
           ErrorUtil.printError("Check Student Issue Records", e);
        }

        return false; 
    }
    private static void syncBookQuantityToDB(Connection connect,Book newBook,int n) throws SQLException//helper
    {
        String sql = "UPDATE books SET quantity = quantity + ? WHERE book_id=?;";

        try( PreparedStatement  pstmt = connect.prepareStatement(sql); )
        {
            pstmt.setInt(1, n);
            pstmt.setString(2, newBook.getBookId());

            int rows = pstmt.executeUpdate();

            if( rows<1 )
            {
                throw new SQLException("Book Count Not updated");
            }
        }
         
    }
    private static boolean isAlreadyIssued(String regNo,String bookId)//helper
    {
        String sql = "SELECT 1 FROM issued_books WHERE reg_no = ? AND book_id = ? AND status =? ;";

        try(Connection connect = DBConnection.getConnection(); PreparedStatement  pstmt = connect.prepareStatement(sql); )
        {
            pstmt.setString(1, regNo);
            pstmt.setString(2, bookId);
            pstmt.setString(3, IssueStatus.ISSUED.name());

            try(ResultSet rs=pstmt.executeQuery())
            {
                return rs.next();
            }
        }
        catch(SQLException e)
        {
           ErrorUtil.printError("Check Already Issued", e);
        }
        
        return false;
    }
    public static void issueBook(String regNo,String bookId)
    {
        if(StudentDao.getStudentByRegNo(regNo)==null)
        {
            System.out.println("No Student found with the Reg No : "+regNo);
            return ;
        }

        Book newBook =BookDao.getBookById(bookId);

        if(newBook == null )
        {
            System.out.println("No Book found with the Book Id : "+bookId);
            return ;
        }

        if(newBook.getQuantity() <= 0)
        {
            System.out.println("No Copies Available");
            return ;
        }

        if(isAlreadyIssued(regNo, bookId))
        {
            System.out.println("Book Already Issued to the Student");
            return ;
        }
        
       LocalDate issueDate = LocalDate.now();
       LocalTime issueTime = LocalTime.now().withNano(0);
       LocalDate dueDate = issueDate.plusDays(7);

       IssueRecord newRecord = new IssueRecord(regNo, bookId, issueDate, issueTime, dueDate);

       String sql = "INSERT INTO issued_books (reg_no,book_id,issue_date,issue_time,due_date,status) VALUES (?,?,?,?,?,?) ;";

       Connection connect = null;

       try
       {
            connect = DBConnection.getConnection();
            connect.setAutoCommit(false);
       
            try( PreparedStatement  pstmt = connect.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);)
            {
                
                pstmt.setString(1,regNo);
                pstmt.setString(2, bookId);
                pstmt.setDate(3, java.sql.Date.valueOf(issueDate));
                pstmt.setTime(4, java.sql.Time.valueOf(issueTime));
                pstmt.setDate(5, java.sql.Date.valueOf(dueDate));
                pstmt.setString(6, newRecord.getStatus().name());

                int n = pstmt.executeUpdate();

                if(n==0) 
                {
                    throw new SQLException("Issue Book Failed");
                }

                try(ResultSet rs=pstmt.getGeneratedKeys())
                {
                    if(rs.next())
                    {
                        int issueId = rs.getInt(1);
                        newRecord.setIssueId(issueId);
                    }
                }    

                syncBookQuantityToDB(connect,newBook,-1);

                connect.commit();

                System.out.println("Book Issued Succesfully");
                System.out.println("Issue ID : "+newRecord.getIssueId());

            }
        }
        catch (Exception e)
        {
            try 
            {
                if(connect != null)
                {
                    connect.rollback();
                }
            } 
            catch (Exception ex)
            {
                ErrorUtil.printError("Connection Rollback", ex);
            }

            ErrorUtil.printError("Issue Book", e);
        }
        finally
        {
            try
            {
                if(connect != null)
                {
                    connect.setAutoCommit(true);
                    connect.close();
                }
            }
            catch(Exception e)
            {
                ErrorUtil.printError("Connection Cleanup", e);
            }
        }
    }
    private static IssueRecord getIssuedRecord(String regNo,String bookId,IssueStatus status)
    {
        String sql = "SELECT * FROM issued_books WHERE reg_no = ? and book_id = ? and status = ? ;";

        try (Connection connect = DBConnection.getConnection(); PreparedStatement  pstmt = connect.prepareStatement(sql);)
        {
            pstmt.setString(1, regNo);
            pstmt.setString(2,bookId);
            pstmt.setString(3,status.name());

            try(ResultSet rs = pstmt.executeQuery())
            {
                if(rs.next()) 
                {
                    return new IssueRecord(rs.getInt("issue_id"), rs.getString("reg_no"), rs.getString("book_id"), rs.getDate("issue_date").toLocalDate(), rs.getTime("issue_time").toLocalTime(), rs.getDate("due_date").toLocalDate(),IssueStatus.valueOf(rs.getString("status")));
                }
            }
        } 
        catch (Exception e) 
        {
           ErrorUtil.printError("Get Issue Record", e);
        }

        return null;
    }
    public static void returnBook(String regNo,String bookId)
    {
        IssueRecord newRecord = getIssuedRecord(regNo, bookId, IssueStatus.ISSUED);

        if(newRecord == null)
        {
            System.out.println("No Record found to return book");
            return;
        }

        LocalDate returnDate=LocalDate.now();
        LocalTime returnTime=LocalTime.now().withNano(0);

        newRecord.setReturnDate(returnDate);
        newRecord.setReturnTime(returnTime);
        newRecord.setStatus(IssueStatus.RETURNED);

        String sql = "UPDATE issued_books SET return_date =? ,return_time = ?,status = ? WHERE issue_id = ?;";

        Connection connect = null;

        try
        {
            connect = DBConnection.getConnection();
            connect.setAutoCommit(false);
           
            try( PreparedStatement  pstmt = connect.prepareStatement(sql))
            {
                pstmt.setDate(1, java.sql.Date.valueOf(returnDate));
                pstmt.setTime(2, java.sql.Time.valueOf(returnTime));
                pstmt.setString(3,IssueStatus.RETURNED.name());
                pstmt.setInt(4, newRecord.getIssueId());

                int n= pstmt.executeUpdate();

                if(n == 0)
                {
                    throw new Exception("Book Return Failed");
                }

                Book newBook = BookDao.getBookById(bookId);
              
                syncBookQuantityToDB(connect,newBook,1);

                connect.commit();

                System.out.println("Book Returned Successfully");
          
         
            }
        }
        catch(Exception e)
        {
            try
            {
                if(connect != null)
                {
                    connect.rollback();
                }
            }
            catch(Exception ex)
            {
               ErrorUtil.printError("Connection Rollback", ex);
            }

            ErrorUtil.printError("Return Book", e);
        }
        finally
        {
            try
            {
                if(connect != null)
                {
                    connect.setAutoCommit(true);
                    connect.close();
                }
            }
            catch(Exception e)
            {
              ErrorUtil.printError("Connection Cleanup", e);
            }
        }
    }
    public static void viewAllRecords()
    {
        String sql ="SELECT * FROM issued_books ;";

        try (Connection connect = DBConnection.getConnection(); PreparedStatement  pstmt = connect.prepareStatement(sql);)
        {
            viewRecords(pstmt); 
        } 
        catch (Exception e) 
        {
            ErrorUtil.printError("View All Records", e);
        }
       
    }
    public static void viewRecords(PreparedStatement pstmt)//helper
    {
        try (ResultSet rs=pstmt.executeQuery()) 
        {
            if(!rs.next())
            {
                System.out.println("No Record Available");
                return;
            }
            
            ConsoleUtil.printRecordHeader();
            
            int cnt=0;

            do{
                cnt++;
                System.out.printf("%-10d %-12s %-12s %-12s %-12s %-12s %-12s %-12s %-10s%n", rs.getInt("issue_id"),rs.getString("reg_no"),rs.getString("book_id"),rs.getDate("issue_date"),rs.getTime("issue_time"),rs.getDate("due_date"),rs.getDate("return_date")==null? "-" : rs.getDate("return_date"),rs.getTime("return_time") == null ? "-" : rs.getTime("return_time"),rs.getString("status"));
                ConsoleUtil.printSeparatorLine(110);
            }while(rs.next());

            ConsoleUtil.printHeadLine(110);
            System.out.println("Total Record : "+cnt);
            
        } 
        catch (Exception e) 
        {
            ErrorUtil.printError("View Records", e);
        }
    }
    public static void viewRecordsByStudent(String regNo)
    {
        Student newStudent = StudentDao.getStudentByRegNo(regNo);

        if(newStudent == null)
        {
            System.out.println("No Student Found with Reg No : "+regNo);
            return;
        }

        String sql = "SELECT * FROM issued_books WHERE reg_no = ? ;";
        
        try(Connection connect = DBConnection.getConnection() ;PreparedStatement pstmt = connect.prepareStatement(sql))
        {
            pstmt.setString(1, regNo);

            viewRecords(pstmt);
        } 
        catch (Exception e) 
        {
           ErrorUtil.printError("View Student Records", e);
        }
    }
    public static void viewRecordsByBook(String bookId)
    {
        Book newBook=BookDao.getBookById(bookId);

        if( newBook == null)
        {
            System.out.println("No Book Found with the Book Id : "+bookId);
            return;
        }

        String sql = "SELECT * FROM issued_books WHERE book_id = ? ;";

        try(Connection connect = DBConnection.getConnection() ;PreparedStatement pstmt = connect.prepareStatement(sql)) 
        {
            pstmt.setString(1, bookId);

            viewRecords(pstmt);
        }
        catch (Exception e) 
        {
          ErrorUtil.printError("View Book Records", e);
        }
    }
    public static void viewReturnedRecords()
    {
        String sql = "SELECT * FROM issued_books WHERE status  = ? ;";
        
        try (Connection connect = DBConnection.getConnection() ;PreparedStatement pstmt = connect.prepareStatement(sql)) 
        {
            pstmt.setString(1,IssueStatus.RETURNED.name());

            viewRecords(pstmt);
        }
        catch (Exception e) 
        {
           ErrorUtil.printError("View Returned Books", e);
        }
    }
    public static void viewIssuedRecords()
    {     
        String sql = "SELECT * FROM issued_books WHERE status  = ? ;";

        try(Connection connect = DBConnection.getConnection() ;PreparedStatement pstmt = connect.prepareStatement(sql)) 
        {
            pstmt.setString(1,IssueStatus.ISSUED.name());

            viewRecords(pstmt);
        } 
        catch (Exception e) 
        {
          ErrorUtil.printError("View Issued Books", e);
        }
    }
    public static void viewOverdueRecords()
    {
        LocalDate currentDate = LocalDate.now();

        String sql = "SELECT * FROM issued_books WHERE due_date < ? and status = ? ;";

        try (Connection connect = DBConnection.getConnection() ;PreparedStatement pstmt = connect.prepareStatement(sql)) 
        {
            pstmt.setDate(1,Date.valueOf(currentDate));
            pstmt.setString(2,IssueStatus.ISSUED.name());

            viewRecords(pstmt);
        } 
        catch (Exception e) 
        {
          ErrorUtil.printError("View Overdue Books", e);
        }
    }
    public static void viewLateReturnedBooks()
    {
        String sql = "SELECT * FROM issued_books WHERE return_date > due_date ;" ;

        try(Connection connect = DBConnection.getConnection() ; PreparedStatement pstmt = connect.prepareStatement(sql)) 
        {
            viewRecords(pstmt);
        } 
        catch (Exception e) 
        {
            ErrorUtil.printError("View Late Returned Books", e);
        }
    }


}