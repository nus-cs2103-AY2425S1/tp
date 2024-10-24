package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents an abstract command for adding an object of type {@code T} to the address book.
 */
public abstract class AddCommand<T> extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Add the entity identified by argument 'contact', 'company' or 'job'\n"
                    + "Parameters: [contact/job/company] ENTITY PARAMETERS \nExample: " + COMMAND_WORD + " contact ...";

    protected T toAdd;

    /**
     * Creates respective AddCommand for specified {@code toAdd}
     */
    public AddCommand(T toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("toAdd", toAdd).toString();
    }
}
