package seedu.address.testutil;

import seedu.address.model.WeddingBook;
import seedu.address.model.wedding.Wedding;

/**
 * A utility class to help with building Weddingbook objects.
 * Example usage: <br>
 *     {@code WeddingBook wb = new WeddingBookBuilder().withPerson("John", "Doe").build();}
 */
public class WeddingBookBuilder {

    private WeddingBook weddingBook;

    public WeddingBookBuilder() {
        weddingBook = new WeddingBook();
    }

    public WeddingBookBuilder(WeddingBook weddingBook) {
        this.weddingBook = weddingBook;
    }

    /**
     * Adds a new {@code Wedding} to the {@code WeddingBook} that we are building.
     */
    public WeddingBookBuilder withWedding(Wedding wedding) {
        weddingBook.addWedding(wedding);
        return this;
    }

    public WeddingBook build() {
        return weddingBook;
    }
}
