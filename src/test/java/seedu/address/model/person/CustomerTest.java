package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;
import seedu.address.model.util.Remark;

public class CustomerTest {

    private Customer customer;
    private Customer sameCustomer;
    private Customer differentCustomer;
    private Set<Tag> tags;

    @BeforeEach
    public void setUp() {
        tags = new HashSet<>();
        tags.add(new Tag("loyal"));

        customer = new Customer(
                new Name("Alice Tan"),
                new Phone("91234567"),
                new Email("alice@example.com"),
                new Address("123 Orchard Rd"),
                new Information("Allergic to nuts"),
                new Remark("Preferred customer"),
                tags
        );

        // Create a customer with the same details to check for equality
        sameCustomer = new Customer(
                new Name("Alice Tan"),
                new Phone("91234567"),
                new Email("alice@example.com"),
                new Address("123 Orchard Rd"),
                new Information("Allergic to nuts"),
                new Remark("Preferred customer"),
                tags
        );

        // Create a customer with different details
        differentCustomer = new Customer(
                new Name("Bob Lee"),
                new Phone("92345678"),
                new Email("bob@example.com"),
                new Address("456 Marina Bay"),
                new Information("Likes gluten-free products"),
                new Remark("New customer"),
                new HashSet<>(Set.of(new Tag("new")))
        );
    }

    @Test
    public void testEquals() {
        // Same object -> returns true
        assertEquals(customer, customer);

        // Different object, same values -> returns true
        assertEquals(customer, sameCustomer);

        // Different values -> returns false
        assertNotEquals(customer, differentCustomer);

        // Null -> returns false
        assertNotEquals(customer, null);

        // Different type -> returns false
        assertNotEquals(customer, new Object());
    }

    @Test
    public void testHashCode() {
        // Same object, same hash code
        assertEquals(customer.hashCode(), sameCustomer.hashCode());

        // Different values, different hash code
        assertNotEquals(customer.hashCode(), differentCustomer.hashCode());
    }

    @Test
    public void testToString() {
        String expectedString = "Name: Alice Tan Phone: 91234567 Email: alice@example.com "
                + "Address: 123 Orchard Rd Tags: [loyal] Remark: Preferred customer "
                + "Information: Allergic to nuts";
        assertEquals(expectedString, customer.toString());
    }

    @Test
    public void getInformation_returnsCorrectInformation() {
        assertEquals("Allergic to nuts", customer.getInformation().toString());
    }
}
