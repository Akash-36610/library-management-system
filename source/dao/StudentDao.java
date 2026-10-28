package source.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import source.util.ConsoleUtil;
import source.util.DBConnection;
import source.util.ErrorUtil;
import source.model.Student;

/**
 * Data Access Object (DAO) responsible for managing
 * all student-related database operations.
 *
 * This class handles:
 * - Student registration
 * - Student searching
 * - Student deletion
 * - Student information updates
 * - Student record viewing
 */
public class StudentDao
{   

    /* ==========================================================
   Student Management Operations
   ----------------------------------------------------------
   Handles student creation, retrieval,
   searching, and deletion operations.
   ========================================================== */

    public static void addStudent(Student student)
    {
        String sql = "INSERT INTO students (reg_no,name,department,year,phone,mail) VALUES (?,?,?,?,?,?);";

        try(Connection   connect = DBConnection.getConnection(); PreparedStatement  pstmt=connect.prepareStatement(sql); )
        {
            int i=1;

            pstmt.setString(i++,student.getRegNo());
            pstmt.setString(i++,student.getName());
            pstmt.setString(i++,student.getDepartment());
            pstmt.setInt(i++,student.getYear());
            pstmt.setString(i++,student.getPhone());
            pstmt.setString(i++,student.getMailId());

            int n = pstmt.executeUpdate();

            if( n > 0) 
                System.out.println("Student Added Successfully");
            else 
                System.out.println("Student Addition Failed");
        }
        catch(Exception e)
        {
            ErrorUtil.printError("Add Student", e);
        }
    }
    public static void viewAllStudents()
    {
        String  sql = "SELECT * FROM students ;";

        try(Connection connect = DBConnection.getConnection(); PreparedStatement  pstmt=connect.prepareStatement(sql);)
        {
           viewStudents(pstmt); 
        }
        catch(SQLException e)
        {
            ErrorUtil.printError("View Student", e);
        }
    }
    public static void searchStudentByRegNo(String regNo)
    {
        String sql = "SELECT * FROM students WHERE reg_no=? ;";

        try(Connection   connect = DBConnection.getConnection(); PreparedStatement  pstmt=connect.prepareStatement(sql);)
        {
            pstmt.setString(1, regNo);

            viewStudents(pstmt);
        }
        catch(SQLException e)
        {
           ErrorUtil.printError("Search Student", e);
        }
    }
    public  static void deleteStudentByRegNo(String regNo)
    {
        if(getStudentByRegNo(regNo)==null)
        {
            System.out.println("Student Not Found");
            return;
        }

        if(IssueRecordDao.hasIssueRecordForRegNo(regNo))
        {
            System.out.println( "Student Cannot Be Deleted Because Related Issue Records Exist");
            return;
        }

        String sql = "DELETE FROM students WHERE reg_no = ? ;";

        try(Connection   connect = DBConnection.getConnection(); PreparedStatement  pstmt=connect.prepareStatement(sql);)
        {
            pstmt.setString(1, regNo);

            int n = pstmt.executeUpdate();

            if(n>0) 
                System.out.println("Student Deleted Successfully");
            else 
                System.out.println("No Student Found With Reg No : "+regNo );
            
        } 
        catch (Exception e) 
        {
           ErrorUtil.printError("Delete Student", e);
        }

    }







    /* ==========================================================
   Student Update Operations
   ----------------------------------------------------------
   Handles updates to student information
   such as phone number, email address,
   and academic year.
   ========================================================== */

