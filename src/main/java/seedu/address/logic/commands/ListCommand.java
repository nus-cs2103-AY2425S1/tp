package seedu.address.logic.commands;

/**
 * Lists all persons in the address book to the user.
 */
public abstract class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": List the entity identified by argument 'contact', 'company' or 'job' or show all 'all'\n"
            + "Parameters: [contact/job/company/all]\nExample: " + COMMAND_WORD + " contact";

}
