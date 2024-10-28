package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SortCommandTest {
    private static final String INVALID_ATTRIBUTE = "gamer";
    private static final String VALID_ATTRIBUTE_DATE = "date";
    private static final String VALID_ATTRIBUTE_EMAIL = "email";
    private static final String VALID_ATTRIBUTE_NAME = "name";
    private static final String VALID_ATTRIBUTE_PHONE = "phone";
    private static final String VALID_ATTRIBUTE_ROLE = "role";

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullAttribute_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SortCommand(null, false));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        SortCommand sortCommand = new SortCommand("", false);
        assertThrows(NullPointerException.class, () -> sortCommand.execute(null));
    }

    @Test
    public void execute_invalidAttribute_throwsCommandException() {
        boolean isAscending = false;
        String attribute = INVALID_ATTRIBUTE;
        SortCommand sortCommand = new SortCommand(attribute, isAscending);
        assertThrows(CommandException.class,
                SortCommand.MESSAGE_UNKNOWN_ATTRIBUTE_MAIN, () -> sortCommand.execute(model));
    }

    @Test
    public void execute_validAttributeDate() throws CommandException {
        boolean isAscending = true;
        String attribute = VALID_ATTRIBUTE_DATE;
        CommandResult commandResult = new SortCommand(attribute, isAscending).execute(model);
        assertEquals(String.format(SortCommand.MESSAGE_SUCCESS_MAIN + (isAscending ? "ascending " : "descending ")
                + attribute), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_validAttributeEmail() throws CommandException {
        boolean isAscending = true;
        String attribute = VALID_ATTRIBUTE_EMAIL;
        CommandResult commandResult = new SortCommand(attribute, isAscending).execute(model);
        assertEquals(String.format(SortCommand.MESSAGE_SUCCESS_MAIN + (isAscending ? "ascending " : "descending ")
                + attribute), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_validAttributeName() throws CommandException {
        boolean isAscending = false;
        String attribute = VALID_ATTRIBUTE_NAME;
        CommandResult commandResult = new SortCommand(attribute, isAscending).execute(model);
        assertEquals(String.format(SortCommand.MESSAGE_SUCCESS_MAIN + (isAscending ? "ascending " : "descending ")
                + attribute), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_validAttributePhone() throws CommandException {
        boolean isAscending = true;
        String attribute = VALID_ATTRIBUTE_PHONE;
        CommandResult commandResult = new SortCommand(attribute, isAscending).execute(model);
        assertEquals(String.format(SortCommand.MESSAGE_SUCCESS_MAIN + (isAscending ? "ascending " : "descending ")
                + attribute), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_validAttributeRole() throws CommandException {
        boolean isAscending = false;
        String attribute = VALID_ATTRIBUTE_ROLE;
        CommandResult commandResult = new SortCommand(attribute, isAscending).execute(model);
        assertEquals(String.format(SortCommand.MESSAGE_SUCCESS_MAIN + (isAscending ? "ascending " : "descending ")
                + attribute), commandResult.getFeedbackToUser());
    }
}
