package seedu.address.logic.commands;

import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.FilterTagCommand.MESSAGE_TAG_DOESNT_EXIST;
import static seedu.address.testutil.TypicalPersons.*;
import static seedu.address.testutil.TypicalTags.FRIENDS;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.TagContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

public class FilterTagCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_filterNonExistentTag() {
        String expectedMessage = MESSAGE_TAG_DOESNT_EXIST;
        FilterTagCommand filterTagCommand = new FilterTagCommand(new TagContainsKeywordsPredicate(FRIENDS));
        assertCommandFailure(filterTagCommand, model, expectedMessage);
    }

    @Test
    public void execute_filterTag_success() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        model.addTag(FRIENDS);
        expectedModel.addTag(FRIENDS);
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(FRIENDS);
        FilterTagCommand command = new FilterTagCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filterValidTagWithNoPersons() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Tag tag = new Tag("Test tag");
        model.addTag(tag);
        expectedModel.addTag(tag);
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(tag);
        FilterTagCommand command = new FilterTagCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }
}
