import library.Book;
import library.Library;
import library.exceptions.BookNotFoundException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TestLibrary {

    @Test
    void testLibraryIsEmpty() {
        Library testLibrary = new Library();

        assertTrue(testLibrary.isEmpty(), "Library should be empty.");
    }

    @Test
    void testLibraryAddBook() {
        Library testLibrary = new Library();

        Book exampleBook = new Book("Квіти Щастя", "Ретом Керт", 248);
        testLibrary.add(exampleBook);

        assertEquals(testLibrary.size(), 1);
    }

    @Test
    void testLibraryFindBookByPage() {
        Library testLibrary = new Library();
        Book exampleBook = new Book("Квіти Щастя", "Ретом Керт", 248);
        testLibrary.add(exampleBook);

        try {
            assertEquals(exampleBook, testLibrary.getBookByPageCount(248));
        } catch (BookNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
