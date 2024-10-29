package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SIXTH_PERSON;
import static seedu.address.testutil.TypicalTags.FRIENDS_TAG;
import static seedu.address.testutil.TypicalTags.TEST_SET;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.CompanyBuilder;
import seedu.address.testutil.StudentBuilder;

public class DeleteTagCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexValidTags_studentSuccess() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> tagsToDelete = new HashSet<>();
        tagsToDelete.add(FRIENDS_TAG);
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(INDEX_FIRST_PERSON, tagsToDelete);

        Person expectedPerson = new StudentBuilder().withName("Alice Pauline")
                .withStudentID("A1234567X")
                .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
                .withPhone("94351253").build();
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToEdit, expectedPerson);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS,
                expectedPerson);

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteMultipleTags_studentSuccess() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Set<Tag> tagsToDelete = new HashSet<>();
        tagsToDelete.add(FRIENDS_TAG);
        tagsToDelete.add(new Tag("owesMoney"));
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(INDEX_SECOND_PERSON, tagsToDelete);

        Person expectedPerson = new StudentBuilder().withName("Benson Meier")
                .withStudentID("A7654321X")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withEmail("benson@example.com").withPhone("98765432").build();
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToEdit, expectedPerson);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS,
                expectedPerson);

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexValidTags_companySuccess() {
        Person contactToEdit = model.getFilteredPersonList().get(INDEX_SIXTH_PERSON.getZeroBased());
        Set<Tag> tagsToDelete = new HashSet<>();
        tagsToDelete.add(new Tag("reliable"));
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(INDEX_SIXTH_PERSON, tagsToDelete);

        Person expectedPerson = new CompanyBuilder().withName("Amazing Bank")
                .withIndustry("Banking")
                .withPhone("84871319")
                .withEmail("abank@example.com").withAddress("Money Fly").withTags("loyalPartner").build();
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setPerson(contactToEdit, expectedPerson);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS,
                expectedPerson);

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void toStringMethod() {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("loyalPartner"));
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(INDEX_SIXTH_PERSON, tags);
        String expected = DeleteTagCommand.class.getCanonicalName()
                + "{targetIndex=" + INDEX_SIXTH_PERSON + ", tagsToDelete=" + tags + "}";
        assertEquals(expected, deleteTagCommand.toString());
    }

    @Test
    public void execute_invalidTag_throwsCommandException() {
        Set<Tag> tagsToDelete = new HashSet<>();
        tagsToDelete.add(FRIENDS_TAG);
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(INDEX_SIXTH_PERSON, tagsToDelete);

        assertThrows(CommandException.class,
                String.format(DeleteTagCommand.MESSAGE_INVALID_TAG, FRIENDS_TAG), ()
                 -> deleteTagCommand.execute(model));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Set<Tag> tagsToDelete = new HashSet<>();
        tagsToDelete.add(FRIENDS_TAG);
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(Index.fromOneBased(7), tagsToDelete);

        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () -> deleteTagCommand.execute(model));
    }

    @Test
    public void equalsMethod() {
        DeleteTagCommand deleteTagCommand2 = new DeleteTagCommand(INDEX_FIRST_PERSON, TEST_SET);
        DeleteTagCommand deleteTagCommand3 = new DeleteTagCommand(INDEX_SECOND_PERSON, TEST_SET);

        assertTrue(deleteTagCommand2.equals(deleteTagCommand2));
        assertFalse(deleteTagCommand2.equals(deleteTagCommand3));
        assertFalse(deleteTagCommand2.equals("Not a DeleteTagCommand object"));
        assertFalse(deleteTagCommand2.equals(null));
    }
}
