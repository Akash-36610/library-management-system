package source.util;

import source.main.Main;

/**
 * Utility class that validates user input before processing.
 * Ensures only valid data is accepted in the Library Management System.
 */
public class Validator
{
    /* ==========================================================
       Book Input Validation Methods
       ========================================================== */

    /**
     * Validates and returns a book ID.
     * Expected format: ABC123
     *
     * @param prompt Message displayed to the user
     * @return Valid book ID
     */
    public static String getBookIdInput(String prompt)
    {
        while (true)
        {
            System.out.print(prompt);

            String s = Main.sc.nextLine().trim();

            if (s.matches("[A-Z]+\\d+"))
            {
                return s;
            }

            System.out.println("Invalid Book Id !!!");
        }
    }

    /**
     * Validates and returns a book title.
     *
     * @param prompt Message displayed to the user
     * @return Valid book title
     */
    public static String getBookTitleInput(String prompt)
    {
        while (true)
        {
            System.out.print(prompt);

            String s = Main.sc.nextLine().trim();

            if (!s.isEmpty() && s.matches("[A-Za-z0-9. ]+"))
            {
                return s;
            }

            System.out.println("Invalid Book Title !!!");
        }
    }

    public static String getBookAuthorInput(String prompt)
    {
        while (true)
        {
            System.out.print(prompt);

            String s = Main.sc.nextLine().trim();

            if (!s.isEmpty() && s.matches("[A-Za-z ]+"))
            {
                return s;
            }

            System.out.println("Invalid Author Name !!!");
        }
    }

    public static String getBookCategoryInput(String prompt)
    {
        while (true)
        {
            System.out.print(prompt);

            String s = Main.sc.nextLine().trim();

            if (!s.isEmpty() && s.matches("[A-Za-z ]+"))
            {
                return s;
            }

            System.out.println("Invalid Category !!!");
        }
    }

    public static int getBookQuantityInput(String prompt)
    {
        while (true)
        {
            System.out.print(prompt);

            try
            {
                int i = Integer.parseInt(Main.sc.nextLine().trim());

                if (i > 0)
                {
                    return i;
                }

                System.out.println("Value Must be Greater than 0");
            }
            catch (Exception e)
            {
                System.out.println("Invalid Book Quantity");
            }
        }
    }

    /**
     * Validates and returns a positive integer value.
     *
     * @param prompt Message displayed to the user
     * @return Positive integer
     */
    public static int getIntInput(String prompt)
    {
        while (true)
        {
            System.out.print(prompt);

            try
            {
                int i = Integer.parseInt(Main.sc.nextLine().trim());

                if (i > 0)
                {
                    return i;
                }

                System.out.println("Value must be greater than 0");
            }
            catch (Exception e)
            {
                System.out.println("Invalid Value");
            }
        }
    }

    /* ==========================================================
       Student Input Validation Methods
       ========================================================== */

    public static String getRegNo(String prompt)
    {
        while (true)
        {
            System.out.print(prompt);

            String s = Main.sc.nextLine().trim();

            if (!s.isEmpty() && s.matches("[A-Z]{2,5}\\d{4}"))
            {
                return s;
            }

            System.out.println("Invalid Reg No");
        }
    }

    public static String getName(String prompt)
    {
        while (true)
        {
            System.out.print(prompt);

            String s = Main.sc.nextLine().trim();

            if (!s.isEmpty() && s.matches("[A-Za-z ]+"))
            {
                return s;
            }

            System.out.println("Invalid Name");
        }
    }

    public static String getDepartment(String prompt)
    {
        while (true)
        {
            System.out.print(prompt);

            String s = Main.sc.nextLine().trim();

            if (!s.isEmpty() &&
                s.matches("(CSE|IT|ECE|EEE|MECH|CIVIL|AIDS|AIML|CSBS|CS|NS)"))
            {
                return s;
            }

            System.out.println("Invalid Department");
        }
    }

    public static int getYear(String prompt)
    {
        while (true)
        {
            System.out.print(prompt);

            String s = Main.sc.nextLine().trim();

            try
            {
                int n = Integer.parseInt(s);

                if (n >= 1 && n <= 4)
                {
                    return n;
                }

                System.out.println("Year must be between 1 and 4");
            }
            catch (NumberFormatException e)
            {
                System.out.println("Invalid Year");
            }
        }
    }

    public static String getPhone(String prompt)
    {
        while (true)
        {
            System.out.print(prompt);

            String s = Main.sc.nextLine().trim();

            if (!s.isEmpty() && s.matches("[6-9]\\d{9}"))
            {
                return s;
            }

            System.out.println("Invalid Phone No");
        }
    }

    public static String getMailId(String prompt)
    {
        while (true)
        {
            System.out.print(prompt);

            String s = Main.sc.nextLine().trim();

            if (!s.isEmpty() &&
                s.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}"))
            {
                return s;
            }

            System.out.println("Invalid Mail Id");
        }
    }
}