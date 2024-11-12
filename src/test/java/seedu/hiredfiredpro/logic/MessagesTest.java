package seedu.hiredfiredpro.logic;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hiredfiredpro.logic.Messages.formatStatus;
import static seedu.hiredfiredpro.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.hiredfiredpro.testutil.TypicalPersons.getTypicalHiredFiredPro;

import org.junit.jupiter.api.Test;

import seedu.hiredfiredpro.model.Model;
import seedu.hiredfiredpro.model.ModelManager;
import seedu.hiredfiredpro.model.UserPrefs;
import seedu.hiredfiredpro.model.person.Person;

public class MessagesTest {
    private Model model = new ModelManager(getTypicalHiredFiredPro(), new UserPrefs());

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
