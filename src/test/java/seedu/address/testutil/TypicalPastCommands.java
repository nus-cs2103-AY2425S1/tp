package seedu.address.testutil;

import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDate;
import java.time.LocalTime;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteAppointmentCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Name;


/**
 * A utility class containing a list of past commands to be used in tests.
 */
public class TypicalPastCommands {
    public static final AddCommand EXAMPLE_ADD = new AddCommand(TypicalPersons.HOON);
    public static final DeleteCommand EXAMPLE_DELETE = new DeleteCommand(TypicalIndexes.INDEX_FIRST_MULTIPLE);
    public static final ClearCommand EXAMPLE_CLEAR = new ClearCommand();
    public static final EditPersonDescriptor EDIT_PERSON_DESCRIPTOR = new EditPersonDescriptor();
    public static final Name NEW_NAME = new Name("Amy");

    public static final EditCommand EXAMPLE_EDIT = new EditCommand(INDEX_FIRST_PERSON,
            settingName(NEW_NAME, EDIT_PERSON_DESCRIPTOR));

    public static final AddAppointmentCommand EXAMPLE_ADDA = new AddAppointmentCommand(Index.fromZeroBased(0),
            LocalDate.of(2024, 12, 31),
            LocalTime.of(14, 30), LocalTime.of(16, 30));

    public static final DeleteAppointmentCommand EXAMPLE_DELETEA =
            new DeleteAppointmentCommand(Index.fromZeroBased(0));

    public static final CommandHistory EMPTY_COMMAND_LAST = createPastCommands();

    public static final CommandHistory CLEAR_COMMAND_LAST =
            createPastCommands(EXAMPLE_DELETE, EXAMPLE_EDIT, EXAMPLE_ADD, EXAMPLE_CLEAR);

    public static final CommandHistory ADD_COMMAND_LAST =
            createPastCommands(EXAMPLE_DELETE, EXAMPLE_EDIT, EXAMPLE_CLEAR, EXAMPLE_ADD);

    public static final CommandHistory DELETE_COMMAND_LAST =
            createPastCommands(EXAMPLE_CLEAR, EXAMPLE_EDIT, EXAMPLE_ADD, EXAMPLE_DELETE);

    public static final CommandHistory EDIT_COMMAND_LAST =
            createPastCommands(EXAMPLE_CLEAR, EXAMPLE_ADD, EXAMPLE_DELETE, EXAMPLE_EDIT);

    public static final CommandHistory ADDA_COMMAND_LAST =
            createPastCommands(EXAMPLE_CLEAR, EXAMPLE_ADD, EXAMPLE_DELETE, EXAMPLE_ADDA);

    public static final CommandHistory DELETEA_COMMAND_LAST =
            createPastCommands(EXAMPLE_CLEAR, EXAMPLE_ADD, EXAMPLE_DELETE, EXAMPLE_DELETEA);


    private static CommandHistory CURR_PAST_COMMANDS = new CommandHistory();
    /**
     * Helper function to create CommandHistory with specified past commands.
     *
     * @param pastCommands specified past commands to have.
     * @return CommandHistory with past commands.
     */
    public static CommandHistory createPastCommands(Command... pastCommands) {
        CURR_PAST_COMMANDS = new CommandHistory();
        for (int i = 0; i < pastCommands.length; i++) {
            CURR_PAST_COMMANDS.add(pastCommands[i]);
        }
        return CURR_PAST_COMMANDS;

    }

    public static EditPersonDescriptor settingName(Name newName, EditPersonDescriptor editPersonDescriptor) {
        editPersonDescriptor.setName(newName);
        return editPersonDescriptor;
    }
}
