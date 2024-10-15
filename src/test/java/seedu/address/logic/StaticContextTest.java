package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class StaticContextTest {

    @Test
    public void setAndGetPersonToDelete() {
        Person person = new PersonBuilder().build();
        StaticContext.setPersonToDelete(person);
        assertEquals(person, StaticContext.getPersonToDelete());

        StaticContext.setPersonToDelete(null);
        assertNull(StaticContext.getPersonToDelete());
    }
}
