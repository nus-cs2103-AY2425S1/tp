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

public class FavouriteGameCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_favourite_success() {
        //build an edited person
        Person firstPerson = model.getFilteredPersonList().get(0);
        Person editedPerson = new PersonBuilder(firstPerson).build();

        //build the edited game
        Game favouritedGame = new GameBuilder(new Game("LoL")).build();
        favouritedGame.setAsFavourite();
        editedPerson.getGames().put("LoL", favouritedGame);

        FavouriteGameCommand favGameCommand = new FavouriteGameCommand(INDEX_FIRST_PERSON, "LoL");

        String expectedMessage = String.format(FavouriteGameCommand.MESSAGE_FAVOURITE_GAME_SUCCESS,
                favouritedGame.gameName);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);
        System.out.println(firstPerson.getGames().get("LoL").username.toString());

        assertCommandSuccess(favGameCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        FavouriteGameCommand favGameCommand = new FavouriteGameCommand(outOfBoundIndex, "random game");
        assertCommandFailure(favGameCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        FavouriteGameCommand favGameCommand = new FavouriteGameCommand(outOfBoundIndex, "random game");
        assertCommandFailure(favGameCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_gameNotFound_failure() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());

        FavouriteGameCommand favGameCommand = new FavouriteGameCommand(indexLastPerson, "NonexistentGame");

        assertCommandFailure(favGameCommand, model, FavouriteGameCommand.MESSAGE_GAME_NOT_FOUND);
    }
}
