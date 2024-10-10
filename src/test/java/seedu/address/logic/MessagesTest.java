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
        // if tag is hired
        Person hiredPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased() + 2);
        assertTrue(formatStatus(hiredPerson).equals("Carl Kurz; Job: Software Engineer L3; Status: hired"));
        //if tag is rejected
        Person rejectedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased() + 3);
        assertTrue(formatStatus(rejectedPerson).equals("Daniel Meier; Job: Software Engineer L4; Status: rejected"));
        // if tag is pending
        Person pendingPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        assertTrue(formatStatus(pendingPerson).equals("Alice Pauline; Job: Software Engineer L1; Status: pending"));
        //TODO: Change the pending, rejected, hired person's status from pending to respective status.
    }
}
