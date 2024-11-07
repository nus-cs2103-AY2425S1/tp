package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.TEACHER_ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_commandExceptionThrown() {
        Model model = new ModelManager();

        assertCommandFailure(new ClearCommand(null), model, ClearCommand.MESSAGE_NO_ACTION);
    }

    @Test
    public void execute_noMatchingEntries_commandExceptionThrown() throws ParseException {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        assertCommandFailure(new ClearCommand(new PersonContainsKeywordsPredicate(Arrays.asList("/name", "TEST_FAIL"))),
                model, ClearCommand.MESSAGE_NO_ACTION);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() throws ParseException {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(new PersonContainsKeywordsPredicate(List.of(""))),
                model, ClearCommand.MESSAGE_CLEAR_ALL, expectedModel);
    }

    @Test
    public void execute_matchingEntries_success() throws ParseException {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.deletePerson(TEACHER_ALICE);

        assertCommandSuccess(new ClearCommand(new PersonContainsKeywordsPredicate(Arrays.asList("/name", "Alice"))),
                model, String.format(ClearCommand.MESSAGE_SUCCESS, 1), expectedModel);
    }

}
