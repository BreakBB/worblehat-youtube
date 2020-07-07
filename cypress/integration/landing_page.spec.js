describe('Landingpage', () => {
    beforeEach(() => {
        cy.visit("/")
    })

    it('should show the header', () => {
        cy.get('h1 > span').should('have.text', 'Worblehat Bookmanager')
    });

    it('should show version number', () => {
        cy.get('footer > div').should('have.text', 'Version: 1.3.1-SNAPSHOT')
    });

    it('should navigate to all books', () => {
        cy.get('a[href="/bookList"]').click()
        cy.location("pathname").should("eq", "/bookList")
    });

    it('should navigate to add a book', () => {
        cy.get('a[href="/insertBooks"]').click()
        cy.location("pathname").should("eq", "/insertBooks")
    });

    it('should navigate to borrow a book', () => {
        cy.get('a[href="/borrow"]').click()
        cy.location("pathname").should("eq", "/borrow")
    });

    it('should navigate to return all books', () => {
        cy.get('a[href="/returnAllBooks"]').click()
        cy.location("pathname").should("eq", "/returnAllBooks")
    });
})
