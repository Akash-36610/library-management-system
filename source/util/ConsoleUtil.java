package source.util;

/**
 * Utility class that provides formatted console output methods.
 * Used to display headers and separator lines for various reports
 * in the Library Management System.
 */
public class ConsoleUtil
{
    /**
     * Prints a headline using '=' characters.
     *
     * @param n Number of characters to print
     */
    public static void printHeadLine(int n)
    {
        System.out.println("=".repeat(n));
    }

    /**
     * Prints a separator line using '-' characters.
     *
     * @param n Number of characters to print
     */
    public static void printSeparatorLine(int n)
    {
        System.out.println("-".repeat(n));
    }

    /**
     * Displays the header format for book records.
     * Used when listing books in a tabular format.
     */
    public static void printBookHeader()
    {
        printHeadLine(100);

        System.out.printf(
            "%-12s %-30s %-25s %-20s %-10s%n",
            "BOOK ID",
            "TITLE",
            "AUTHOR",
            "CATEGORY",
            "QUANTITY"
        );

        printHeadLine(100);
        System.out.println();
    }

    /**
     * Displays the header format for student records.
     * Used when listing student details.
     */
    public static void printStudentHeader()
    {
        printHeadLine(110);

        System.out.printf(
            "%-12s %-25s %-15s %-8s %-15s %-30s%n",
            "REG NO",
            "NAME",
            "DEPARTMENT",
            "YEAR",
            "PHONE",
            "MAIL ID"
        );

        printHeadLine(110);
        System.out.println();
    }

    /**
     * Displays the header format for issue and return records.
     * Used when showing library transaction history.
     */
    public static void printRecordHeader()
    {
        printHeadLine(110);

        System.out.printf(
            "%-10s %-12s %-12s %-12s %-12s %-12s %-12s %-12s %-10s%n",
            "Issue ID",
            "Reg No",
            "Book ID",
            "Issue Date",
            "Issue Time",
            "Due Date",
            "Return Date",
            "Return Time",
            "Status"
        );

        printHeadLine(110);
        System.out.println();
    }
}