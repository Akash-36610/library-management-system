package source.model;

public class Student 
{
    private final String regNo;
    private final String name;
    private final String department;
    private int year;
    private String phone;
    private String mailId;

    //constructor
    
    public Student(String regNo, String name, String department, int year, String phone, String mailId)
    {
        this.regNo = regNo;
        this.name = name;
        this.department = department;
        this.year = year;
        this.phone = phone;
        this.mailId = mailId;
    }

    //getters

    public String getRegNo() 
    {
        return regNo; 
    }
    public String getName() 
    {
        return name;
    }
    public String getDepartment()
    {
        return department;
    }
    public int getYear() 
    {
        return year;
    }
    public String getPhone() 
    {
        return phone;
    }
    public String getMailId() 
    {
        return mailId;
    }

    //setters
    public void setYear(int year) 
    {
        this.year = year;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }
    public void setMailId(String mailId) 
    {
        this.mailId = mailId;
    }


  
}
