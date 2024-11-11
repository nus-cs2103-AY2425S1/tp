package seedu.address.logic.commands;

import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalEvents;
import seedu.address.testutil.TypicalVolunteers;

import org.junit.jupiter.api.BeforeEach;

public class AssignCommandTest {
    AddressBook addressBook = new AddressBook();


    @BeforeEach
    public void resetAddressBook() {
        addressBook = new AddressBook();
        addressBook.addEvent(TypicalEvents.EVENT_A);
        addressBook.addVolunteer(TypicalVolunteers.ALICE);
    }

    

}
