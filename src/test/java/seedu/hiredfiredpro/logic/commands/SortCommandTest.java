package seedu.hiredfiredpro.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.hiredfiredpro.testutil.TypicalPersons.getTypicalHiredFiredPro;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.hiredfiredpro.logic.commands.exceptions.CommandException;
import seedu.hiredfiredpro.logic.parser.SortCommandParser;
import seedu.hiredfiredpro.logic.parser.exceptions.ParseException;
import seedu.hiredfiredpro.model.Model;
import seedu.hiredfiredpro.model.ModelManager;
import seedu.hiredfiredpro.model.UserPrefs;
import seedu.hiredfiredpro.model.person.Person;

public class SortCommandTest {

    private Model model = new ModelManager(getTypicalHiredFiredPro(), new UserPrefs());

    @Test
    public void execute_sortAscending_success() throws CommandException {
        SortCommand sortCommand = new SortCommand(true);
        CommandResult commandResult = sortCommand.execute(model);

        List<Person> sortedList = model.getFilteredPersonList();
        assertEquals("Sorted candidate list by interview score in ascending order", commandResult.getFeedbackToUser());
        // Add assertions to check the order of the sorted list
    }

    @Test
    public void execute_sortDescending_success() throws CommandException {
        SortCommand sortCommand = new SortCommand(false);
        CommandResult commandResult = sortCommand.execute(model);

        List<Person> sortedList = model.getFilteredPersonList();
        assertEquals("Sorted candidate list by interview score in descending order", commandResult.getFeedbackToUser());
        // Add assertions to check the order of the sorted list
    }

    @Test
    public void execute_invalidSortOrder_throwsParseException() {
        SortCommandParser parser = new SortCommandParser();
        assertThrows(ParseException.class, () -> parser.parse("invalid"));
    }
}
