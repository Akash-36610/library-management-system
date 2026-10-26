package source.main;


import java.util.Scanner;
import source.dao.BookDao;
import source.dao.IssueRecordDao;
import source.dao.StudentDao;
import source.model.Book;
import source.model.Student;
import source.util.Validator;


/**
 * Main entry point of the Library Management System.
 * Displays menus and handles user interactions.
 */
public class Main
{
    // Scanner object used throughout the application for user input
    public static Scanner sc = new Scanner(System.in);

    /**
     * Displays the main menu options. 
     * */
    private static void printMenu()
    {
            System.out.println("==============================");
            System.out.println();
            System.out.println(" LIBRARY MANAGEMENT SYSTEM");
            System.out.println();
            System.out.println("==============================");
            System.out.println();
            System.out.println("Menu : ");
            System.out.println();
            System.out.println("1.Add Books ");
            System.out.println("2.View All Books");
            System.out.println("3.Search Book");
            System.out.println("4.Delete Book");
            System.out.println("5.Update Book Quantity");
            System.out.println();
            System.out.println("6.Add Student");
            System.out.println("7.View All Students");
            System.out.println("8.Search Student");
            System.out.println("9.Delete Student");
            System.out.println("10.Update Student Phone No");
            System.out.println("11.Update Student Mail Id");
            System.out.println("12.Update Student Year ");
            System.out.println(); 
            System.out.println("13.Issue Book");
            System.out.println("14.Return Book");
            System.out.println("15.View All Record");
            System.out.println("16.View Record by Student");
            System.out.println("17.View Records by Book ");
            System.out.println("18.View Issued Records ");
            System.out.println("19.View Returned Records ");
            System.out.println("20.View Overdue Records");
            System.out.println("21.View Late Returned Books ");
            System.out.println("22.Exit ");
    }

