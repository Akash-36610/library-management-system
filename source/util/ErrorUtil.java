package source.util;

/**
 * Utility class for displaying error messages in a
 * consistent format throughout the application.
 */
public class ErrorUtil
{
    /**
     * Displays details about a failed operation.
     *
     * @param operation Name of the operation that failed
     * @param e Exception containing error details
     */
    public static void printError(String operation, Exception e)
    {
        System.out.println("[ERROR] " + operation + " Failed");
        System.out.println("Reason : " + e.getMessage());
        System.out.println();
    }
}