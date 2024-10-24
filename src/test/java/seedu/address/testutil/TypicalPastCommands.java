package seedu.address.testutil;

import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
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

    public static final ArrayList<Command> EMPTY_COMMAND_LAST =
            new ArrayList<>();
    public static final ArrayList<Command> CLEAR_COMMAND_LAST =
            new ArrayList<>(Arrays.asList(EXAMPLE_ADD, EXAMPLE_EDIT, EXAMPLE_DELETE, EXAMPLE_CLEAR));

    public static final ArrayList<Command> ADD_COMMAND_LAST =
            new ArrayList<>(Arrays.asList(EXAMPLE_DELETE, EXAMPLE_EDIT, EXAMPLE_CLEAR, EXAMPLE_ADD));

    public static final ArrayList<Command> DELETE_COMMAND_LAST =
            new ArrayList<>(Arrays.asList(EXAMPLE_CLEAR, EXAMPLE_EDIT, EXAMPLE_ADD, EXAMPLE_DELETE));

    public static final ArrayList<Command> EDIT_COMMAND_LAST =
            new ArrayList<>(Arrays.asList(EXAMPLE_CLEAR, EXAMPLE_ADD, EXAMPLE_DELETE, EXAMPLE_EDIT));

    public static EditPersonDescriptor settingName(Name newName, EditPersonDescriptor editPersonDescriptor) {
        editPersonDescriptor.setName(newName);
        return editPersonDescriptor;
    }
}
