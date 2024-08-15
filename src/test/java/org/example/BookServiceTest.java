package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
public class BookServiceTest {
    private Book book1, book2, book3;
    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        book1 = new Book("The Great Gatsby", "F. Scott Fitzgerald", "Classic", 10.99);
        book2 = new Book("Moby Dick", "Herman Melville", "Adventure", 8.99);
        book3 = new Book("1984", "George Orwell", "Dystopian", 12.99);

        bookService.addBook(book1);
        bookService.addBook(book2);
        bookService.addBook(book3);
    }

    @AfterEach
    void tearDown() {
        bookService.removeBook(new Book("The Great Gatsby", "F. Scott Fitzgerald", "Classic", 10.99));
        bookService.removeBook(new Book("Moby Dick", "Herman Melville", "Adventure", 8.99));
        bookService.removeBook(new Book("1984", "George Orwell", "Dystopian", 12.99));
    }

    @Test
    void testSearchBook_Successful_Title() {
        List<Book> searchResult = bookService.searchBook("The Great Gatsby");
        assertEquals(1, searchResult.size());
        assertEquals("The Great Gatsby", searchResult.get(0).getTitle());
    }

    @Test
    void testSearchBook_Successful_Author() {
        List<Book> searchResult = bookService.searchBook("George Orwell");
        assertEquals(1, searchResult.size());
        assertEquals("George Orwell", searchResult.get(0).getAuthor());
    }

    @Test
    void testSearchBook_Successful_Genre() {
        List<Book> searchResult = bookService.searchBook("Adventure");
        assertEquals(1, searchResult.size());
        assertEquals("Adventure", searchResult.get(0).getGenre());
    }

    @Test
    void testSearchBook_NoMatch() {
        List<Book> searchResult = bookService.searchBook("non existent");
        assertEquals(0, searchResult.size());
    }

    @Test
    void testPurchaseBook_Successful() {
        User user = new User("john_doe", "password123", "john@doe.com");
        assertTrue(bookService.purchaseBook(user, book1));
    }

    @Test
    void testPurchaseBook_Failure() {
        User user = new User("john", "password123", "john@doe.com");
        Book notBook = new Book("not a book", "author", "genre", 10.99);
        assertFalse(bookService.purchaseBook(user, notBook));
    }

    @Test
    void testPurchaseBook_Null() {
        assertFalse(bookService.purchaseBook(null, null));

    }

}
