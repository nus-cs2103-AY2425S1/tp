package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_GAME;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.GameDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for
 * {@code UndoCommand}.
 */
public class UndoCommandTest {

    private Model model;
    private String sampleUserInput;
    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_emptyCommandLog_throwsCommandException() {
        UndoCommand undoCommand = new UndoCommand();

        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_INVALID_PREVIOUS_COMMAND);
    }

    @Test
    public void execute_addCommand_success() throws CommandException {
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        sampleUserInput = "add test";

        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        UndoCommand undoCommand = new UndoCommand();

        LogicStub logicStub = new LogicStub(model, addCommand);
        logicStub.execute(sampleUserInput);
        String expectedMessage = String.format(UndoCommand.MESSAGE_SUCCESS, sampleUserInput);

        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addGameCommand_success() throws CommandException {
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        sampleUserInput = "addgame test";

        AddGameCommand.GameDescriptor descriptor = new GameDescriptorBuilder().withGame(VALID_GAME).build();
        AddGameCommand addCommand = new AddGameCommand(INDEX_FIRST_PERSON, VALID_GAME, descriptor);
        UndoCommand undoCommand = new UndoCommand();

        LogicStub logicStub = new LogicStub(model, addCommand);
        logicStub.execute(sampleUserInput);
        String expectedMessage = String.format(UndoCommand.MESSAGE_SUCCESS, sampleUserInput);

        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_clearCommand_success() throws CommandException {
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        sampleUserInput = "clear test";

        ClearCommand clearCommand = new ClearCommand();
        UndoCommand undoCommand = new UndoCommand();

        LogicStub logicStub = new LogicStub(model, clearCommand);
        logicStub.execute(sampleUserInput);
        String expectedMessage = String.format(UndoCommand.MESSAGE_SUCCESS, sampleUserInput);

        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteCommand_success() throws CommandException {
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        sampleUserInput = "delete test";

        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        UndoCommand undoCommand = new UndoCommand();

        LogicStub logicStub = new LogicStub(model, deleteCommand);
        logicStub.execute(sampleUserInput);
        String expectedMessage = String.format(UndoCommand.MESSAGE_SUCCESS, sampleUserInput);

        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_notUndoableCommand_throwsCommandException() throws CommandException {
        Command[] notUndoableCommands = {new HelpCommand(), new SaveCommand(), new LoadCommand()};
        sampleUserInput = "notUndoableCommand test";

        for (int i = 0; i < 3; i++) {
            UndoCommand undoCommand = new UndoCommand();
            LogicStub logicStub = new LogicStub(model, notUndoableCommands[i]);
            logicStub.execute(sampleUserInput);

            assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_INVALID_PREVIOUS_COMMAND);
        }
    }

    /**
     * A Logic stub meant to only execute one command that has all methods other than {@code execute} failing.
     *
     * The constructor stores the {@code Command} directly.
     * Arguments for {@code execute()} is not used.
     */
    private class LogicStub implements Logic {
        private final Model model;
        private final Command command;

        public LogicStub(Model model, Command command) {
            this.model = model;
            this.command = command;
        }

        @Override
        public CommandResult execute(String commandText) throws CommandException {
            CommandResult commandResult = command.execute(model);
            if (command.canBeUndone()) {
                model.addInputToLog(commandText);
                model.addCommandToLog(command);
            }

            return commandResult;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

    }
}
