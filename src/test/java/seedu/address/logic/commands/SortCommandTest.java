package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.SortCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class SortCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_sortAscending_success() throws CommandException {
        SortCommand sortCommand = new SortCommand(true);
        CommandResult commandResult = sortCommand.execute(model);

        List<Person> sortedList = model.getFilteredPersonList();
        assertEquals("Sorted person list by interview score in ascending order", commandResult.getFeedbackToUser());
        // Add assertions to check the order of the sorted list
    }

    @Test
    public void execute_sortDescending_success() throws CommandException {
        SortCommand sortCommand = new SortCommand(false);
        CommandResult commandResult = sortCommand.execute(model);

        List<Person> sortedList = model.getFilteredPersonList();
        assertEquals("Sorted person list by interview score in descending order", commandResult.getFeedbackToUser());
        // Add assertions to check the order of the sorted list
    }

    @Test
    public void execute_invalidSortOrder_throwsParseException() {
        SortCommandParser parser = new SortCommandParser();
        assertThrows(ParseException.class, () -> parser.parse("invalid"));
    }
}
