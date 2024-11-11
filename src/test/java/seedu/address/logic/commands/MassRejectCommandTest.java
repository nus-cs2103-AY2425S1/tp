package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.JobCodePredicate;
import seedu.address.model.person.JobCodeTagPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tag;
import seedu.address.model.person.TagPredicate;

public class MassRejectCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validJobCodePredicate_success() {
        Predicate<Person> predicate = new JobCodePredicate("SWE2024");
        MassRejectCommand command = new MassRejectCommand(predicate);

        String expectedMessage = String.format(MassRejectCommand.MESSAGE_SUCCESS, 1);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person rejectedPerson = createRejectedPerson(ALICE);
        expectedModel.setPerson(ALICE, rejectedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validTagPredicate_success() {
        Predicate<Person> predicate = new TagPredicate("TP");
        MassRejectCommand command = new MassRejectCommand(predicate);

        String expectedMessage = String.format(MassRejectCommand.MESSAGE_SUCCESS, 1);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person rejectedPerson = createRejectedPerson(BENSON);
        expectedModel.setPerson(BENSON, rejectedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validJobCodeTagPredicate_success() {
        Predicate<Person> predicate = new JobCodeTagPredicate("SWE2024", "N");
        MassRejectCommand command = new MassRejectCommand(predicate);

        String expectedMessage = String.format(MassRejectCommand.MESSAGE_SUCCESS, 1);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person rejectedPerson = createRejectedPerson(ALICE);
        expectedModel.setPerson(ALICE, rejectedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noMatchingPersons_failure() {
        Predicate<Person> predicate = new JobCodePredicate("NON_EXISTENT_JOB_CODE");
        MassRejectCommand command = new MassRejectCommand(predicate);

        assertCommandFailure(command, model, "No matching persons found.");
    }

    @Test
    public void equals() {
        Predicate<Person> predicateOne = new JobCodePredicate("SWE2024");
        Predicate<Person> predicateTwo = new TagPredicate("new");

        MassRejectCommand commandOne = new MassRejectCommand(predicateOne);
        MassRejectCommand commandTwo = new MassRejectCommand(predicateTwo);

        // same object -> returns true
        assertEquals(commandOne, commandOne);

        // same predicate -> returns true
        MassRejectCommand commandOneCopy = new MassRejectCommand(predicateOne);
        assertEquals(commandOne, commandOneCopy);

        // different predicate -> returns false
        assertFalse(commandOne.equals(commandTwo));

        // null -> returns false
        assertFalse(commandOne.equals(null));

        // different types -> returns false
        assertFalse(commandOne.equals(new ClearCommand()));
    }

    /**
     * Helper method to create a rejected person by setting their tag to "r" (rejected).
     */
    private Person createRejectedPerson(Person person) {
        return new Person(person.getName(), person.getPhone(), person.getEmail(), person.getJobCode(),
                new Tag("r"), person.getRemark());
    }
}
