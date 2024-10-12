package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.client.Buyer;
import seedu.address.model.client.Client;
import seedu.address.model.client.Email;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;
import seedu.address.model.client.Seller;

class MessagesTest {

    @Test
    void getErrorMessageForDuplicatePrefixes_singlePrefix_success() {
        // Arrange
        Prefix prefixPhone = new Prefix("p/");

        // Act
        String result = Messages.getErrorMessageForDuplicatePrefixes(prefixPhone);

        // Assert
        assertEquals(Messages.MESSAGE_DUPLICATE_FIELDS + "p/", result);
    }

    @Test
    void getErrorMessageForDuplicatePrefixes_multiplePrefixes_success() {
        // Arrange
        Prefix prefixPhone = new Prefix("p/");
        Prefix prefixEmail = new Prefix("e/");

        // Act
        String result = Messages.getErrorMessageForDuplicatePrefixes(prefixPhone, prefixEmail);

        // Assert
        assertEquals(Messages.MESSAGE_DUPLICATE_FIELDS + "e/ p/", result);
    }

    @Test
    void formatBuyer_success() {
        // Arrange
        Client buyer = new Buyer(new Name("Alice Bee"), new Phone("98765432"), new Email("alice@example.com"));

        // Act
        String result = Messages.format(buyer);

        // Assert
        String expected = "BUYER; Alice Bee; Phone: 98765432; Email: alice@example.com";
        assertEquals(expected, result);
    }

    @Test
    void formatSeller_success() {
        // Arrange
        Client seller = new Seller(new Name("Bob Bee"), new Phone("87654321"), new Email("bob@example.com"));

        // Act
        String result = Messages.format(seller);

        // Assert
        String expected = "SELLER; Bob Bee; Phone: 87654321; Email: bob@example.com";
        assertEquals(expected, result);
    }
}
