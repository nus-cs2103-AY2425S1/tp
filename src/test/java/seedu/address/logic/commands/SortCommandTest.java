package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.UnsortedTypicalPersons.getTypicalAddressBook;

import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class SortCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_sortListWithoutModifyingModel_success() {
        SortCommand sortCommand = new SortCommand();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateSortedPersonList();

        assertCommandSuccess(sortCommand, model, SortCommand.MESSAGE_SORT_LIST_SUCCESS, expectedModel);

        // Checks that model remains unchanged
        assertEquals(model, expectedModel);

        List<Person> sortedList = model.getFilteredPersonList().stream()
                .sorted(Comparator.comparing(x -> x.getName().fullName))
                .toList();

        assertEquals(sortedList, model.getFilteredPersonList());
    }
}
