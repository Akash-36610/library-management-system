package source.util;

public class ConsoleUtil {
    
    public static void printHeadLine(int n)
    {
        System.out.println("=".repeat(n));
    }
    public static void printSeparatorLine(int n)
    {
        System.out.println("-".repeat(n));
    }
    public static void printBookHeader()
    {   
        printHeadLine(100);
        System.out.printf("%-12s %-30s %-25s %-20s %-10s%n","BOOK ID","TITLE","AUTHOR","CATEGORY","QUANTITY");
        printHeadLine(100);
        System.out.println();
    }
    public static void printStudentHeader()
    {
        printHeadLine(110);
        System.out.printf("%-12s %-25s %-15s %-8s %-15s %-30s%n", "REG NO","NAME","DEPARTMENT","YEAR","PHONE","MAIL ID");
        printHeadLine(110);
        System.out.println();
    }
    public static void printRecordHeader()
    {
        printHeadLine(110);
        System.out.printf("%-10s %-12s %-12s %-12s %-12s %-12s %-12s %-12s %-10s%n", "Issue ID","Reg No","Book ID","Issue Date","Issue Time","Due Date","Return Date","Return Time","Status");
        printHeadLine(110);
        System.out.println();
    }
   
}
