package seedu.address.logic.commands;

/**
 * Updates the filtered list or display based on the matching between entities,
 * such as contacts (persons) and jobs, depending on the specific screen command's logic.
 * This command is intended to filter and display relevant matches between different types of entities
 * (e.g., contacts matching job requirements or jobs matching contact qualifications).
 */
public abstract class ScreenCommand extends Command {

    public static final String COMMAND_WORD = "screen";
    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Filters and displays entities matching the specified criteria. "
                    + "\n\nParameters:\n- job <INDEX>: Screens and lists contacts that fit the specified job.\n"
                    + "\nExample:\n" + COMMAND_WORD + " job 1";
}
