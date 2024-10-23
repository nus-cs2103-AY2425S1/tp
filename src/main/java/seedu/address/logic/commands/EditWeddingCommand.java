package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingDate;
import seedu.address.model.wedding.WeddingName;

/**
 * Edits a wedding in the address book.
 */
public class EditWeddingCommand extends Command {

    public static final String COMMAND_WORD = "editw";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": edits wedding \n"
            + "Parameters: INDEX (must be a positive integer) " + PREFIX_NAME + "NAME "
            + PREFIX_DATE + "DATE\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_NAME + "Pb&J " + PREFIX_DATE + "21/03/2024";

    public static final String MESSAGE_SUCCESS = "Edited Wedding: %1$s \n";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_WEDDING = "This wedding already exists in the address book.";

    private final Index index;
    private final EditWeddingDescriptor editWeddingDescriptor;

    /**
     * Constructor.
     * @param index
     * @param editWeddingDescriptor
     */
    public EditWeddingCommand(Index index, EditWeddingDescriptor editWeddingDescriptor) {
        requireNonNull(index);
        requireNonNull(editWeddingDescriptor);

        assert index.getZeroBased() >= 0 : "Index should not be negative";

        this.index = index;
        this.editWeddingDescriptor = new EditWeddingDescriptor(editWeddingDescriptor);
    }

    /**
     * Executes edit wedding command
     * @param model {@code Model} which the command should operate on.
     * @return CommandResult
     * @throws CommandException with invalid input
     */
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(index);
        List<Wedding> lastShownList = model.getFilteredWeddingList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_WEDDING_DISPLAYED_INDEX);
        }

        Wedding weddingToEdit = lastShownList.get(index.getZeroBased());
        assert weddingToEdit != null : "Wedding to edit should not be null";

        Wedding editedWedding = createEditedWedding(weddingToEdit, editWeddingDescriptor);

        if (!weddingToEdit.isSameWedding(editedWedding) && model.hasWedding(editedWedding)) {
            throw new CommandException(MESSAGE_DUPLICATE_WEDDING);
        }

        model.setWedding(weddingToEdit, editedWedding);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.formatWedding(editedWedding)));


    }

    private static Wedding createEditedWedding(Wedding weddingToEdit,
                                            EditWeddingDescriptor editWeddingDescriptor) {
        assert weddingToEdit != null;

        WeddingName updatedWeddingName = editWeddingDescriptor.getWeddingName().orElse(weddingToEdit.getWeddingName());
        WeddingDate updatedWeddingDate = editWeddingDescriptor.getWeddingDate().orElse(weddingToEdit.getWeddingDate());
        assert updatedWeddingName != null : "Updated name should not be null";
        assert updatedWeddingDate != null : "Updated phone should not be null";

        return new Wedding(updatedWeddingName, updatedWeddingDate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditWeddingCommand)) {
            return false;
        }

        EditWeddingCommand otherEditWeddingCommand = (EditWeddingCommand) other;
        return index.equals(otherEditWeddingCommand.index)
                && editWeddingDescriptor.equals(otherEditWeddingCommand.editWeddingDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editWeddingDescriptor", editWeddingDescriptor)
                .toString();
    }


    /**
     * Stores the details to edit the wedding with. Each non-empty field value will replace the
     * corresponding field value of the wedding.
     */
    public static class EditWeddingDescriptor {
        private WeddingName weddingName;
        private WeddingDate weddingDate;

        public EditWeddingDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditWeddingDescriptor(EditWeddingDescriptor toCopy) {
            setWeddingName(toCopy.weddingName);
            setWeddingDate(toCopy.weddingDate);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(weddingName, weddingDate);
        }

        public void setWeddingName(WeddingName weddingName) {
            this.weddingName = weddingName;
        }

        public Optional<WeddingName> getWeddingName() {
            return Optional.ofNullable(weddingName);
        }

        public void setWeddingDate(WeddingDate weddingDate) {
            this.weddingDate = weddingDate;
        }

        public Optional<WeddingDate> getWeddingDate() {
            return Optional.ofNullable(weddingDate);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditWeddingDescriptor)) {
                return false;
            }

            EditWeddingDescriptor otherEditWeddingDescriptor = (EditWeddingDescriptor) other;
            return Objects.equals(weddingName, otherEditWeddingDescriptor.weddingName)
                    && Objects.equals(weddingDate, otherEditWeddingDescriptor.weddingDate);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("weddingName", weddingName)
                    .add("weddingDate", weddingDate)
                    .toString();
        }
    }


}
