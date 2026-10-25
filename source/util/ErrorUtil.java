package source.util;

public class ErrorUtil {
    public static void printError(String operation, Exception e)
    {
        System.out.println(operation + " Failed");
        System.out.println( "Reason : " + e.getMessage());
        System.out.println();
    }
}
