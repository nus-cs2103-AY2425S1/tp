package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonIsStarredPredicate;
import seedu.address.model.person.StarredStatus;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
    @Test
    public void execute_listIsEmpty_showsEmptyMessage() {
        // Set up an empty model for testing
        Model emptyModel = new ModelManager(new AddressBook(), new UserPrefs());
        Model expectedEmptyModel = new ModelManager(new AddressBook(), new UserPrefs());

        // Check that the command shows the empty list message
        assertCommandSuccess(new ListCommand(), emptyModel, ListCommand.MESSAGE_EMPTY_LIST, expectedEmptyModel);
    }
    @Test
    public void execute_listStarredContacts_showsStarredList() {
        Person personToStar = model.getFilteredPersonList().get(0);
        Person alreadyStarredPerson = new Person(
                personToStar.getName(),
                personToStar.getPhone(),
                personToStar.getEmail(),
                personToStar.getAddress(),
                personToStar.getAge(),
                personToStar.getSex(),
                personToStar.getAppointment(),
                personToStar.getTags(),
                personToStar.getNote(),
                new StarredStatus("true"));
        model.setPerson(personToStar, alreadyStarredPerson);
        expectedModel.setPerson(personToStar, alreadyStarredPerson);

        PersonIsStarredPredicate predicate = new PersonIsStarredPredicate();
        ListCommand listStarredCommand = new ListCommand(predicate);

        model.updateFilteredPersonList(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(listStarredCommand, model, ListCommand.MESSAGE_STARRED_LIST, expectedModel);
    }

    @Test
    public void execute_listNoStarredContacts_showsStarredList() {
        Model noStarredModel = new ModelManager(new AddressBook(), new UserPrefs());
        Model expectedNoStarredModel = new ModelManager(new AddressBook(), new UserPrefs());

        PersonIsStarredPredicate predicate = new PersonIsStarredPredicate();
        ListCommand listStarredCommand = new ListCommand(predicate);

        assertCommandSuccess(listStarredCommand, noStarredModel, ListCommand.MESSAGE_EMPTY_STARRED_LIST,
                expectedNoStarredModel);
    }
}
