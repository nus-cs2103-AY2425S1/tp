package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_GAME;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddGameCommand.GameDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.game.Game;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.GameDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
/**
 * Contains integration tests (interaction with the Model) and unit tests for AddGameCommand.
 */
public class AddGameCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person editedPerson = new PersonBuilder().withGames(VALID_GAME).build();
        GameDescriptor descriptor = new GameDescriptorBuilder().withGame(VALID_GAME).build();

        AddGameCommand addCommand = new AddGameCommand(INDEX_FIRST_PERSON, VALID_GAME, descriptor);

        Game expectedGame = editedPerson.getGames().get(VALID_GAME);
        String expectedMessage = String.format(AddGameCommand.MESSAGE_ADD_GAME_SUCCESS, Messages.format(expectedGame));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(addCommand, model, expectedMessage, model);
    }
}