    public static void updateStudentPhoneByRegNo(String regNo,String newPhone)
    {
        Student newStudent = getStudentByRegNo(regNo);

        if(newStudent == null ) 
        {   System.out.println("Student Not Found");
            return;
        }

        newStudent.setPhone(newPhone);
         
        String sql ="UPDATE students SET phone = ? WHERE reg_no=? ;";

        try(Connection   connect = DBConnection.getConnection(); PreparedStatement  pstmt=connect.prepareStatement(sql);) 
        {
            pstmt.setString(1, newPhone);
            pstmt.setString(2, newStudent.getRegNo());

            int n = pstmt.executeUpdate();

            if( n>0) 
                System.out.println("Phone No updated Successfully");
            else 
                System.out.println("Updation Failed");
        } 
        catch (Exception e) 
        {
           ErrorUtil.printError("Update Phone", e);
        }
       
    }
    public static void updateStudentMailIdByRegNo(String regNo,String newMailId)
    {
        Student newStudent = getStudentByRegNo(regNo);

        if(newStudent == null) 
        {
            System.out.println("Student Not Found");
            return;
        }

        newStudent.setMailId(newMailId);

        String sql ="UPDATE students SET mail = ? WHERE reg_no=? ;";

        try(Connection   connect = DBConnection.getConnection(); PreparedStatement  pstmt=connect.prepareStatement(sql);) 
        {
            
            pstmt.setString(1, newStudent.getMailId());
            pstmt.setString(2, newStudent.getRegNo());

            int n = pstmt.executeUpdate();

            if( n>0) 
                System.out.println("Mail Id updated Successfully");
            else 
                System.out.println("Updation Failed");
        } 
        catch (Exception e) 
        {
           ErrorUtil.printError("Update Mail Id", e);
        }
        
    }
    public static void updateStudentYearByRegNo(String regNo,int newYear)
    {
        Student newStudent = getStudentByRegNo(regNo);

        if(newStudent == null ) 
        {
            System.out.println("Student Not Found");
            return;
        }

        newStudent.setYear(newYear);

        
        String sql ="UPDATE students SET year = ? WHERE reg_no=? ;";

        try (Connection   connect = DBConnection.getConnection(); PreparedStatement  pstmt=connect.prepareStatement(sql); )
        {
            pstmt.setInt(1, newStudent.getYear());
            pstmt.setString(2, newStudent.getRegNo());

            int n = pstmt.executeUpdate();

            if( n>0) 
                System.out.println("Year updated Successfully");
            else 
                System.out.println("Updation Failed");
        } 
        catch (Exception e) 
        {
          ErrorUtil.printError("Update Year", e);
        }
       


    }







    /* ==========================================================
   Helper Methods
   ----------------------------------------------------------
   Internal utility methods used for
   student retrieval and formatted
   record display.
   ========================================================== */


   /* ----------------------------------------------------------
   Display Helpers
   ----------------------------------------------------------
   Responsible for formatting and displaying
   student records in a tabular format.
   ---------------------------------------------------------- */

    public static void viewStudents(PreparedStatement pstmt)
    {
        try(ResultSet rs=pstmt.executeQuery()) 
        {
            if(!rs.next())
                {
                    System.out.println("No Students Available");
                    return;
                }  
                
            ConsoleUtil.printStudentHeader();

            int cnt=0;

             do{
                cnt++;
                int i=1;
                System.out.printf("%-12s %-25s %-15s %-8d %-15s %-30s%n",rs.getString(i++),rs.getString(i++),rs.getString(i++),rs.getInt(i++),rs.getString(i++),rs.getString(i++));
                ConsoleUtil.printSeparatorLine(110);
            }while(rs.next());

        
            ConsoleUtil.printHeadLine(110);
            System.out.println();
            System.out.println("Total Students : "+cnt);
        } 
        catch (Exception e) 
        {
           ErrorUtil.printError("View Students", e);
        }
    }
    

    /* ----------------------------------------------------------
   Record Lookup Helpers
   ----------------------------------------------------------
   Retrieves student information from the
   database using registration numbers.
   ---------------------------------------------------------- */
   
   public static Student getStudentByRegNo(String regNo)
    {
        String sql = "SELECT * FROM students WHERE reg_no = ? ;";

        try(Connection   connect = DBConnection.getConnection(); PreparedStatement  pstmt=connect.prepareStatement(sql);) 
        {
            pstmt.setString(1, regNo);

            try(ResultSet rs=pstmt.executeQuery())
            {

                if(rs.next())
                {   
                    int i=1;
                    Student newStudent = new Student(rs.getString(i++),rs.getString(i++),rs.getString(i++), rs.getInt(i++), rs.getString(i++), rs.getString(i++));

                    return newStudent;
                }
            }

        } 
        catch (Exception e) 
        {
           ErrorUtil.printError("Get Student", e);
        }
        
        return null;
    }
    
}