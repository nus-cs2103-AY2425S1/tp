package seedu.address.logic.commands;

public abstract class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

}
