package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.person.Name;

/**
 * Views an existing person in the address book.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View all persons whose names are exactly "
            + "the specified name (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: NAME\n"
            + "Example: " + COMMAND_WORD + " Alice Yeoh";

    private final Name name;

    /**
     * Creates a ViewCommand to view people with the specified {@code Name}.
     * @param name Name of person to view.
     */
    public ViewCommand(Name name) {
        requireNonNull(name);
        this.name = name;
    }
    
    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("Viewing " + name);
    }
}
