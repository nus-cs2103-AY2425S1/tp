package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

public class AddVendorCommandTest {


    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddVendorCommand(null));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Amy").build();
        Person bob = new PersonBuilder().withName("Ben").build();
        AddVendorCommand addAmyCommand = new AddVendorCommand(alice);
        AddVendorCommand addBenCommand = new AddVendorCommand(bob);

        // same object -> returns true
        assertTrue(addAmyCommand.equals(addAmyCommand));

        // same values -> returns true
        AddVendorCommand addAliceCommandCopy = new AddVendorCommand(alice);
        assertTrue(addAmyCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAmyCommand.equals(1));

        // null -> returns false
        assertFalse(addAmyCommand.equals(null));

        // different person -> returns false
        assertFalse(addAmyCommand.equals(addBenCommand));
    }

    @Test
    public void toStringMethod() {
        AddVendorCommand addVendorCommand = new AddVendorCommand(ALICE);
        String expected = AddVendorCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addVendorCommand.toString());
    }
}
