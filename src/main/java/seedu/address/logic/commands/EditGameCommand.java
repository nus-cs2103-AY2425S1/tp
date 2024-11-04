package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILLLEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Map;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddGameCommand.GameDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.game.Game;
import seedu.address.model.game.Role;
import seedu.address.model.game.SkillLevel;
import seedu.address.model.game.Username;
import seedu.address.model.person.Person;

/**
 * Represents a command to edit information about a game.
 * This game is stored within a {@code Person}.
 */
public class EditGameCommand extends Command {
    public static final String COMMAND_WORD = "editgame";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the game "
            + "under the person specified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_GAME + "GAMENAME] "
            + "[" + PREFIX_USERNAME + "USERNAME] "
            + "[" + PREFIX_SKILLLEVEL + "SKILLLEVEL] "
            + "[" + PREFIX_ROLE + "ROLE] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_GAME + "Valorant "
            + PREFIX_USERNAME + "johndoe123 "
            + PREFIX_SKILLLEVEL + "Gold 1 "
            + PREFIX_ROLE + "Duelist";

    public static final String MESSAGE_EDIT_GAME_SUCCESS = "Edited Game: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final String gameName;
    private final GameDescriptor editGameDescriptor;

    private Game gameToEdit;
    private Person personToEdit;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editGameDescriptor details to edit the game with
     */
    public EditGameCommand(Index index, String gameName, GameDescriptor editGameDescriptor) {
        requireNonNull(index);
        requireNonNull(editGameDescriptor);

        this.index = index;
        this.gameName = gameName;
        this.editGameDescriptor = editGameDescriptor;

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        assert model != null;
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEdit = lastShownList.get(index.getZeroBased());
        Map<String, Game> gameMap = personToEdit.getGames();
        gameToEdit = gameMap.get(gameName);
        if (gameToEdit == null) {
            throw new CommandException("That game doesn't exist for this user...");
        }

        Game editedGame = createEditedGame(gameToEdit, editGameDescriptor);
        gameMap.put(gameName, editedGame);
        model.setPerson(personToEdit, personToEdit);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.addCommandToLog(this);
        return new CommandResult(String.format(MESSAGE_EDIT_GAME_SUCCESS, Messages.format(editedGame)));
    }

    @Override
    public void undo(Model model) {
        requireNonNull(model);
        Map<String, Game> gameMap = personToEdit.getGames();
        gameMap.put(gameName, gameToEdit);
        model.setPerson(personToEdit, personToEdit);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    /**
     * Creates and returns a {@code Game} with the details of {@code gameToEdit}
     * edited with {@code editGameDescriptor}.
     */
    private static Game createEditedGame(Game gameToEdit, GameDescriptor editGameDescriptor) {
        assert gameToEdit != null;
        Username updatedUsername = editGameDescriptor.getUsername().orElse(gameToEdit.getUsername());
        SkillLevel updatedSkillLevel = editGameDescriptor.getSkillLevel().orElse(gameToEdit.getSkillLevel());
        Role updatedRole = editGameDescriptor.getRole().orElse(gameToEdit.getRole());
        boolean updatedFavouriteStatus = gameToEdit.getFavouriteStatus();
        return new Game(gameToEdit.gameName, updatedUsername, updatedSkillLevel, updatedRole, updatedFavouriteStatus);
    }
}
