package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private static final Person GHOST = new PersonBuilder().withName("Ghost").withPhone("12345678").build();

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        AddressBook ab = getTypicalAddressBook();
        // Add a person with all optional fields missing
        ab.addPerson(GHOST);
        model = new ModelManager(ab, new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(null, false), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(null, false), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listSortedByName_success() {
        ListCommand listCommand = new ListCommand("name", false);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.sortFilteredPersonList(Comparator.comparing(person -> person.getName().fullName));
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listSortedByEmail_success() {
        ListCommand listCommand = new ListCommand("email", false);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.sortFilteredPersonList(
            Comparator.comparing(
                person -> person.getEmail() != null ? person.getEmail().value : null,
                Comparator.nullsLast(Comparator.naturalOrder())
            )
        );
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listSortedByIncome_success() {
        ListCommand listCommand = new ListCommand("income", false);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.sortFilteredPersonList(
            Comparator.comparing(
                person -> person.getIncome() != null ? person.getIncome().value : null,
                Comparator.nullsLast(Comparator.naturalOrder())
            )
        );
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listSortedByAge_success() {
        ListCommand listCommand = new ListCommand("age", false);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.sortFilteredPersonList(
            Comparator.comparing(
                person -> person.getAge() != null ? person.getAge().value : null,
                Comparator.nullsLast(Comparator.naturalOrder())
            )
        );
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listSortedByNameReversed_success() {
        ListCommand listCommand = new ListCommand("name", true);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.sortFilteredPersonList(
            Comparator.comparing((Person person) -> person.getName().fullName).reversed()
        );
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listSortedByEmailReversed_success() {
        ListCommand listCommand = new ListCommand("email", true);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.sortFilteredPersonList(
            Comparator.comparing((Person person) -> person.getEmail() != null ? person.getEmail().value : null,
                Comparator.nullsLast(Comparator.naturalOrder())
            ).reversed()
        );
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listSortedByIncomeReversed_success() {
        ListCommand listCommand = new ListCommand("income", true);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.sortFilteredPersonList(
            Comparator.comparing((Person person) -> person.getIncome() != null ? person.getIncome().value : null,
                Comparator.nullsLast(Comparator.naturalOrder())
            ).reversed()
        );
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listSortedByAgeReversed_success() {
        ListCommand listCommand = new ListCommand("age", true);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.sortFilteredPersonList(
            Comparator.comparing((Person person) -> person.getAge() != null ? person.getAge().value : null,
                Comparator.nullsLast(Comparator.naturalOrder())
            ).reversed()
        );
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listWithInvalidSortField_throwsCommandException() {
        ListCommand listCommand = new ListCommand("invalid", false);
        assertThrows(CommandException.class, () -> listCommand.execute(model));
    }

    @Test
    public void execute_listWithInvalidSortFieldAndReverse_throwsCommandException() {
        ListCommand listCommand = new ListCommand("invalid", true);
        assertThrows(CommandException.class, () -> listCommand.execute(model));
    }
}
