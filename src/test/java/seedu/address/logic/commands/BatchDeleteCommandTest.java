package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.predicates.PersonContainsTagsPredicate;
import seedu.address.model.tag.Tag;

class BatchDeleteCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        Set<Tag> tags = Set.of();
        PersonContainsTagsPredicate predicate = new PersonContainsTagsPredicate(tags);
        // Tags is null
        assertThrows(NullPointerException.class, () -> new BatchDeleteCommand(null, predicate));

        // Predicate is null
        assertThrows(NullPointerException.class, () -> new BatchDeleteCommand(tags, null));

        // Both arguments are null
        assertThrows(NullPointerException.class, () -> new BatchDeleteCommand(null, null));
    }

    @Test
    public void execute_validTags_success() {
        Set<Tag> tagsV1 = Set.of(new Tag("friends"), new Tag("owesMoney"));
        PersonContainsTagsPredicate predicate1 = new PersonContainsTagsPredicate(tagsV1);
        BatchDeleteCommand batchDeleteCommand = new BatchDeleteCommand(tagsV1, predicate1);

        String expectedMessage = String.format(
                BatchDeleteCommand.MESSAGE_BATCH_DELETE_EACH_PERSON_SUCCESS,
                Messages.format(BENSON)
        );

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(BENSON);
        expectedModel.updateFilteredPersonList(Predicate.not(Model.PREDICATE_SHOW_ALL_PERSONS));

        assertCommandSuccess(batchDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void toStringMethod() {
        Set<Tag> tagsV1 = Set.of(new Tag("friends"), new Tag("owesMoney"));
        PersonContainsTagsPredicate predicate1 = new PersonContainsTagsPredicate(tagsV1);
        BatchDeleteCommand batchDeleteCommand1 = new BatchDeleteCommand(tagsV1, predicate1);

        String expected = BatchDeleteCommand.class.getCanonicalName()
                + "{tags="
                + tagsV1
                + ", predicate="
                + predicate1
                + "}";
        assertEquals(expected, batchDeleteCommand1.toString());
    }

    @Test
    public void equals() {
        Set<Tag> tagsV1 = Set.of(new Tag("friends"), new Tag("owesMoney"));
        PersonContainsTagsPredicate predicate1 = new PersonContainsTagsPredicate(tagsV1);
        BatchDeleteCommand batchDeleteCommand1 = new BatchDeleteCommand(tagsV1, predicate1);

        Set<Tag> tagsV2 = Set.of(new Tag("friends"));
        PersonContainsTagsPredicate predicate2 = new PersonContainsTagsPredicate(tagsV2);
        BatchDeleteCommand batchDeleteCommand2 = new BatchDeleteCommand(tagsV2, predicate2);

        // same object -> returns true
        assertTrue(batchDeleteCommand1.equals(batchDeleteCommand1));

        // same values -> returns true
        BatchDeleteCommand batchDeleteCommandV1Copy = new BatchDeleteCommand(tagsV1, predicate1);
        assertTrue(batchDeleteCommand1.equals(batchDeleteCommandV1Copy));

        // different types -> returns false
        assertFalse(batchDeleteCommand1.equals(1));

        // null -> returns false
        assertFalse(batchDeleteCommand1.equals(null));

        // different person -> returns false
        assertFalse(batchDeleteCommand1.equals(batchDeleteCommand2));
    }
}
