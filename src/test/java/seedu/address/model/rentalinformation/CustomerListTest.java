package seedu.address.model.rentalinformation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CustomerListTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CustomerList(null));
    }

    @Test
    public void constructor_invalidCustomerList_throwsIllegalArgumentException() {
        String invalidCustomerList = "";
        assertThrows(IllegalArgumentException.class, () -> new CustomerList(invalidCustomerList));
    }

    @Test
    public void isValidCustomerList() {
        // null customer list
        assertThrows(NullPointerException.class, () -> CustomerList.isValidCustomerList(null));

        // invalid customer list
        assertFalse(CustomerList.isValidCustomerList("")); // empty string
        assertFalse(CustomerList.isValidCustomerList(" ")); // spaces only
        assertFalse(CustomerList.isValidCustomerList(";David")); // invalid semicolon
        assertFalse(CustomerList.isValidCustomerList("David;")); // invalid semicolon
        assertFalse(CustomerList.isValidCustomerList("David;Steven;")); // invalid semicolon
        assertFalse(CustomerList.isValidCustomerList("David;;Steven")); // double semicolon

        // valid customer list
        assertTrue(CustomerList.isValidCustomerList("David")); // exactly 1 name
        assertTrue(CustomerList.isValidCustomerList("David;Steven")); // multiple names
        assertTrue(CustomerList.isValidCustomerList("David;Steven;Andrew")); // multiple names
    }

    @Test
    public void equals() {
        CustomerList customerList = new CustomerList("David;Steven");

        // same values -> returns true
        assertTrue(customerList.equals(new CustomerList("David;Steven")));
        assertTrue(customerList.equals(new CustomerList("Steven;David")));

        // same object -> returns true
        assertTrue(customerList.equals(customerList));

        // null -> returns false
        assertFalse(customerList.equals(null));

        // different types -> returns false
        assertFalse(customerList.equals(5.0f));

        // different values -> returns false
        assertFalse(customerList.equals(new CustomerList("David")));

        // value is null
        // one null
        assertFalse(customerList.equals(new CustomerList()));

        customerList = new CustomerList();
        assertFalse(customerList.equals(new CustomerList("David")));

        // both null
        assertTrue(customerList.equals(new CustomerList()));
    }

    @Test
    public void hashcode() {
        CustomerList customerList = new CustomerList("David;Steven");

        // same customer list
        assertEquals(customerList.hashCode(), new CustomerList("David;Steven").hashCode());

        // different customer list
        assertNotEquals(customerList.hashCode(), new CustomerList("Steven;David").hashCode());
    }

    @Test
    public void toStringMethod() {
        CustomerList customerList = new CustomerList("David;Steven");

        // same customer list
        assertEquals(customerList.toString(), new CustomerList("David;Steven").toString());

        // different customer list
        assertNotEquals(customerList.toString(), new CustomerList("David").toString());
        assertNotEquals(customerList.toString(), new CustomerList("Steven;David").toString());

        // null value in customer list
        customerList = new CustomerList();
        assertEquals("null", customerList.toString());
    }
}
