package source.model;

/**
 * Represents a book in the Library Management System.
 * Stores all details related to a book such as its
 * unique ID, title, author, category, and quantity.
 */
public class Book
{
    // Unique identifier assigned to the book
    private final String bookId;

    // Basic book information
    private final String title;
    private final String author;
    private final String category;

    // Number of available copies of the book
    private int quantity;

    /**
     * Constructor used to initialize a Book object
     * with all required details.
     */
    public Book(String bookId,String title,String author,String category,int quantity)
    {
        this.bookId=bookId;
        this.title=title;
        this.author=author;
        this.category=category;
        this.quantity=quantity;
    }

    // Returns the unique book ID
    public String getBookId()
    {
        return bookId;
    }

    // Returns the title of the book
    public String getTitle()
    {
        return title;
    }

    // Returns the author name
    public String getAuthor()
    {
        return author;
    }

    // Returns the category/genre of the book
    public String getCategory()
    {
        return category;
    }

    // Returns the current available quantity
    public int getQuantity()
    {
        return quantity;
    }

    /**
     * Updates the quantity of the book.
     */
    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    /**
     * Increases the available quantity when
     * new copies are added to the library.
     */
    public void increaseQuantity(int quantity)
    {
        this.quantity = this.quantity + quantity;
    }

    /**
     * Decreases the available quantity when
     * books are borrowed or removed.
     */
    public void decreaseQuantity(int quantity)
    {
        this.quantity = this.quantity - quantity;
    }

}