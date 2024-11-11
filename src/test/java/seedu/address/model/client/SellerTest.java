package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

class SellerTest {

    @Test
    void testIsSameClient_sameClient_returnsTrue() {
        // Arrange
        NameWithoutNumber name = mock(NameWithoutNumber.class);
        Phone phone = mock(Phone.class);
        Email email = mock(Email.class);

        Seller seller1 = new Seller(name, phone, email);

        // Act & Assert
        assertTrue(seller1.isSameClient(seller1));
    }

    @Test
    void testIsSameClient_samePhoneDifferentSeller_returnsTrue() {
        // Arrange
        NameWithoutNumber name1 = mock(NameWithoutNumber.class);
        NameWithoutNumber name2 = mock(NameWithoutNumber.class);
        Phone phone = mock(Phone.class);
        Email email1 = mock(Email.class);
        Email email2 = mock(Email.class);

        Seller seller1 = new Seller(name1, phone, email1);
        Seller seller2 = new Seller(name2, phone, email2); // Different name and email, same phone

        // Act & Assert
        assertTrue(seller1.isSameClient(seller2));
    }

    @Test
    void testIsSameClient_differentPhone_returnsFalse() {
        // Arrange
        NameWithoutNumber name1 = mock(NameWithoutNumber.class);
        Phone phone1 = mock(Phone.class);
        Phone phone2 = mock(Phone.class);
        Email email1 = mock(Email.class);
        Email email2 = mock(Email.class);

        Seller seller1 = new Seller(name1, phone1, email1);
        Seller seller2 = new Seller(name1, phone2, email2); // Different phone

        // Act & Assert
        assertFalse(seller1.isSameClient(seller2));
    }

    @Test
    void testIsDuplicateEmail_sameClient_returnsTrue() {
        // Arrange
        NameWithoutNumber name = mock(NameWithoutNumber.class);
        Phone phone = mock(Phone.class);
        Email email = mock(Email.class);

        Seller seller = new Seller(name, phone, email);

        // Act & Assert
        assertTrue(seller.isDuplicateEmail(seller));
    }

    @Test
    void testIsDuplicateEmail_nullClient_returnsFalse() {
        // Arrange
        NameWithoutNumber name = mock(NameWithoutNumber.class);
        Phone phone = mock(Phone.class);
        Email email = mock(Email.class);

        Seller seller = new Seller(name, phone, email);

        // Act & Assert
        assertFalse(seller.isDuplicateEmail(null));
    }

    @Test
    void testIsDuplicateEmail_sellerWithSameEmail_returnsTrue() {
        // Arrange
        NameWithoutNumber name = mock(NameWithoutNumber.class);
        Phone phone1 = mock(Phone.class);
        Phone phone2 = mock(Phone.class); // Different phone
        Email email = mock(Email.class);

        when(email.toString()).thenReturn("seller@example.com");

        Seller seller1 = new Seller(name, phone1, email);
        Seller seller2 = new Seller(name, phone2, email); // Same email, different phone

        // Act & Assert
        assertTrue(seller1.isDuplicateEmail(seller2));
    }

    @Test
    void testIsDuplicateEmail_buyerWithSameEmailDifferentPhone_returnsTrue() {
        // Arrange
        NameWithoutNumber name = mock(NameWithoutNumber.class);
        Phone phone1 = mock(Phone.class);
        Phone phone2 = mock(Phone.class); // Different phone
        Email email = mock(Email.class);

        when(email.toString()).thenReturn("buyer@example.com");

        Seller seller = new Seller(name, phone1, email);
        Buyer buyer = new Buyer(name, phone2, email); // Same email, different phone

        // Act & Assert
        assertTrue(seller.isDuplicateEmail(buyer));
    }

