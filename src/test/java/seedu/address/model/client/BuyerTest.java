package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

class BuyerTest {

    @Test
    void testIsSameClient_sameClient_returnsTrue() {
        // Arrange
        Name name = mock(Name.class);
        Phone phone = mock(Phone.class);
        Email email = mock(Email.class);

        Buyer buyer1 = new Buyer(name, phone, email);

        // Act & Assert
        assertTrue(buyer1.isSameClient(buyer1));
    }

    @Test
    void testIsSameClient_samePhoneDifferentBuyer_returnsTrue() {
        // Arrange
        Name name1 = mock(Name.class);
        Name name2 = mock(Name.class);
        Phone phone = mock(Phone.class);
        Email email1 = mock(Email.class);
        Email email2 = mock(Email.class);

        Buyer buyer1 = new Buyer(name1, phone, email1);
        Buyer buyer2 = new Buyer(name2, phone, email2); // Different name and email, same phone

        // Act & Assert
        assertTrue(buyer1.isSameClient(buyer2));
    }

    @Test
    void testIsSameClient_differentPhone_returnsFalse() {
        // Arrange
        Name name1 = mock(Name.class);
        Phone phone1 = mock(Phone.class);
        Phone phone2 = mock(Phone.class);
        Email email1 = mock(Email.class);
        Email email2 = mock(Email.class);

        Buyer buyer1 = new Buyer(name1, phone1, email1);
        Buyer buyer2 = new Buyer(name1, phone2, email2); // Different phone

        // Act & Assert
        assertFalse(buyer1.isSameClient(buyer2));
    }

    @Test
    void testEquals_sameObject_returnsTrue() {
        // Arrange
        Name name = mock(Name.class);
        Phone phone = mock(Phone.class);
        Email email = mock(Email.class);

        Buyer buyer = new Buyer(name, phone, email);

        // Act & Assert
        assertEquals(buyer, buyer);
    }

    @Test
    void testEquals_sameAttributes_returnsTrue() {
        // Arrange
        Name name1 = mock(Name.class);
        Phone phone1 = mock(Phone.class);
        Email email1 = mock(Email.class);

        Buyer buyer1 = new Buyer(name1, phone1, email1);
        Buyer buyer2 = new Buyer(name1, phone1, email1); // Same attributes

        // Act & Assert
        assertEquals(buyer1, buyer2);
    }

    @Test
    void testEquals_differentAttributes_returnsFalse() {
        // Arrange
        Name name1 = mock(Name.class);
        Name name2 = mock(Name.class);
        Phone phone1 = mock(Phone.class);
        Phone phone2 = mock(Phone.class);
        Email email1 = mock(Email.class);
        Email email2 = mock(Email.class);

        Buyer buyer1 = new Buyer(name1, phone1, email1);
        Buyer buyer2 = new Buyer(name2, phone2, email2); // Different attributes

        // Act & Assert
        assertNotEquals(buyer1, buyer2);
    }

    @Test
    void testHashCode_sameAttributes_returnsSameHashCode() {
        // Arrange
        Name name = mock(Name.class);
        Phone phone = mock(Phone.class);
        Email email = mock(Email.class);

        Buyer buyer1 = new Buyer(name, phone, email);
        Buyer buyer2 = new Buyer(name, phone, email); // Same attributes

        // Act & Assert
        assertEquals(buyer1.hashCode(), buyer2.hashCode());
    }

    @Test
    void testHashCode_differentAttributes_returnsDifferentHashCode() {
        // Arrange
        Name name1 = mock(Name.class);
        Phone phone1 = mock(Phone.class);
        Email email1 = mock(Email.class);

        Name name2 = mock(Name.class);
        Phone phone2 = mock(Phone.class);
        Email email2 = mock(Email.class);

        Buyer buyer1 = new Buyer(name1, phone1, email1);
        Buyer buyer2 = new Buyer(name2, phone2, email2); // Different attributes

        // Act & Assert
        assertNotEquals(buyer1.hashCode(), buyer2.hashCode());
    }

    @Test
    void testToString_correctFormat() {
        // Arrange
        Name name = mock(Name.class);
        Phone phone = mock(Phone.class);
        Email email = mock(Email.class);

        when(name.toString()).thenReturn("John Doe");
        when(phone.toString()).thenReturn("12345678");
        when(email.toString()).thenReturn("john@example.com");

        Buyer buyer = new Buyer(name, phone, email);

        // Act & Assert
        String expectedString = "seedu.address.model.client.Buyer"
                + "{client type=BUYER, name=John Doe, phone=12345678, email=john@example.com}";

        assertEquals(expectedString, buyer.toString());
    }

    @Test
    void testGetTypeString_returnsCorrectType() {
        // Arrange
        Buyer buyer = new Buyer(mock(Name.class), mock(Phone.class), mock(Email.class));

        // Act
        String typeString = buyer.getTypeString();

        // Assert
        assertEquals(ClientTypes.BUYER.getType(), typeString);
    }
}
