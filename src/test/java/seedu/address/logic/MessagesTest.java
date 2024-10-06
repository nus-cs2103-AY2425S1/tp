package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.formatStatus;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class MessagesTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void formatStatus_correctFormatting() {
        // if tag is pending
        Person hiredPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased() + 2);
        assertTrue(formatStatus(hiredPerson).equals("Carl Kurz; Job: Software Engineer L3; Status: pending"));\
        //TODO: When tag is hired or rejected
    }
}
