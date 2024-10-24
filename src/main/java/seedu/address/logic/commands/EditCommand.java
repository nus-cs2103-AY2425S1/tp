package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENDOR;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Parent abstract class for edit commands.
 * Contains the index of the target to be edited.
 */
public abstract class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the vendor or event identified "
            + "by the index number used in the displayed list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: " + PREFIX_VENDOR + "INDEX" + " <other vendor parameters> or "
            + PREFIX_EVENT + "INDEX (INDEX must be a positive integer)" + " <other event parameters>" + "\n"
            + "Example to edit a vendor: " + COMMAND_WORD + " " + PREFIX_VENDOR + "1 "
            + PREFIX_NAME + "Adam's Bakery "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_DESCRIPTION + "Pastries and cakes, bake in a day "
            + PREFIX_TAG + "pastry "
            + PREFIX_TAG + "fast" + "\n"
            + "Example to edit an event: " + COMMAND_WORD + " " + PREFIX_EVENT + "1 "
            + PREFIX_NAME + "John Baby Shower" + " "
            + PREFIX_DATE + "2021-10-10";

    public static final String MESSAGE_NOT_EDITED = "At least one valid field to edit must be provided.";

    protected final Index index;

    /**
     * @param targetIndex in the filtered list to edit
     */
    public EditCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.index = targetIndex;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", index)
                .toString();
    }
}
