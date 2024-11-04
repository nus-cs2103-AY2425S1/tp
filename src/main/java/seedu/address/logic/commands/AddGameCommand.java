package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILLLEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
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
public class AddGameCommand extends Command {

    public static final String COMMAND_WORD = "addgame";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a game to the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_GAME + "GAME] "
            + "[" + PREFIX_USERNAME + "USERNAME] "
            + "[" + PREFIX_ROLE + "ROLE] "
            + "[" + PREFIX_SKILLLEVEL + "SKILL LEVEL]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_GAME + "Brawl Stars "
            + PREFIX_USERNAME + "johndoe420";

    public static final String MESSAGE_ADD_GAME_SUCCESS = "Added Game to Person: %1$s";
    public static final String MESSAGE_GAME_EXISTS = "The game provided already exists for that person.";

    private final Index index;
    private final GameDescriptor addGameDescriptor;
    private final String gameName;

    private Person personToEdit;
    private Person editedPerson;

    /**
     * @param index of the person in the filtered person list to edit
     * @param addGameDescriptor details to edit the person with
     */
    public AddGameCommand(Index index, String gameName, GameDescriptor addGameDescriptor) {
        requireNonNull(index);
        requireNonNull(addGameDescriptor);

        this.index = index;
        this.gameName = gameName;
        this.addGameDescriptor = new GameDescriptor(addGameDescriptor);
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

        if (gameMap.containsKey(gameName)) {
            throw new CommandException(MESSAGE_GAME_EXISTS);
        }

        Game editedGame = createNewGame(gameName, addGameDescriptor);
        gameMap.put(gameName, editedGame);
        model.setPerson(personToEdit, personToEdit);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.addCommandToLog(this);
        return new CommandResult(String.format(MESSAGE_ADD_GAME_SUCCESS, Messages.format(editedGame)));
    }

    @Override
    public void undo(Model model) {
        requireNonNull(model);

        Map<String, Game> gameMap = personToEdit.getGames();
        gameMap.remove(gameName);

        model.setPerson(personToEdit, personToEdit);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    /**
     * Creates and returns a {@code Game} with the details of {@code addGameDescriptor}
     */
    private static Game createNewGame(String gameName, AddGameCommand.GameDescriptor addGameDescriptor) {
        Username updatedUsername = addGameDescriptor.getUsername().orElse(new Username(""));
        SkillLevel updatedSkillLevel = addGameDescriptor.getSkillLevel().orElse(new SkillLevel(""));
        Role updatedRole = addGameDescriptor.getRole().orElse(new Role(""));
        return new Game(gameName, updatedUsername, updatedSkillLevel, updatedRole, false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddGameCommand)) {
            return false;
        }

        AddGameCommand otherEditCommand = (AddGameCommand) other;
        return index.equals(otherEditCommand.index)
                && addGameDescriptor.equals(otherEditCommand.addGameDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("addGameDescriptor", addGameDescriptor)
                .toString();
    }


    /**
     * Stores the details for the game to be added.
     */
    public static class GameDescriptor {
        private String game;
        private Username username;
        private SkillLevel skillLevel;
        private Role role;
        private boolean isFavourite;

        public GameDescriptor() {}

        /**
         * Copy constructor.
         */
        public GameDescriptor(AddGameCommand.GameDescriptor toCopy) {
            setGame(toCopy.game);
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
        public void setGame(String game) {
            this.game = game;
        }

        public Optional<String> getGame() {
            return Optional.ofNullable(game);
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
            if (!(other instanceof AddGameCommand.GameDescriptor)) {
                return false;
            }

            AddGameCommand.GameDescriptor otherGameDescriptor = (AddGameCommand.GameDescriptor) other;
            return Objects.equals(username, otherGameDescriptor.username)
                    && Objects.equals(skillLevel, otherGameDescriptor.skillLevel)
                    && Objects.equals(role, otherGameDescriptor.role);
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
