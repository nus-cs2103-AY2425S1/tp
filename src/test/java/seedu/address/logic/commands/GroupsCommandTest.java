package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * GroupsCommand.
 */
public class GroupsCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_groupsWhileEmpty_showsEmptyGroups() {
        CommandResult expectedResult = new CommandResult(GroupsCommand.MESSAGE_NOGROUPS, false, false);
        assertCommandSuccess(new GroupsCommand(), model, expectedResult, expectedModel);
    }
    // TODO MORE TESTS

    @Test
    public void execute_groupsWhileNotEmpty_showsNotEmptyGroups() {
        CommandResult expectedResult = new CommandResult(GroupsCommand.MESSAGE_SUCCESS, false, false);
        List<Person> people = model.getFilteredPersonList();
        model.addGroup(new Group("abc", people));
        assertCommandSuccess(new GroupsCommand(), model, expectedResult, expectedModel);
    }
}
