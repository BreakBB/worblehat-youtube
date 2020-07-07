describe('Book list', () => {
    beforeEach(() => {
        cy.visit("/bookList")
    })

    it('should show all table header', () => {
        cy.fixture('book_list_headers.json').then((headers) => {
            for (const header of headers.headers) {
                cy.get('.bookList > thead > tr > th').contains(header)
            }
        });
    });

    it('should navigate back to home', () => {
        cy.get('a[href="/"]').click()
        cy.location("pathname").should("eq", "/")
    });

    it('should show version number', () => {
        cy.get('footer > div').should('have.text', 'Version: 1.3.1-SNAPSHOT')
    });
});
