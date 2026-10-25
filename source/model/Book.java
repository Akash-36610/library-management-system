package source.model;

public class Book 
{
    //book attributes
    private final String bookId;
    private final String title;
    private final String author;
    private final String category;
    private int quantity;

    //Constructor -> initialize attribute values
    public Book(String bookId,String title,String author,String category,int quantity) 
    {
        this.bookId=bookId;
        this.title=title;
        this.author=author;
        this.category=category;
        this.quantity=quantity;
    }


    public String getBookId()
    {
        return bookId;
    }
    public String getTitle() 
    {
        return title;
    }
    public String getAuthor() 
    {
        return author;
    }
    public String getCategory() 
    {
        return category;
    }
    public int getQuantity() 
    {
        return quantity;
    }

    //setters

    public void setQuantity(int quantity) 
    {
        this.quantity =  quantity;
    }
    public void increaseQuantity(int quantity) 
    {
        this.quantity = this.quantity + quantity;
    }
    public void decreaseQuantity(int quantity) 
    {
        this.quantity = this.quantity - quantity;
    }

}
