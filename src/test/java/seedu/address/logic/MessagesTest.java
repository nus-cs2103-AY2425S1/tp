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
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingDate;
import seedu.address.model.meeting.MeetingTitle;
import seedu.address.model.property.Ask;
import seedu.address.model.property.Bid;
import seedu.address.model.property.PostalCode;
import seedu.address.model.property.Property;
import seedu.address.model.property.Type;
import seedu.address.model.property.Unit;

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

    @Test
    void formatProperty_success() {
        // Arrange
        Property property = new Property(new PostalCode("123456"), new Unit("12-75"), new Type("CONDO"),
                new Ask("50000"), new Bid("60000"));

        // Act
        String result = Messages.format(property);

        // Assert
        String expected = "PostalCode: 123456; Unit: 12-75; Type: CONDO; Ask: 50000; Bid: 60000";
        assertEquals(expected, result);
    }

    // New test for formatting Meeting
    @Test
    void formatMeeting_success() {
        // Arrange
        Meeting meeting = new Meeting(new MeetingTitle("Project Meeting"), new MeetingDate("01-01-2024"),
                new Phone("81234567"), new Phone("91234567"), new Type("hdb"), new PostalCode("123456"));

        // Act
        String result = Messages.format(meeting);

        // Assert
        String expected = "MeetingTitle: Project Meeting; MeetingDate: 01-01-2024; Buyer's Phone Number: 81234567; "
                + "Seller's Phone Number: 91234567; "
                + "Type: HDB; PostalCode: 123456";
        assertEquals(expected, result);
    }
}
