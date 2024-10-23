package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOB_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOB_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Email;
import seedu.address.model.person.InterviewScore;
import seedu.address.model.person.Job;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.skill.Skill;
import seedu.address.model.tag.Tag;

public class ViewStatusCommandTest {
    public final Name amyName = new Name(VALID_NAME_AMY);
    public final Name bobName = new Name(VALID_NAME_BOB);
    public final Job amyJob = new Job(VALID_JOB_AMY);
    public final Job bobJob = new Job(VALID_JOB_BOB);
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_validInput_success() {
        ViewStatusCommand command = new ViewStatusCommand(amyName, amyJob);

        // Verify that the name and job are correctly initialized
        assertEquals(amyName, command.name);
        assertEquals(amyJob, command.job);
    }

    @Test
    public void execute_viewSuccessPending() {
        // tests the viewing of status for the first person in TypicalAddressBook
        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ViewStatusCommand viewStatusCommand = new ViewStatusCommand(person.getName(), person.getJob());
        String expectedMessage = String.format(ViewStatusCommand.MESSAGE_VIEW_SUCCESS, Messages.formatStatus(person));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(viewStatusCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_viewFailurePending() {
        // amy doesn't exist in the typical address book
        ViewStatusCommand viewStatusCommand = new ViewStatusCommand(amyName, amyJob);
        String expectedMessage = String.format(ViewStatusCommand.MESSAGE_VIEW_FAILURE, amyName, amyJob);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(viewStatusCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_viewSuccessHired() {
        Person hiredPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased() + 2);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        String expectedMessage = String.format(ViewStatusCommand.MESSAGE_VIEW_SUCCESS,
                Messages.formatStatus(hiredPerson));
        ViewStatusCommand viewStatusCommand = new ViewStatusCommand(hiredPerson.getName(), hiredPerson.getJob());

        assertCommandSuccess(viewStatusCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_viewSuccessRejected() {
        Person rejectedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased() + 3);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        String expectedMessage = String.format(ViewStatusCommand.MESSAGE_VIEW_SUCCESS,
                Messages.formatStatus(rejectedPerson));
        ViewStatusCommand viewStatusCommand = new ViewStatusCommand(rejectedPerson.getName(), rejectedPerson.getJob());

        assertCommandSuccess(viewStatusCommand, model, expectedMessage, expectedModel);
        //TODO: Mark hiredPerson or rejectedPerson before assertCommandSuccess.
    }

    @Test
    public void execute_viewStatusWithMultipleTags_success() {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        Person personWithMultipleTags = new Person(
                new Name("Alice Pauline"),
                new Job("Software Engineer"),
                new Phone("85355255"),
                new Email("alice@example.com"),
                new HashSet<>(Set.of(new Skill("python"))),
                new InterviewScore("6"),
                new HashSet<>(Arrays.asList(new Tag("friends"), new Tag("colleagues")))
        );
        model.addPerson(personWithMultipleTags);

        ViewStatusCommand viewStatusCommand = new ViewStatusCommand(
                personWithMultipleTags.getName(), personWithMultipleTags.getJob());
        String expectedMessage = String.format(ViewStatusCommand.MESSAGE_VIEW_SUCCESS,
                Messages.formatStatus(personWithMultipleTags));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(viewStatusCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_viewStatusWithDifferentJob_failure() {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        Person personWithDifferentJob = new Person(
                new Name("Alice Pauline"),
                new Job("Data Analyst"),
                new Phone("85355255"),
                new Email("alice@example.com"),
                new HashSet<>(Set.of(new Skill("python"))),
                new InterviewScore("6"),
                new HashSet<>(Arrays.asList(new Tag("friends")))
        );
        model.addPerson(personWithDifferentJob);

        ViewStatusCommand viewStatusCommand = new ViewStatusCommand(
                new Name("Alice Pauline"), new Job("Software Engineer"));
        String expectedMessage = String.format(
                ViewStatusCommand.MESSAGE_VIEW_FAILURE, new Name("Alice Pauline"), new Job("Software Engineer"));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(viewStatusCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_viewStatusPersonNotFound_failure() {
        ViewStatusCommand viewStatusCommand = new ViewStatusCommand(
                new Name("Non Existent"), new Job("Software Engineer"));
        String expectedMessage = String.format(ViewStatusCommand.MESSAGE_VIEW_FAILURE,
                new Name("Non Existent"), new Job("Software Engineer"));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(viewStatusCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final ViewStatusCommand standardCommand = new ViewStatusCommand(amyName, amyJob);

        // same values -> returns true
        ViewStatusCommand commandWithSameValues = new ViewStatusCommand(amyName, amyJob);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new ViewStatusCommand(bobName, amyJob)));

        // different remark -> returns false
        assertFalse(standardCommand.equals(new ViewStatusCommand(amyName, bobJob)));
    }
}
