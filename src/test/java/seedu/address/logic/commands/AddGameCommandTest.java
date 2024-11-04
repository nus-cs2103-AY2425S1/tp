package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GAME;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddGameCommand.GameDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.game.Game;
import seedu.address.model.person.Person;
import seedu.address.testutil.GameDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
/**
 * Contains integration tests (interaction with the Model) and unit tests for AddGameCommand.
 */
public class AddGameCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @AfterEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }
    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withGames(VALID_GAME).build();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        GameDescriptor descriptor = new GameDescriptorBuilder().withGame(VALID_GAME).build();
        AddGameCommand addCommand = new AddGameCommand(INDEX_FIRST_PERSON, VALID_GAME, descriptor);
        Game expectedGame = editedPerson.getGames().get(VALID_GAME);
        String expectedMessage = String.format(AddGameCommand.MESSAGE_ADD_GAME_SUCCESS, Messages.format(expectedGame));

        assertCommandSuccess(addCommand, model, expectedMessage, expectedModel);
        assertEquals(expectedGame.getGameName(), VALID_GAME);
        addCommand.undo(model);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withGames(VALID_GAME).build();

        model.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        // edit person in filtered list into a duplicate in address book
        Person personInList = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        AddGameCommand addCommand = new AddGameCommand(INDEX_FIRST_PERSON, VALID_GAME,
                new GameDescriptorBuilder().build());

        assertCommandFailure(addCommand, model, AddGameCommand.MESSAGE_GAME_EXISTS);
    }
    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withGames(VALID_GAME).build();

        model.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        // edit person in filtered list into a duplicate in address book
        Person personInList = model.getAddressBook().getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        AddGameCommand addCommand = new AddGameCommand(INDEX_FIRST_PERSON, VALID_GAME,
                new GameDescriptorBuilder().withGame(VALID_GAME).build());

        assertCommandFailure(addCommand, model, AddGameCommand.MESSAGE_GAME_EXISTS);
    }
}
