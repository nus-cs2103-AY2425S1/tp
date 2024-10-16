package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.game.Game;
import seedu.address.model.game.Username;

/**
 * Represents a command to add a username to the specified game of the contact.
 */
public class UsernameCommand extends Command {
    public static final String COMMAND_WORD = "user";

    private final Index index;
    private final Game targetGame;
    private final Username username;

    /**
     * creates a username command with all fields set to null.
     */
    public UsernameCommand() {
        this.index = null;
        this.targetGame = null;
        this.username = null;
    }

    /**
     * Creates a username command with the fields specified.
     * @param index the index of the person listed in the address book
     * @param game the game to associate the username with
     * @param username the username to be added
     */
    public UsernameCommand(Index index, Game game, Username username) {
        this.index = index;
        this.targetGame = game;
        this.username = username;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException("This command is supposed to generate a username");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UsernameCommand)) {
            return false;
        }

        UsernameCommand e = (UsernameCommand) other;
        return this.username.equals(e.username) && this.index.equals(e.index);
    }
}
