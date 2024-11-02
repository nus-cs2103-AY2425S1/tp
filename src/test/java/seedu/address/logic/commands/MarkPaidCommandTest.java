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


public class MarkPaidCommandTest {
    private static final Set<MonthPaid> VALID_MONTHSPAID = Set.of(new MonthPaid("2024-01"));
    private static final String VALID_MONTHPAID_STRING = "2024-01";

    @Test
    public void execute_markPaidPerson_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new CommandHistory());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new CommandHistory());
        Index index = INDEX_FIRST_PERSON;
        MarkPaidCommand.MarkPaidTarget target = MarkPaidCommand.MarkPaidTarget.fromIndex(index);
        MarkPaidCommand command = new MarkPaidCommand(target, VALID_MONTHSPAID, false);
        Person modifiedPerson = createMarkedPerson(
                model.getFilteredPersonList().get(index.getZeroBased()), VALID_MONTHSPAID,
                false);
        String expectedMessage = String.format(MarkPaidCommand.MESSAGE_MARKPAID_PERSON_SUCCESS,
                Messages.markPaidFormat(modifiedPerson));
        expectedModel.setPerson(model.getFilteredPersonList().get(index.getZeroBased()),
                modifiedPerson);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_markAllPersons_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new CommandHistory());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new CommandHistory());
        Set<MonthPaid> monthsPaid = Set.of(new MonthPaid("2024-01"));
        MarkPaidCommand command = new MarkPaidCommand(MarkPaidCommand.MarkPaidTarget.all(), monthsPaid, false);

        for (Person person : expectedModel.getFilteredPersonList()) {
            Person markedPerson = createMarkedPerson(person, monthsPaid, false);
            expectedModel.setPerson(person, markedPerson);
        }

        for (Person person : expectedModel.getFilteredPersonList()) {
            assertTrue(person.getMonthsPaid().containsAll(monthsPaid), "Person not marked as paid correctly in expected model.");
        }

        expectedModel.updateFilteredPersonList(person -> person.getMonthsPaid().containsAll(monthsPaid));

        String monthsPaidStr = monthsPaid.toString().replaceAll("^\\[|\\]$", "");
        String expectedMessage = String.format(MarkPaidCommand.MESSAGE_MARKPAID_ALL_SUCCESS, monthsPaidStr);

        // Assert the command success with model and expectedModel
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }



    @Test
    public void toStringMethod() {
        MarkPaidCommand.MarkPaidTarget target = MarkPaidCommand.MarkPaidTarget.fromIndex(INDEX_FIRST_PERSON);
        MarkPaidCommand command = new MarkPaidCommand(target, VALID_MONTHSPAID, false);
        assertEquals("seedu.address.logic.commands.MarkPaidCommand{target="
                        + target.toString()
                        + ", monthsPaid=[[" + VALID_MONTHPAID_STRING + "]], isRemoving=false}",
                command.toString());
    }
    @Test
    public void equals() {
        MarkPaidCommand.MarkPaidTarget target = MarkPaidCommand.MarkPaidTarget.fromIndex(INDEX_FIRST_PERSON);
        MarkPaidCommand command1 = new MarkPaidCommand(target, VALID_MONTHSPAID, false);
        MarkPaidCommand command2 = new MarkPaidCommand(target, VALID_MONTHSPAID, false);
        MarkPaidCommand command3 = new MarkPaidCommand(target, VALID_MONTHSPAID, false);
        MarkPaidCommand command4 = new MarkPaidCommand(target, Collections.emptySet(), false);
        assertTrue(command1.equals(command2));
        assertFalse(command1.equals(command4));
        assertTrue(command1.equals(command3));
        assertTrue(command3.equals(command1));
    }

    private static Person createMarkedPerson(Person personToMark, Set<MonthPaid> monthsPaid, boolean isRemoving) {
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
        if (isRemoving) {
            updatedMonthsPaid.removeAll(monthsPaid);
        } else {
            updatedMonthsPaid.addAll(monthsPaid);
        }
        Set<Tag> tags = personToMark.getTags();

        return new Person(name, phone, email, address, fees, classId,
                updatedMonthsPaid, tags);
    }
}
