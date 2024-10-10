package seedu.address.logic.commands;

/**
 * Adds a person to the address book.
 */
public abstract class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Add the entity identified by argument 'contact', 'company' or 'job'\n"
                    + "Parameters: [contact/job/company] ENTITY PARAMETERS \nExample: " + COMMAND_WORD + " contact ...";

}
