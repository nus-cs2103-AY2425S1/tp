// src/test/java/seedu/address/logic/StaticContextTest.java
package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

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
}