     /**
     * Handles menu navigation and executes
     * the selected library operations.
     */
    private static  void menu()
    {
            
        int choice;

        do{

            printMenu();

             // Read user's menu choice
            choice = Validator.getIntInput("Enter Choice : ");

            switch(choice)
            {
                case 1://add books
                {
                    System.out.println("Enter Book Details ");

                    String id = Validator.getBookIdInput("Enter Book ID : ");
                    String title = Validator.getBookTitleInput("Enter Book Title : ");
                    String author = Validator.getBookAuthorInput("Enter Author Name : ");
                    String category = Validator.getBookCategoryInput("Enter Book Category : ");
                    int quantity = Validator.getBookQuantityInput("Enter Book Quantity :");

                    Book book = new Book(id, title, author, category, quantity);

                    System.out.println();
                    BookDao.addBook(book);

                }
                break;
                case 2://display all books
                    {
                        BookDao.viewAllBooks();
                    }
                    break;

                case 3: //display particular book
                    {
                        String searchId=Validator.getBookIdInput("Enter Book Id to Search :");

                        BookDao.searchBookById(searchId);
                    }
                    break;
                case 4: // delete particular book
                    {
                        String deleteId=Validator.getBookIdInput("Enter Book Id to Delete :");

                        BookDao.deleteBookById(deleteId);
                    }
                    break;

                case 5: //update book count
                    {
                        System.out.println();
                        System.out.println("1.Increase Book Copies");
                        System.out.println("2.Decrease Book Copies");
                        System.out.println("3.Set Overall Count");

                        int option=Validator.getIntInput("Enter option :");

                        switch (option)
                        {
                            case 1:  //add books
                                {
                                    String id=Validator.getBookIdInput("Enter Book Id to Increase Count :");
                                    int increaseCount=Validator.getIntInput("Enter No of Books to increase count : ");
                                    BookDao.increaseBookCount(id, increaseCount);
                                }
                                break;

                            case 2:  //remove books
                                {
                                    String id=Validator.getBookIdInput("Enter Book Id to Decrease Count :");
                                    
                                    int decreaseCount=Validator.getIntInput("Enter No of Books to decrease count : ");

                                    BookDao.decreaseBookCount(id, decreaseCount);
                                }
                                break;

                            case 3:  //set overall quantity
                                {
                                    String id=Validator.getBookIdInput("Enter Book Id to Update Count :");
                                    
                                    int count=Validator.getIntInput("Enter No of Books to Update count : ");

                                    BookDao.setOverallQuantity(id, count);
                                }
                                break;
                        
                            default:
                                System.out.println("Invalid Option");
                                break;
                        }
                    }
                    break;
                case 6://add student
                    {
                        System.out.println("Enter Student Details");

                        String regNo = Validator.getRegNo("Enter Reg No ( DEPT1234 ): ");
                        String name = Validator.getName("Enter Name : ");
                        String department = Validator.getDepartment("Enter Department : ");
                        int  year = Validator.getYear("Enter Year : ");
                        String phone = Validator.getPhone("Enter Phone No : ");
                        String mailId = Validator.getMailId("Enter Mail Id : ");

                        Student newStudent = new Student(regNo, name, department, year, phone, mailId);

                        StudentDao.addStudent(newStudent);
                    }
                    break;

                case 7://view all student
                    {
                        StudentDao.viewAllStudents();
                    }
                    break;
                        
                case 8://search student
                    {
                        String regNo = Validator.getRegNo("Enter Student Reg No to Search : ");

                        StudentDao.searchStudentByRegNo(regNo);
                    }
                    break;
                    
                case 9://delete student
                    {
                        String regNo = Validator.getRegNo("Enter Student Reg No to Delete : ");

                        StudentDao.deleteStudentByRegNo(regNo);
                    }
                    break;

                case 10://update student phone
                    {
                        String regNo = Validator.getRegNo("Enter Reg No : ");
                        String newPhone = Validator.getPhone("Enter new Phone No : ");

                        StudentDao.updateStudentPhoneByRegNo(regNo, newPhone);
                    }
                    break;

                case 11://update student mail Id
                    {
                        String regNo = Validator.getRegNo("Enter Reg No : ");
                        String newMailId = Validator.getMailId("Enter new MailId No : ");

                        StudentDao.updateStudentMailIdByRegNo(regNo, newMailId);
                    }
                    break;

                case 12://update student year
                    {
                        String regNo = Validator.getRegNo("Enter Reg No : ");
                        int newYear = Validator.getYear("Enter new Year No : ");

                        StudentDao.updateStudentYearByRegNo(regNo, newYear);
                    }
                    break;
                case 13://Issue a book
                    {
                        String bookId = Validator.getBookIdInput("Enter the Book id to Issue Book : ");
                        String regNo=Validator.getRegNo("Enter Student Reg No : ");
                        
                        IssueRecordDao.issueBook(regNo, bookId);
                    }
                    break;

                case 14://return book
                    {
                        String bookId = Validator.getBookIdInput("Enter the Book id to Return Book : ");
                        String regNo=Validator.getRegNo("Enter Student Reg No : ");

                        IssueRecordDao.returnBook(regNo, bookId);
                    }
                    break;

                case 15://view all records
                    {
                        IssueRecordDao.viewAllRecords();
                    }
                    break;

                case 16://view records by student
                    {
                        String regNo = Validator.getRegNo("Enter Student Reg No : ");

                        IssueRecordDao.viewRecordsByStudent(regNo);
                    }
                    break;

                case 17://view records by student
                    {
                        String bookId = Validator.getBookIdInput("Enter Book Id : ");

                        IssueRecordDao.viewRecordsByBook(bookId);
                    }
                    break;

                case 18://view Issued records
                    {
                        IssueRecordDao.viewIssuedRecords();
                    }
                    break;

                case 19://view Returned Records
                    {
                        IssueRecordDao.viewReturnedRecords();
                    }
                    break;

                case 20://view Overdued Records
                    {
                        IssueRecordDao.viewOverdueRecords();
                    }
                    break;

                case 21://late returned books
                    {
                        IssueRecordDao.viewLateReturnedBooks();
                    }
                    break;
                case 22://exit application
                    {
                        System.out.println("Thank you for using our Library System. Welcome Again.");
                    }
                    break;
                default://Invalid menu selection
                        {
                            System.out.println("Invalid Choice Selected");
                        }
            }

        }while(choice != 22);
    }
            
     /**
     * Program execution starts here.
     */
    public static void main(String[] args) 
    {
        menu();   
    }
}
