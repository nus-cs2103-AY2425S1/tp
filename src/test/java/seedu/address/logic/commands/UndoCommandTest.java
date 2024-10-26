package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.UndoCommand.MESSAGE_UNDO_ADD;
import static seedu.address.logic.commands.UndoCommand.MESSAGE_UNDO_ADD_APPOINTMENT;
import static seedu.address.logic.commands.UndoCommand.MESSAGE_UNDO_CLEAR;
import static seedu.address.logic.commands.UndoCommand.MESSAGE_UNDO_COMMAND_SUCCESS;
import static seedu.address.logic.commands.UndoCommand.MESSAGE_UNDO_DELETE;
import static seedu.address.logic.commands.UndoCommand.MESSAGE_UNDO_DELETE_APPOINTMENT;
import static seedu.address.logic.commands.UndoCommand.MESSAGE_UNDO_EDIT;
import static seedu.address.testutil.TypicalPastCommands.ADDA_COMMAND_LAST;
import static seedu.address.testutil.TypicalPastCommands.ADD_COMMAND_LAST;
import static seedu.address.testutil.TypicalPastCommands.CLEAR_COMMAND_LAST;
import static seedu.address.testutil.TypicalPastCommands.DELETEA_COMMAND_LAST;
import static seedu.address.testutil.TypicalPastCommands.DELETE_COMMAND_LAST;
import static seedu.address.testutil.TypicalPastCommands.EDIT_COMMAND_LAST;
import static seedu.address.testutil.TypicalPastCommands.EMPTY_COMMAND_LAST;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook2;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.testutil.TypicalPersons;

public class UndoCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook2(), new ArrayList<>(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new ArrayList<>(), new UserPrefs());
    }

    @Test
    public void execute_undoClearCommand() throws CommandException {
        CLEAR_COMMAND_LAST.getCommandHistory().get(CLEAR_COMMAND_LAST.getSize() - 1).execute(model);

        UndoCommand undoCommand = new UndoCommand(CLEAR_COMMAND_LAST);
        String expectedMessage = String.format(MESSAGE_UNDO_COMMAND_SUCCESS, MESSAGE_UNDO_CLEAR);

        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_undoAddCommand() throws CommandException {
        ADD_COMMAND_LAST.getCommandHistory().get(ADD_COMMAND_LAST.getSize() - 1).execute(model);

        UndoCommand undoCommand = new UndoCommand(ADD_COMMAND_LAST);

        String expectedMessage = String.format(MESSAGE_UNDO_COMMAND_SUCCESS,
                String.format(MESSAGE_UNDO_ADD, TypicalPersons.HOON.getName()));

        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_undoDeleteCommand() throws CommandException {
        DELETE_COMMAND_LAST.getCommandHistory().get(DELETE_COMMAND_LAST.getSize() - 1).execute(model);
        for (int i = 0; i < DELETE_COMMAND_LAST.getSize(); i++) {
        }
        UndoCommand undoCommand = new UndoCommand(DELETE_COMMAND_LAST);

        String expectedMessage = String.format(MESSAGE_UNDO_COMMAND_SUCCESS,
                String.format(MESSAGE_UNDO_DELETE, TypicalPersons.BENSON.getName()));
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_undoEditCommand() throws CommandException {
        EDIT_COMMAND_LAST.getCommandHistory().get(EDIT_COMMAND_LAST.getSize() - 1).execute(model);

        UndoCommand undoCommand = new UndoCommand(EDIT_COMMAND_LAST);

        String expectedMessage = String.format(MESSAGE_UNDO_COMMAND_SUCCESS,
                String.format(MESSAGE_UNDO_EDIT, TypicalPersons.BENSON.getName()));
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel);

    }
    @Test
    public void execute_undoAddAppointmentCommand() throws CommandException {
        ADDA_COMMAND_LAST.getCommandHistory().get(ADDA_COMMAND_LAST.getSize() - 1).execute(model);

        UndoCommand undoCommand = new UndoCommand(ADDA_COMMAND_LAST);

        String expectedMessage = String.format(MESSAGE_UNDO_COMMAND_SUCCESS,
                String.format(MESSAGE_UNDO_ADD_APPOINTMENT, model.getFilteredAppointmentList().get(0)));
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_undoDeleteAppointmentCommand() throws CommandException {
        Appointment testAppointment = new Appointment(TypicalPersons.BENSON.getName(),
                LocalDate.of(2024, 12, 31),
                LocalTime.of(14, 30), LocalTime.of(16, 30));
        List<Appointment> temp = new ArrayList<>();
        temp.add(testAppointment);
        model = new ModelManager(getTypicalAddressBook2(), temp, new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), temp, new UserPrefs());
        DELETEA_COMMAND_LAST.getCommandHistory().get(DELETEA_COMMAND_LAST.getSize() - 1).execute(model);
        UndoCommand undoCommand = new UndoCommand(DELETEA_COMMAND_LAST);

        String expectedMessage = String.format(MESSAGE_UNDO_COMMAND_SUCCESS,
                String.format(MESSAGE_UNDO_DELETE_APPOINTMENT, testAppointment));
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_noPastCommand() {
        UndoCommand undoCommand = new UndoCommand(EMPTY_COMMAND_LAST);

        assertCommandFailure(undoCommand, model, Messages.MESSAGE_NO_LATEST_COMMAND);
    }

    @Test
    public void equals() {
        UndoCommand undoFirstCommand = new UndoCommand(ADD_COMMAND_LAST);
        UndoCommand undoSecondCommand = new UndoCommand(DELETE_COMMAND_LAST);

        // same object -> returns true
        assertTrue(undoFirstCommand.equals(undoFirstCommand));

        // same values -> returns true
        UndoCommand undoFirstCommandCopy = new UndoCommand(ADD_COMMAND_LAST);
        assertTrue(undoFirstCommand.equals(undoFirstCommandCopy));

        // different types -> returns false
        assertFalse(undoFirstCommand.equals(1));

        // null -> returns false
        assertFalse(undoFirstCommand.equals(null));

        // different past commands -> returns false
        assertFalse(undoFirstCommand.equals(undoSecondCommand));
    }
}
