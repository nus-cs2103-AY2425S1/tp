package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.game.Game;
import seedu.address.model.game.Role;
import seedu.address.model.game.SkillLevel;
import seedu.address.model.game.Username;
import seedu.address.model.person.Person;

/**
 * Edits the details of an existing person in the address book.
 */
public class DeleteGameCommand extends Command {

    public static final String COMMAND_WORD = "deletegame";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a game from the person identified "
            + "by the index number used in the displayed person list.\n";

    public static final String MESSAGE_DELETE_GAME_SUCCESS = "Deleted Game from Person: %1$s";
    public static final String MESSAGE_NOT_DELETED = "The game provided does not exist for that person.";

    private final Index index;
    private final String gameName;
    private Person personToEdit;
    private Game deletedGame;

    /**
     * @param index of the person in the filtered person list to edit
     * @param gameName of the game to be deleted
     */
    public DeleteGameCommand(Index index, String gameName) {
        requireNonNull(index);
        assert gameName != null;

        this.index = index;
        this.gameName = gameName;
        this.deletedGame = null;
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

        if (!gameMap.containsKey(gameName)) {
            throw new CommandException(MESSAGE_NOT_DELETED);
        }

        deletedGame = gameMap.remove(gameName);
        model.setPerson(personToEdit, personToEdit);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.addCommandToLog(this);
        return new CommandResult(String.format(MESSAGE_DELETE_GAME_SUCCESS, Messages.format(deletedGame)));
    }

    @Override
    public void undo(Model model) {
        requireNonNull(model);

        Map<String, Game> gameMap = personToEdit.getGames();
        gameMap.put(gameName, deletedGame);

        model.setPerson(personToEdit, personToEdit);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteGameCommand)) {
            return false;
        }

        DeleteGameCommand otherDeleteCommand = (DeleteGameCommand) other;
        return index.equals(otherDeleteCommand.index)
                && gameName.equals(otherDeleteCommand.gameName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("gameName", gameName)
                .toString();
    }


    /**
     * Stores the details for the game to be added.
     */
    public static class AddGameDescriptor {
        private Username username;
        private SkillLevel skillLevel;
        private Role role;
        private boolean isFavourite;

        public AddGameDescriptor() {}

        /**
         * Copy constructor.
         */
        public AddGameDescriptor(DeleteGameCommand.AddGameDescriptor toCopy) {
            setUsername(toCopy.username);
            setSkillLevel(toCopy.skillLevel);
            setRole(toCopy.role);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(username, skillLevel, role);
        }
        public void setUsername(Username username) {
            this.username = username;
        }

        public Optional<Username> getUsername() {
            return Optional.ofNullable(username);
        }

        public void setSkillLevel(SkillLevel skillLevel) {
            this.skillLevel = skillLevel;
        }

        public Optional<SkillLevel> getSkillLevel() {
            return Optional.ofNullable(skillLevel);
        }

        public void setRole(Role role) {
            this.role = role;
        }

        public Optional<Role> getRole() {
            return Optional.ofNullable(role);
        }

        public boolean getFavouriteStatus() {
            return this.isFavourite;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof DeleteGameCommand.AddGameDescriptor)) {
                return false;
            }

            DeleteGameCommand.AddGameDescriptor otherAddGameDescriptor = (DeleteGameCommand.AddGameDescriptor) other;
            return Objects.equals(username, otherAddGameDescriptor.username)
                    && Objects.equals(skillLevel, otherAddGameDescriptor.skillLevel)
                    && Objects.equals(role, otherAddGameDescriptor.role);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("username", username)
                    .add("skillLevel", skillLevel)
                    .add("role", role)
                    .toString();
        }
    }
}
