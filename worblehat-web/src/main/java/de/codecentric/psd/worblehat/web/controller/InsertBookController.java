package de.codecentric.psd.worblehat.web.controller;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.psd.worblehat.web.formdata.InsertBookFormData;
import java.util.Optional;
import javax.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/** Handles requests for the application home page. */
@Controller
@RequestMapping("/insertBooks")
@RequiredArgsConstructor
public class InsertBookController {

  private static final Logger LOG = LoggerFactory.getLogger(InsertBookController.class);

  @NonNull private final BookService bookService;

  @RequestMapping(method = RequestMethod.GET)
  public void setupForm(ModelMap modelMap) {
    modelMap.put("insertBookFormData", new InsertBookFormData());
  }

  @RequestMapping(method = RequestMethod.POST)
  public String processSubmit(
      @ModelAttribute("insertBookFormData") @Valid InsertBookFormData insertBookFormData,
      BindingResult result) {

    if (result.hasErrors()) {
      return "insertBooks";
    } else {
      Optional<Book> book = bookService.createBook(insertBookFormData.toBookParameter());
      if (book.isPresent()) {
        LOG.info("new book instance is created: " + book.get());
      } else {
        LOG.debug("failed to create new book with: " + insertBookFormData.toString());
      }
      return "redirect:bookList";
    }
  }
}
