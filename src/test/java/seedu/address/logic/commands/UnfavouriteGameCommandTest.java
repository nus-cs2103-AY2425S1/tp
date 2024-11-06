package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.game.Game;
import seedu.address.model.person.Person;
import seedu.address.testutil.GameBuilder;
import seedu.address.testutil.PersonBuilder;

public class UnfavouriteGameCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_unfavourite_success() {
        //build an edited person
        Person secondPerson = model.getFilteredPersonList().get(1);
        Person editedPerson = new PersonBuilder(secondPerson).build();

        //build the edited game
        Game unfavouritedGame = new GameBuilder(new Game("LoL")).build();
        editedPerson.getGames().put("LoL", unfavouritedGame);

        UnfavouriteGameCommand unfavGameCommand = new UnfavouriteGameCommand(INDEX_SECOND_PERSON, "LoL");

        String expectedMessage = String.format(UnfavouriteGameCommand.MESSAGE_UNFAVOURITE_GAME_SUCCESS,
                unfavouritedGame.gameName);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(1), editedPerson);

        assertCommandSuccess(unfavGameCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        UnfavouriteGameCommand unfavGameCommand = new UnfavouriteGameCommand(outOfBoundIndex, "random game");
        assertCommandFailure(unfavGameCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        UnfavouriteGameCommand unfavGameCommand = new UnfavouriteGameCommand(outOfBoundIndex, "random game");
        assertCommandFailure(unfavGameCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_gameNotFound_failure() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());

        UnfavouriteGameCommand unfavGameCommand = new UnfavouriteGameCommand(indexLastPerson, "NonexistentGame");

        assertCommandFailure(unfavGameCommand, model, FavouriteGameCommand.MESSAGE_GAME_NOT_FOUND);
    }
}