    @Test
    void testIsDuplicateEmail_buyerWithSameEmailSamePhone_returnsFalse() {
        // Arrange
        NameWithoutNumber name = mock(NameWithoutNumber.class);
        Phone phone = mock(Phone.class);
        Email email = mock(Email.class);

        when(email.toString()).thenReturn("same@example.com");

        Seller seller = new Seller(name, phone, email);
        Buyer buyer = new Buyer(name, phone, email); // Same email and phone

        // Act & Assert
        assertFalse(seller.isDuplicateEmail(buyer));
    }

    @Test
    void testEquals_sameObject_returnsTrue() {
        // Arrange
        NameWithoutNumber name = mock(NameWithoutNumber.class);
        Phone phone = mock(Phone.class);
        Email email = mock(Email.class);

        Seller seller = new Seller(name, phone, email);

        // Act & Assert
        assertEquals(seller, seller);
    }

    @Test
    void testEquals_sameAttributes_returnsTrue() {
        // Arrange
        NameWithoutNumber name1 = mock(NameWithoutNumber.class);
        Phone phone1 = mock(Phone.class);
        Email email1 = mock(Email.class);

        Seller seller1 = new Seller(name1, phone1, email1);
        Seller seller2 = new Seller(name1, phone1, email1); // Same attributes

        // Act & Assert
        assertEquals(seller1, seller2);
    }

    @Test
    void testEquals_differentAttributes_returnsFalse() {
        // Arrange
        NameWithoutNumber name1 = mock(NameWithoutNumber.class);
        NameWithoutNumber name2 = mock(NameWithoutNumber.class);
        Phone phone1 = mock(Phone.class);
        Phone phone2 = mock(Phone.class);
        Email email1 = mock(Email.class);
        Email email2 = mock(Email.class);

        Seller seller1 = new Seller(name1, phone1, email1);
        Seller seller2 = new Seller(name2, phone2, email2); // Different attributes

        // Act & Assert
        assertNotEquals(seller1, seller2);
    }

    @Test
    void testHashCode_sameAttributes_returnsSameHashCode() {
        // Arrange
        NameWithoutNumber name = mock(NameWithoutNumber.class);
        Phone phone = mock(Phone.class);
        Email email = mock(Email.class);

        Seller seller1 = new Seller(name, phone, email);
        Seller seller2 = new Seller(name, phone, email); // Same attributes

        // Act & Assert
        assertEquals(seller1.hashCode(), seller2.hashCode());
    }

    @Test
    void testHashCode_differentAttributes_returnsDifferentHashCode() {
        // Arrange
        NameWithoutNumber name1 = mock(NameWithoutNumber.class);
        Phone phone1 = mock(Phone.class);
        Email email1 = mock(Email.class);

        NameWithoutNumber name2 = mock(NameWithoutNumber.class);
        Phone phone2 = mock(Phone.class);
        Email email2 = mock(Email.class);

        Seller seller1 = new Seller(name1, phone1, email1);
        Seller seller2 = new Seller(name2, phone2, email2); // Different attributes

        // Act & Assert
        assertNotEquals(seller1.hashCode(), seller2.hashCode());
    }

    @Test
    void testToString_correctFormat() {
        // Arrange
        NameWithoutNumber name = mock(NameWithoutNumber.class);
        Phone phone = mock(Phone.class);
        Email email = mock(Email.class);

        when(name.toString()).thenReturn("Jane Smith");
        when(phone.toString()).thenReturn("87654321");
        when(email.toString()).thenReturn("jane@example.com");

        Seller seller = new Seller(name, phone, email);

        // Act & Assert
        String expectedString = "seedu.address.model.client.Seller"
                + "{client type=SELLER, name=Jane Smith, phone=87654321, email=jane@example.com}";

        assertEquals(expectedString, seller.toString());
    }

    @Test
    void testGetTypeString_returnsCorrectType() {
        // Arrange
        Seller seller = new Seller(mock(NameWithoutNumber.class), mock(Phone.class), mock(Email.class));

        // Act
        String typeString = seller.getTypeString();

        // Assert
        assertEquals(ClientTypes.SELLER.getType(), typeString);
    }
}
