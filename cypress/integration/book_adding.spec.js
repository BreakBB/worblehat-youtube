describe('Add book', () => {
    beforeEach(() => {
        cy.visit("/insertBooks")
    });

    it('should show header', () => {
        cy.get("h1").should("have.text", "Add Book")
    });

    it('should add a book', () => {
        cy.fixture('test_book_monte_christo.json').then((book) => {
            cy.get("[name=title]").type(book.title)
            cy.get("[name=edition]").type(book.edition)
            cy.get("[name=isbn]").type(book.isbn)
            cy.get("[name=author]").type(book.author)
            cy.get("[name=yearOfPublication]").type(book.year)
            cy.get("[name=description]").type(book.description)

            cy.get("[id=addBook]").click()
        });
    });
});
