// src/test/java/seedu/address/logic/StaticContextTest.java
package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.WeddingBuilder;

public class StaticContextTest {

    @Test
    public void testSetAndGetPersonToDelete() {
        // Initially, personToDelete should be null
        assertNull(StaticContext.getPersonToDelete());

        // Set a person to delete
        Person person = new PersonBuilder().withName("John Doe").build();
        StaticContext.setPersonToDelete(person);

        // Verify that the personToDelete is correctly set
        assertEquals(person, StaticContext.getPersonToDelete());
    }

    @Test
    public void testGetPersonToDeleteInitiallyNull() {
        // Initially, personToDelete should be null
        assertNull(StaticContext.getPersonToDelete());
    }

    @Test
    public void testSetAndGetWeddingToDelete() {
        // Initially, weddingToDelete should be null
        assertNull(StaticContext.getWeddingToDelete());

        // Set a wedding to delete
        Wedding wedding = new WeddingBuilder().withWeddingName("Jane & Stacy").build();
        StaticContext.setWeddingToDelete(wedding);

        // Verify that the weddingToDelete is correctly set
        assertEquals(wedding, StaticContext.getWeddingToDelete());
    }

    @Test
    public void testGetWeddingToDeleteInitiallyNull() {
        // Initially, weddingToDelete should be null
        assertNull(StaticContext.getWeddingToDelete());
    }

    @Test
    public void testClearStaticContext() {
        // Set static context variables to non-null values
        Person person = new PersonBuilder().withName("John Doe").build();
        Wedding wedding = new WeddingBuilder().withWeddingName("Jane & Stacy").build();
        StaticContext.setPersonToDelete(person);
        StaticContext.setWeddingToDelete(wedding);
        StaticContext.setClearAddressBookPending(true);
        StaticContext.setClearWeddingBookPending(true);

        // Call clearStaticContext
        StaticContext.clearStaticContext();

        // Verify that all static context variables are cleared
        assertNull(StaticContext.getPersonToDelete());
        assertNull(StaticContext.getWeddingToDelete());
        assertFalse(StaticContext.isClearAddressBookPending());
        assertFalse(StaticContext.isClearWeddingBookPending());
    }
}
