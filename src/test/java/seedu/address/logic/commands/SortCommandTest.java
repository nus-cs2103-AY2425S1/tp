package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code SortCommand}.
 */
public class SortCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_sortByNameAscending_success() throws CommandException {
        Comparator<Person> comparator = Comparator.comparing(person -> person.getName().fullName);
        SortCommand sortCommand = new SortCommand(comparator);
        String expectedMessage = SortCommand.MESSAGE_SUCCESS;

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        List<Person> sortedList = new ArrayList<>(expectedModel.getFilteredPersonList());
        sortedList.sort(comparator);
        expectedModel.setFilteredPersonList(sortedList);

        CommandResult result = sortCommand.executeCommand(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_sortByNameDescending_success() throws CommandException {
        Comparator<Person> comparator = (p1, p2) -> p2.getName().fullName
                .compareToIgnoreCase(p1.getName().fullName);
        SortCommand sortCommand = new SortCommand(comparator);
        String expectedMessage = SortCommand.MESSAGE_SUCCESS;

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        List<Person> sortedList = new ArrayList<>(expectedModel.getFilteredPersonList());
        sortedList.sort(comparator);
        expectedModel.setFilteredPersonList(sortedList);

        CommandResult result = sortCommand.executeCommand(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_sortByClass_success() throws CommandException {
        Comparator<Person> comparator = Comparator.comparing(person -> {
            return person.getClasses().stream()
                    .min((class1, class2) -> {
                        int num1 = Integer.parseInt(class1.replaceAll("[^0-9]", ""));
                        int num2 = Integer.parseInt(class2.replaceAll("[^0-9]", ""));
                        String section1 = class1.replaceAll("[0-9]", "");
                        String section2 = class2.replaceAll("[0-9]", "");
                        int numberComparison = Integer.compare(num1, num2);
                        return numberComparison != 0 ? numberComparison : section1.compareTo(section2);
                    }).orElse("");
        });
        SortCommand sortCommand = new SortCommand(comparator);
        String expectedMessage = SortCommand.MESSAGE_SUCCESS;

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        List<Person> sortedList = new ArrayList<>(expectedModel.getFilteredPersonList());
        sortedList.sort(comparator);
        expectedModel.setFilteredPersonList(sortedList);

        CommandResult result = sortCommand.executeCommand(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_sortBySubject_success() throws CommandException {
        Comparator<Person> comparator = Comparator.comparing(person -> person.getSubjects().toString());
        SortCommand sortCommand = new SortCommand(comparator);
        String expectedMessage = SortCommand.MESSAGE_SUCCESS;

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        List<Person> sortedList = new ArrayList<>(expectedModel.getFilteredPersonList());
        sortedList.sort(comparator);
        expectedModel.setFilteredPersonList(sortedList);

        CommandResult result = sortCommand.executeCommand(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
    }

    @Test
    public void equals_sameComparator_returnsTrue() {
        Comparator<Person> comparator = Comparator.comparing(person -> person.getName().fullName);
        SortCommand sortCommand1 = new SortCommand(comparator);
        SortCommand sortCommand2 = new SortCommand(comparator);

        assertEquals(sortCommand1, sortCommand2);
    }
    @Test
    public void execute_nullModel_throwsNullPointerException() {
        Comparator<Person> comparator = Comparator.comparing(person -> person.getName().fullName);
        SortCommand sortCommand = new SortCommand(comparator);

        try {
            sortCommand.executeCommand(null);
        } catch (NullPointerException | CommandException e) {
            assertEquals(NullPointerException.class, e.getClass());
        }
    }
}
