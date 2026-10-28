package source.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import source.model.Book;
import source.util.ConsoleUtil;
import source.util.DBConnection;
import source.util.ErrorUtil;

/**
 * Data Access Object (DAO) responsible for handling
 * all book-related database operations.
 *
 * Features:
 * - Add books
 * - View books
 * - Search books
 * - Delete books
 * - Update book quantity
 */
public class BookDao
{

    /* ==========================================================
   Book Management Operations
   ----------------------------------------------------------
   Handles book creation, retrieval, searching,
   and deletion operations.
   ========================================================== */

    public static void addBook(Book book)
    {
        String sql =
            "INSERT INTO Books(book_id,title,author,category,quantity) VALUES (?,?,?,?,?);";

        try (
            Connection connect = DBConnection.getConnection();
            PreparedStatement pstmt = connect.prepareStatement(sql);
        )
        {
            int i = 1;

            pstmt.setString(i++, book.getBookId());
            pstmt.setString(i++, book.getTitle());
            pstmt.setString(i++, book.getAuthor());
            pstmt.setString(i++, book.getCategory());
            pstmt.setInt(i++, book.getQuantity());

            int n = pstmt.executeUpdate();

            if (n > 0)
            {
                System.out.println("Book Added Successfully");
            }
            else
            {
                System.out.println("Book Adding Failed");
            }
        }
        catch (SQLException e)
        {
            ErrorUtil.printError("Add Books", e);
        }
    }
    public static void viewAllBooks()
    {
        String sql = "SELECT * FROM books;";

        try (
            Connection connect = DBConnection.getConnection();
            PreparedStatement pstmt = connect.prepareStatement(sql);
        )
        {
            viewBooks(pstmt);
        }
        catch (SQLException e)
        {
            ErrorUtil.printError("View All Books", e);
        }
    }
    public static void searchBookById(String searchId)
    {
        String sql = "SELECT * FROM books WHERE book_id=?;";

        try (
            Connection connect = DBConnection.getConnection();
            PreparedStatement pstmt = connect.prepareStatement(sql);
        )
        {
            pstmt.setString(1, searchId);

            viewBooks(pstmt);
        }
        catch (Exception e)
        {
            ErrorUtil.printError("Search Book", e);
        }
    }
    public static void deleteBookById(String deleteId)
    {
        if (getBookById(deleteId) == null)
        {
            System.out.println("No Book Found");
            return;
        }

        if (IssueRecordDao.hasIssueRecordForBookId(deleteId))
        {
            System.out.println(
                "Book Cannot Be Deleted Because Related Issue Records Exist"
            );
            return;
        }

        String sql = "DELETE FROM books WHERE book_id=?;";

        try (
            Connection connect = DBConnection.getConnection();
            PreparedStatement pstmt = connect.prepareStatement(sql);
        )
        {
            pstmt.setString(1, deleteId);

            int n = pstmt.executeUpdate();

            if (n > 0)
            {
                System.out.println("Book Deleted Successfully");
            }
            else
            {
                System.out.println("Book Deletion Failed");
            }
        }
        catch (Exception e)
        {
            ErrorUtil.printError("Delete Book", e);
        }
    }







    /* ==========================================================
   Book Quantity Operations
   ----------------------------------------------------------
   Manages book inventory by increasing,
   decreasing, or directly updating quantities.
   ========================================================== */

    public static void increaseBookCount(String bookId, int bookCount)
    {
        Book book = getBookById(bookId);

        if (book == null)
        {
            System.out.println("Book Not Found");
            return;
        }

        book.increaseQuantity(bookCount);

        updateBookCount(book);
    }
    public static void decreaseBookCount(String bookId, int bookCount)
    {
        Book book = getBookById(bookId);

        if (book == null)
        {
            System.out.println("Book Not Found");
            return;
        }
        else if (bookCount > book.getQuantity())
        {
            System.out.println(
                "Decreasing count should be less than or equal to current quantity"
            );
            return;
        }

        book.decreaseQuantity(bookCount);

        updateBookCount(book);
    }
    public static void setOverallQuantity(String bookId, int bookCount)
    {
        Book book = getBookById(bookId);

        if (book == null)
        {
            System.out.println("Book Not Found");
            return;
        }

        book.setQuantity(bookCount);

        updateBookCount(book);
    }







    /* ==========================================================
   Helper Methods
   ----------------------------------------------------------
   Internal utility methods used for
   book retrieval, quantity updates,
   and formatted data display.
   ========================================================== */






   /* ----------------------------------------------------------
   Display Helpers
   ----------------------------------------------------------
   Responsible for formatting and displaying
   book records in a tabular format.
   ---------------------------------------------------------- */
    public static void viewBooks(PreparedStatement pstmt)
    {
        try (ResultSet rs = pstmt.executeQuery())
        {
            if (!rs.next())
            {
                System.out.println("No Book Available");
                return;
            }

            ConsoleUtil.printBookHeader();

            int cnt = 0;

            do
            {
                cnt++;

                int i = 1;

                System.out.printf(
                    "%-12s %-30s %-25s %-20s %-10s%n",
                    rs.getString(i++),
                    rs.getString(i++),
                    rs.getString(i++),
                    rs.getString(i++),
                    rs.getInt(i++)
                );

                ConsoleUtil.printSeparatorLine(100);

            } while (rs.next());

            ConsoleUtil.printHeadLine(100);

            System.out.println();
            System.out.println("Total Books : " + cnt);
        }
        catch (Exception e)
        {
            ErrorUtil.printError("View Books", e);
        }
    }


    /* ----------------------------------------------------------
   Record Lookup Helpers
   ----------------------------------------------------------
   Retrieves book information from the database
   using unique book identifiers.
   ---------------------------------------------------------- */
    public static Book getBookById(String bookId)
    {
        String sql = "SELECT * FROM books WHERE book_id=?;";

        try (
            Connection connect = DBConnection.getConnection();
            PreparedStatement pstmt = connect.prepareStatement(sql);
        )
        {
            pstmt.setString(1, bookId);

            try (ResultSet rs = pstmt.executeQuery())
            {
                if (rs.next())
                {
                    return new Book(
                        rs.getString("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getInt("quantity")
                    );
                }
            }
        }
        catch (Exception e)
        {
            ErrorUtil.printError("Get Book", e);
        }

        return null;
    }


    /* ----------------------------------------------------------
   Database Synchronization Helpers
   ----------------------------------------------------------
   Updates book quantity information and
   keeps inventory data consistent.
   ---------------------------------------------------------- */
    private static void updateBookCount(Book book)
    {
        String sql =
            "UPDATE books SET quantity=? WHERE book_id=?;";

        try (
            Connection connect = DBConnection.getConnection();
            PreparedStatement pstmt = connect.prepareStatement(sql);
        )
        {
            pstmt.setInt(1, book.getQuantity());
            pstmt.setString(2, book.getBookId());

            int n = pstmt.executeUpdate();

            if (n > 0)
            {
                System.out.println("Book Count Updated Successfully");
            }
            else
            {
                System.out.println("Book Count Updation Failed");
            }
        }
        catch (Exception e)
        {
            ErrorUtil.printError("Update Book Count", e);
        }
    }

}