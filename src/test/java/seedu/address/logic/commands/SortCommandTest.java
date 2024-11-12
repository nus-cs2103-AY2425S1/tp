package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;

public class SortCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model emptyModel = new ModelManager();

    @Test
    public void execute_sortNoCriteria_success() {
        SortCommand sortCommand = new SortCommand(new ArrayList<Prefix>());
        sortCommand.execute(model);

        // Sort expected model manually for comparison
        ObservableList<Person> sortedByName = FXCollections.observableArrayList(
                TypicalPersons.ALICE, TypicalPersons.BENSON, TypicalPersons.CARL, // adjust order based on name sorting
                TypicalPersons.DANIEL, TypicalPersons.ELLE, TypicalPersons.FIONA, TypicalPersons.GEORGE);

        assertEquals(sortedByName, model.getFilteredPersonList());
    }

    @Test
    public void execute_sortByTag_success() {
        SortCommand sortCommand = new SortCommand(new ArrayList<>(Arrays.asList(PREFIX_TAG)));
        sortCommand.execute(model);

        // Sort expected model manually for comparison
        ObservableList<Person> sortedByName = FXCollections.observableArrayList(
                TypicalPersons.ALICE, TypicalPersons.DANIEL, TypicalPersons.ELLE, // adjust order based on name sorting
                TypicalPersons.BENSON, TypicalPersons.CARL, TypicalPersons.FIONA, TypicalPersons.GEORGE);

        assertEquals(sortedByName, model.getFilteredPersonList());
    }

    @Test
    public void execute_sortWithEmptyList_failure() {
        SortCommand sortCommand = new SortCommand(new ArrayList<Prefix>());
        CommandResult emptyListCommandResult = sortCommand.execute(emptyModel);
        assertEquals(emptyListCommandResult.getFeedbackToUser(), SortCommand.MESSAGE_EMPTY);
    }
}
