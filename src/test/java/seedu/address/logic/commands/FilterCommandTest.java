package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;


public class FilterCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void equals() {
        FilterCommand filterFirstCommand = new FilterCommand(new Tag("friends"));
        FilterCommand filterSecondCommand = new FilterCommand(new Tag("colleagues"));

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(new Tag("friends"));
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_noTagFilteredFor_showsNothing() {
        expectedModel.updateFilteredPersonList(person -> false);
        String[] absentTags = {
            "bestFriends",
            "Colleagues",
            "4Horsemen"
        };
        for (String absentTag : absentTags) {
            FilterCommand filterCommand = new FilterCommand(new Tag(absentTag));
            assertCommandSuccess(
                    filterCommand,
                    model,
                    String.format(FilterCommand.MESSAGE_SUCCESS, absentTag),
                    expectedModel
            );
        }
    }

    @Test
    public void execute_tagFilteredForPresent_showsFiltered() {
        String[] presentTags = {
            "owesMoney",
            "friends"
        };
        for (String presentTag : presentTags) {
            FilterCommand filterCommand = new FilterCommand(new Tag(presentTag));
            expectedModel.updateFilteredPersonList(
                    person -> person.getTags().contains(new Tag(presentTag))
            );
            assertCommandSuccess(
                    filterCommand,
                    model,
                    String.format(FilterCommand.MESSAGE_SUCCESS, presentTag),
                    expectedModel
            );
        }
    }
}
