package source.model;

/**
 * Represents a student in the Library Management System.
 * Stores student details required for library operations.
 */
public class Student
{
    // Unique registration number of the student
    private final String regNo;

    // Student's full name
    private final String name;

    // Department of the student
    private final String department;

    // Current academic year
    private int year;

    // Contact number
    private String phone;

    // Email address
    private String mailId;

    /**
     * Constructs a Student object with the given details.
     *
     * @param regNo Student registration number
     * @param name Student name
     * @param department Department name
     * @param year Academic year
     * @param phone Contact number
     * @param mailId Email address
     */
    public Student(String regNo,String name,String department,int year,String phone,String mailId)
    {
        this.regNo = regNo;
        this.name = name;
        this.department = department;
        this.year = year;
        this.phone = phone;
        this.mailId = mailId;
    }

    /**
     * Returns the student's registration number.
     *
     * @return Registration number
     */
    public String getRegNo()
    {
        return regNo;
    }

    /**
     * Returns the student's name.
     *
     * @return Student name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the student's department.
     *
     * @return Department name
     */
    public String getDepartment()
    {
        return department;
    }

    /**
     * Returns the student's academic year.
     *
     * @return Academic year
     */
    public int getYear()
    {
        return year;
    }

    /**
     * Returns the student's phone number.
     *
     * @return Contact number
     */
    public String getPhone()
    {
        return phone;
    }

    /**
     * Returns the student's email address.
     *
     * @return Email address
     */
    public String getMailId()
    {
        return mailId;
    }

    /**
     * Updates the student's academic year.
     *
     * @param year New academic year
     */
    public void setYear(int year)
    {
        this.year = year;
    }

    /**
     * Updates the student's phone number.
     *
     * @param phone New contact number
     */
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    /**
     * Updates the student's email address.
     *
     * @param mailId New email address
     */
    public void setMailId(String mailId)
    {
        this.mailId = mailId;
    }
}