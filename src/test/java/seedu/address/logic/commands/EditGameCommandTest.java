package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccessUnfilter;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddGameCommand.GameDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.game.Game;
import seedu.address.model.game.Role;
import seedu.address.model.game.SkillLevel;
import seedu.address.model.game.Username;
import seedu.address.model.person.Person;
import seedu.address.testutil.GameBuilder;
import seedu.address.testutil.PersonBuilder;

public class EditGameCommandTest {


    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        //build an edited person
        Person firstPerson = model.getFilteredPersonList().get(0);
        Person editedPerson = new PersonBuilder(firstPerson).build();

        //build the edited game
        Game editedGame = new GameBuilder().build();
        editedPerson.getGames().put("LoL", editedGame);

        //put in things to edit
        GameDescriptor descriptor = new GameDescriptor();
        descriptor.setGame("LoL");
        descriptor.setUsername(new Username("lmao"));
        descriptor.setSkillLevel(new SkillLevel("nub"));
        descriptor.setRole(new Role("npc"));
        descriptor.setFavouriteStatus(false);

        EditGameCommand editGameCommand = new EditGameCommand(INDEX_FIRST_PERSON, "LoL", descriptor);

        String expectedMessage = String.format(EditGameCommand.MESSAGE_EDIT_GAME_SUCCESS, Messages.format(editedGame));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editGameCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());
        Person editedPerson = new PersonBuilder(lastPerson).build();

        //build the edited game
        Game editedGame = new GameBuilder()
                .withSkillLevel("")
                .withRole("")
                .build();

        editedPerson.getGames().put("LoL", editedGame);

        GameDescriptor descriptor = new GameDescriptor();
        descriptor.setGame("LoL");
        descriptor.setUsername(new Username("lmao"));

        EditGameCommand editGameCommand = new EditGameCommand(indexLastPerson, "LoL", descriptor);

        String expectedMessage = String.format(EditGameCommand.MESSAGE_EDIT_GAME_SUCCESS, Messages.format(editedGame));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(indexLastPerson.getZeroBased()), editedPerson);

        assertCommandSuccess(editGameCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).build();

        //build the edited game
        Game editedGame = new GameBuilder()
                .withSkillLevel("anotherSkillLevel")
                .withRole("anotherRole")
                .build();

        editedPerson.getGames().put("LoL", editedGame);

        GameDescriptor descriptor = new GameDescriptor();
        descriptor.setGame("LoL");
        descriptor.setUsername(new Username("lmao"));
        descriptor.setSkillLevel(new SkillLevel("anotherSkillLevel"));
        descriptor.setRole(new Role("anotherRole"));
        descriptor.setFavouriteStatus(false);

        EditGameCommand editCommand = new EditGameCommand(INDEX_FIRST_PERSON, "LoL", descriptor);

        String expectedMessage = String.format(EditGameCommand.MESSAGE_EDIT_GAME_SUCCESS, Messages.format(editedGame));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);
        assertCommandSuccessUnfilter(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        GameDescriptor descriptor = new GameDescriptor();
        descriptor.setGame("LoL");
        descriptor.setUsername(new Username("lmao"));
        descriptor.setSkillLevel(new SkillLevel("anotherSkillLevel"));
        descriptor.setRole(new Role("anotherRole"));

        EditGameCommand editGameCommand = new EditGameCommand(outOfBoundIndex, "LoL", descriptor);

        assertCommandFailure(editGameCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        GameDescriptor descriptor = new GameDescriptor();
        descriptor.setGame("LoL");
        descriptor.setUsername(new Username("lmao"));
        descriptor.setSkillLevel(new SkillLevel("anotherSkillLevel"));
        descriptor.setRole(new Role("anotherRole"));
        descriptor.setFavouriteStatus(false);

        EditGameCommand editGameCommand = new EditGameCommand(outOfBoundIndex,
                "LoL", descriptor);

        assertCommandFailure(editGameCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
}
