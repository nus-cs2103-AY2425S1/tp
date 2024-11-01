package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GAME;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.game.Game;
import seedu.address.model.person.Person;

/**
 * Represents a command to set a game to "favourite" status.
 */
public class FavouriteGameCommand extends Command {
    public static final String COMMAND_WORD = "favgame";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets a game to \"favourite\" "
            + "under the person specified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_GAME + "GAMENAME] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_GAME + "Valorant ";

    public static final String MESSAGE_FAVOURITE_GAME_SUCCESS = "Favourited Game: %1$s";
    public static final String MESSAGE_GAME_NOT_SPECIFIED = "Please specify a game!";
    public static final String MESSAGE_GAME_NOT_FOUND = "Game not found!";

    private Index index;
    private String gameName;
    private boolean prevGameIsFavourite;

    /**
     * @param index of the person in the filtered person list to edit
     * @param gameName name of the game to be favourited
     */
    public FavouriteGameCommand(Index index, String gameName) {
        this.index = index;
        this.gameName = gameName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (gameName.isEmpty()) {
            throw new CommandException(MESSAGE_GAME_NOT_SPECIFIED);
        }

        Person targetPerson = lastShownList.get(index.getZeroBased());
        Game targetGame = targetPerson.getGames().get(gameName);

        if (targetGame == null) {
            throw new CommandException(MESSAGE_GAME_NOT_FOUND);
        }
        prevGameIsFavourite = targetGame.getFavouriteStatus();
        targetGame.setAsFavourite();
        model.setPerson(targetPerson, targetPerson);
        model.addCommandToLog(this);
        return new CommandResult(String.format(MESSAGE_FAVOURITE_GAME_SUCCESS, gameName));
    }

    @Override
    public void undo(Model model) {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        assert index.getZeroBased() < lastShownList.size() : "Index should still be within bounds when undoing";
        assert !gameName.isEmpty() : "Game name should not be empty when undoing";

        Person targetPerson = lastShownList.get(index.getZeroBased());
        Game targetGame = targetPerson.getGames().get(gameName);

        assert targetGame != null : "Game should be present when undoing";

        if (!prevGameIsFavourite) {
            targetGame.removeFavourite();
        }

        model.setPerson(targetPerson, targetPerson);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FavouriteGameCommand)) {
            return false;
        }

        FavouriteGameCommand e = (FavouriteGameCommand) other;
        return index.equals(e.index) && gameName.equals(e.gameName);
    }
}
