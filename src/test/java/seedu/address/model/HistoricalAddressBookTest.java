package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import org.junit.jupiter.api.Test;

public class HistoricalAddressBookTest {
    private final HistoricalAddressBook historicalAddressBook = new HistoricalAddressBook(new AddressBook());

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), historicalAddressBook.getPersonList());
    }
}
