package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BREED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;

/**
 * Edits the details of an existing entity in PawPatrol.
 */
public abstract class EditCommand<T> extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the owner or pet identified "
        + "by the index number used in the displayed owner or pet list. "
        + "Existing values will be overwritten by the input values.\n"
        + "To edit owners: edit o[INDEX] (must be a positive integer) KEYWORD [MORE_KEYWORDS]...\n"
        + "To edit pets: edit p[INDEX] (must be a positive integer) KEYWORD [MORE_KEYWORDS]...\n"
        + "Example: edit o1 n/bobby, edit p1 n/tom\n"
        + "Allowed field tags for owners: "
        + String.join(", ", Arrays.stream(new Prefix[]{PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS})
          .map(Prefix::toString).toArray(String[]::new)) + "\n"
        + "Allowed field tags for pets: "
        + String.join(", ", Arrays.stream(new Prefix[]{PREFIX_NAME, PREFIX_SPECIES, PREFIX_BREED, PREFIX_AGE,
            PREFIX_SEX, PREFIX_TAG}).map(Prefix::toString).toArray(String[]::new)) + "\n";

    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    protected final Index index;

    /**
     * @param index of the entity in the filtered entity list to edit
     */
    public EditCommand(Index index) {
        requireNonNull(index);

        this.index = index;
    }

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand<?> otherEditCommand = (EditCommand<?>) other;
        return index.equals(otherEditCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("index", index)
            .toString();
    }
}
