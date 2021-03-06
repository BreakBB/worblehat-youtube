package de.codecentric.psd.worblehat.acceptancetests.step.business;

import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;

import de.codecentric.psd.worblehat.acceptancetests.step.StoryContext;
import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookParameter;
import de.codecentric.psd.worblehat.domain.BookService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class Library {

  @Autowired(required = true)
  private BookService bookService;

  private StoryContext storyContext;

  @Autowired
  public Library(ApplicationContext applicationContext, StoryContext storyContext) {
    this.bookService = applicationContext.getBean(BookService.class);
    this.storyContext = storyContext;
  }

  // *******************
  // *** G I V E N *****
  // *******************

  @Given("an empty library")
  public void emptyLibrary() {
    bookService.deleteAllBooks();
  }

  @Given("a library, containing only one book with isbn {string}")
  public void createLibraryWithSingleBookWithGivenIsbn(String isbn) {
    emptyLibrary();
    Book book = DemoBookFactory.createDemoBook().withISBN(isbn).build();
    Optional<Book> createdBook =
        bookService.createBook(
            new BookParameter(
                book.getTitle(),
                book.getAuthor(),
                book.getEdition(),
                book.getIsbn(),
                book.getYearOfPublication(),
                book.getDescription()));
    createdBook.ifPresent(b -> storyContext.putObject("LAST_INSERTED_BOOK", b));
  }

  @Given("{string} has borrowed books {string}")
  public void borrower1HasBorrowerdBooks(String borrower, String isbns) {
    borrowerHasBorrowedBooks(borrower, isbns);
  }

  public void borrowerHasBorrowedBooks(String borrower, String isbns) {
    List<String> isbnList = getListOfItems(isbns);
    for (String isbn : isbnList) {
      Book book = DemoBookFactory.createDemoBook().withISBN(isbn).build();
      bookService
          .createBook(
              new BookParameter(
                  book.getTitle(),
                  book.getAuthor(),
                  book.getEdition(),
                  book.getIsbn(),
                  book.getYearOfPublication(),
                  book.getDescription()))
          .orElseThrow(IllegalStateException::new);

      bookService.borrowBook(book.getIsbn(), borrower);
    }
  }

  private List<String> getListOfItems(String isbns) {
    return isbns.isEmpty() ? Collections.emptyList() : Arrays.asList(isbns.split(" "));
  }
  // *****************
  // *** W H E N *****
  // *****************

  // *****************
  // *** T H E N *****
  // *****************

  @Then("the new book {string} be added")
  public void shouldNotHaveCreatedANewCopy(String can) {
    Book lastInsertedBook = (Book) storyContext.getObject("LAST_INSERTED_BOOK");
    int numberOfCopies = "CAN".equals(can) ? 2 : 1;
    assertNumberOfCopies(lastInsertedBook.getIsbn(), numberOfCopies);
  }

  private void assertNumberOfCopies(String isbn, int nrOfCopies) {
    Set<Book> books = bookService.findBooksByIsbn(isbn);
    assertThat(books.size(), is(nrOfCopies));
    assertThat(books, everyItem(hasProperty("isbn", is(isbn))));
  }

}
