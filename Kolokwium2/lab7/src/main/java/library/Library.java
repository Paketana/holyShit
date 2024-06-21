package library;

import library.exceptions.BookNotFoundException;

import java.util.ArrayList;

public class Library extends ArrayList<Book> {

    public Book getBookByPageCount(int pageCount) throws BookNotFoundException {
        if (pageCount <= 0) {
            throw new BookNotFoundException("Page number should be greater than 0");
        }
        return this.stream()
                .filter(book -> book.pagesAmount() == pageCount)
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("No book with the specified page count."));
    }
}
