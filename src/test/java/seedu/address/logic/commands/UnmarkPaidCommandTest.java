package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Address;
import seedu.address.model.person.ClassId;
import seedu.address.model.person.Email;
import seedu.address.model.person.Fees;
import seedu.address.model.person.MonthPaid;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;


public class UnmarkPaidCommandTest {
    private static final Set<MonthPaid> VALID_MONTHSPAID = Set.of(new MonthPaid("2024-01"));
    private static final String VALID_MONTHPAID_STRING = "2024-01";

    @Test
    public void execute_unmarkPaidPerson_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new CommandHistory());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new CommandHistory());
        Index index = INDEX_FIRST_PERSON;
        UnmarkPaidCommand.UnmarkPaidTarget target = UnmarkPaidCommand.UnmarkPaidTarget.fromIndex(index);
        UnmarkPaidCommand command = new UnmarkPaidCommand(target, VALID_MONTHSPAID);
        Person modifiedPerson = createUnmarkedPerson(
                model.getFilteredPersonList().get(index.getZeroBased()), VALID_MONTHSPAID);
        String expectedMessage = String.format(UnmarkPaidCommand.MESSAGE_UNMARKPAID_PERSON_SUCCESS,
                Messages.unmarkPaidFormat(modifiedPerson));
        expectedModel.setPerson(model.getFilteredPersonList().get(index.getZeroBased()),
                modifiedPerson);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unmarkPaidAll_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new CommandHistory());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new CommandHistory());
        UnmarkPaidCommand.UnmarkPaidTarget target = UnmarkPaidCommand.UnmarkPaidTarget.all();
        UnmarkPaidCommand command = new UnmarkPaidCommand(target, VALID_MONTHSPAID);
        for (int i = 0; i < model.getFilteredPersonList().size(); i++) {
            Person modifiedPerson = createUnmarkedPerson(
                    model.getFilteredPersonList().get(i), VALID_MONTHSPAID);
            expectedModel.setPerson(model.getFilteredPersonList().get(i),
                    modifiedPerson);
        }
        String expectedMonthPaidString = "[" + VALID_MONTHPAID_STRING + "]";
        String expectedMessage = String.format(UnmarkPaidCommand.MESSAGE_UNMARKPAID_ALL_SUCCESS,
                expectedMonthPaidString);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }
    @Test
    public void toStringMethod() {
        UnmarkPaidCommand.UnmarkPaidTarget target = UnmarkPaidCommand.UnmarkPaidTarget.fromIndex(INDEX_FIRST_PERSON);
        UnmarkPaidCommand command = new UnmarkPaidCommand(target, VALID_MONTHSPAID);
        assertEquals("seedu.address.logic.commands.UnmarkPaidCommand{target="
                        + target.toString()
                        + ", monthsPaid=[[" + VALID_MONTHPAID_STRING + "]]}",
                command.toString());
    }
    @Test
    public void equals() {
        UnmarkPaidCommand.UnmarkPaidTarget target = UnmarkPaidCommand.UnmarkPaidTarget.fromIndex(INDEX_FIRST_PERSON);
        UnmarkPaidCommand command1 = new UnmarkPaidCommand(target, VALID_MONTHSPAID);
        UnmarkPaidCommand command2 = new UnmarkPaidCommand(target, VALID_MONTHSPAID);
        UnmarkPaidCommand command3 = new UnmarkPaidCommand(target, VALID_MONTHSPAID);
        UnmarkPaidCommand command4 = new UnmarkPaidCommand(target, Collections.emptySet());
        assertTrue(command1.equals(command2));
        assertFalse(command1.equals(command4));
        assertTrue(command1.equals(command3));
        assertTrue(command3.equals(command1));
    }

    private static Person createUnmarkedPerson(Person personToMark, Set<MonthPaid> monthsPaid) {
        assert personToMark != null;
        assert monthsPaid != null;
        // TODO: should we use editPersonDescriptor here instead?
        Name name = personToMark.getName();
        Phone phone = personToMark.getPhone();
        Email email = personToMark.getEmail();
        Address address = personToMark.getAddress();
        Fees fees = personToMark.getFees();
        ClassId classId = personToMark.getClassId();
        Set<MonthPaid> updatedMonthsPaid = new HashSet<>(personToMark.getMonthsPaid());
        updatedMonthsPaid.removeAll(monthsPaid);
        Set<Tag> tags = personToMark.getTags();

        return new Person(name, phone, email, address, fees, classId,
                updatedMonthsPaid, tags);
    }
}
