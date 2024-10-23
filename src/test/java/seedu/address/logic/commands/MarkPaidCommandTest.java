package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.MonthPaid;
import seedu.address.model.person.Person;

public class MarkPaidCommandTest {
    private static final Set<MonthPaid> VALID_MONTHSPAID = Set.of(new MonthPaid("2024-01"));
    private static final String VALID_MONTHPAID_STRING = "2024-01";
    @Test
    public void execute_markPaidPerson_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new CommandHistory());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new CommandHistory());
        Index index = INDEX_FIRST_PERSON;
        MarkPaidCommand.MarkPaidTarget target = MarkPaidCommand.MarkPaidTarget.fromIndex(index);
        MarkPaidCommand command = new MarkPaidCommand(target, VALID_MONTHSPAID);
        Person modifiedPerson = createMarkedPerson(
                model.getFilteredPersonList().get(index.getZeroBased()), VALID_MONTHSPAID);
        String expectedMessage = String.format(MarkPaidCommand.MESSAGE_MARKPAID_PERSON_SUCCESS,
                Messages.markPaidFormat(modifiedPerson));
        expectedModel.setPerson(model.getFilteredPersonList().get(index.getZeroBased()),
                modifiedPerson);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void toStringMethod() {
        MarkPaidCommand.MarkPaidTarget target = MarkPaidCommand.MarkPaidTarget.fromIndex(INDEX_FIRST_PERSON);
        MarkPaidCommand command = new MarkPaidCommand(target, VALID_MONTHSPAID);
        assertEquals("seedu.address.logic.commands.MarkPaidCommand{target="
                        + target.toString()
                        + ", monthsPaid=[[" + VALID_MONTHPAID_STRING + "]]}",
                command.toString());
    }
    @Test
    public void equals() {
        MarkPaidCommand.MarkPaidTarget target = MarkPaidCommand.MarkPaidTarget.fromIndex(INDEX_FIRST_PERSON);
        MarkPaidCommand command1 = new MarkPaidCommand(target, VALID_MONTHSPAID);
        MarkPaidCommand command2 = new MarkPaidCommand(target, VALID_MONTHSPAID);
        MarkPaidCommand command3 = new MarkPaidCommand(target, VALID_MONTHSPAID);
        MarkPaidCommand command4 = new MarkPaidCommand(target, Collections.emptySet());
        assertTrue(command1.equals(command2));
        assertFalse(command1.equals(command4));
        assertTrue(command1.equals(command3));
        assertTrue(command3.equals(command1));
    }

    private Person createMarkedPerson(Person personToMark, Set<MonthPaid> monthPaid) {
        // create a new Person with the updated months paid
        return new Person(personToMark.getName(), personToMark.getPhone(), personToMark.getEmail(),
                personToMark.getAddress(), personToMark.getFees(), personToMark.getClassId(),
                monthPaid, personToMark.getTags());
    }
}
