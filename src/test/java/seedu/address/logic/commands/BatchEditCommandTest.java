package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonContainsTagsPredicate;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

class BatchEditCommandTest {
    private Tag friendsTag = new Tag("friends");
    private Tag frenTag = new Tag("fren");

    @Test
    public void constructor_nullArguments_throwsNullPointerException() {

        PersonContainsTagsPredicate predicate = new PersonContainsTagsPredicate(Set.of(friendsTag));
        // Tags are null
        assertThrows(NullPointerException.class, () -> new BatchEditCommand(null, null, predicate));

        // One Tag is null
        assertThrows(NullPointerException.class, () -> new BatchEditCommand(friendsTag, null, predicate));
        assertThrows(NullPointerException.class, () -> new BatchEditCommand(null, frenTag, predicate));

        // Predicate is null
        assertThrows(NullPointerException.class, () -> new BatchEditCommand(friendsTag, frenTag, null));

        // All args are null
        assertThrows(NullPointerException.class, () -> new BatchEditCommand(null, null, null));
    }

    @Test
    public void execute_validTags_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        PersonContainsTagsPredicate predicate = new PersonContainsTagsPredicate(Set.of(friendsTag));
        BatchEditCommand batchEditCommand = new BatchEditCommand(friendsTag, frenTag, predicate);

        Person changedAlice = new PersonBuilder().withName("Alice Pauline")
                .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
                .withPhone("94351253").withRole("student")
                .withTags("fren").build();
        Person changedBenson = new PersonBuilder().withName("Benson Meier")
                .withRole("Student")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withEmail("johnd@example.com").withPhone("98765432")
                .withTags("owesMoney", "fren").build();
        Person changedDaniel = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
                .withRole("Parent")
                .withEmail("cornelia@example.com").withAddress("10th street")
                .withTags("fren").build();

        String expectedMessage =
                String.format(BatchEditCommand.MESSAGE_BATCH_EDIT_EACH_PERSON_CHANGED, friendsTag, frenTag);


        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(ALICE, changedAlice);
        expectedModel.setPerson(BENSON, changedBenson);
        expectedModel.setPerson(DANIEL, changedDaniel);
        expectedModel.updateFilteredPersonList(new PersonContainsTagsPredicate(Set.of(frenTag)));

        assertCommandSuccess(batchEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistentTag_successWithMessage() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Tag nonExistentTag = new Tag("nonExistentTag");
        PersonContainsTagsPredicate predicate = new PersonContainsTagsPredicate(Set.of(nonExistentTag));
        BatchEditCommand batchEditCommand = new BatchEditCommand(nonExistentTag, friendsTag, predicate);

        String expectedMessage = String.format(BatchEditCommand.MESSAGE_BATCH_EDIT_NO_PERSON_WITH_TAG, nonExistentTag);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        // Shows the changed person
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(batchEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sameTag_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        PersonContainsTagsPredicate predicate = new PersonContainsTagsPredicate(Set.of(friendsTag));
        BatchEditCommand batchEditCommand = new BatchEditCommand(friendsTag, friendsTag, predicate);

        String expectedMessage =
                String.format(BatchEditCommand.MESSAGE_BATCH_EDIT_EACH_PERSON_CHANGED, friendsTag, friendsTag);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        // Shows the changed person
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(batchEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void toStringMethod() {
        PersonContainsTagsPredicate predicate = new PersonContainsTagsPredicate(Set.of(friendsTag));
        BatchEditCommand batchEditCommand = new BatchEditCommand(friendsTag, frenTag, predicate);

        String expected = BatchEditCommand.class.getCanonicalName()
                + "{oldTag="
                + friendsTag
                + ", newTag="
                + frenTag
                + ", predicate="
                + predicate
                + "}";
        assertEquals(expected, batchEditCommand.toString());
    }

    @Test
    public void equals() {
        PersonContainsTagsPredicate predicate = new PersonContainsTagsPredicate(Set.of(friendsTag));
        BatchEditCommand batchEditCommand = new BatchEditCommand(friendsTag, frenTag, predicate);

        PersonContainsTagsPredicate differentPredicate = new PersonContainsTagsPredicate(Set.of());
        BatchEditCommand differentBatchEdit = new BatchEditCommand(
                new Tag("differentTag"),
                frenTag,
                differentPredicate
        );

        // same object -> returns true
        assertTrue(batchEditCommand.equals(batchEditCommand));

        // same values -> returns true
        BatchEditCommand batchEditCommandV1Copy = new BatchEditCommand(friendsTag, frenTag, predicate);
        assertTrue(batchEditCommand.equals(batchEditCommandV1Copy));

        // different types -> returns false
        assertFalse(batchEditCommand.equals(1));

        // null -> returns false
        assertFalse(batchEditCommand.equals(null));

        // different tags -> returns false
        assertFalse(batchEditCommand.equals(differentBatchEdit));
    }
}
