package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Venue;
import seedu.address.model.wedding.Wedding;

/**
 * Edits the details of an existing wedding.
 */
public class EditwCommand extends Command {

    public static final String COMMAND_WORD = "editw";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the wedding identified "
            + "by the index number used in the displayed wedding list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: w/INDEX "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_VENUE + "VENUE]\n"
            + "Example: " + COMMAND_WORD + " w/1 " + PREFIX_NAME + "Wedding2 " + PREFIX_DATE + "2024-11-01";

    public static final String MESSAGE_EDIT_WEDDING_SUCCESS = "Edited Wedding: %1$s";
    public static final String MESSAGE_INVALID_WEDDING_INDEX = "The wedding index provided is invalid.";

    private final Index index;
    private final EditWeddingDescriptor editWeddingDescriptor;

    /**
     * @param index of the wedding in the filtered wedding list to edit
     * @param editWeddingDescriptor details to edit the wedding with
     */
    public EditwCommand(Index index, EditWeddingDescriptor editWeddingDescriptor) {
        requireNonNull(index);
        requireNonNull(editWeddingDescriptor);

        this.index = index;
        this.editWeddingDescriptor = editWeddingDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Wedding> lastShownList = model.getFilteredWeddingList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_WEDDING_INDEX);
        }

        Wedding weddingToEdit = lastShownList.get(index.getZeroBased());
        Wedding editedWedding = createEditedWedding(weddingToEdit, editWeddingDescriptor);

        model.setWedding(weddingToEdit, editedWedding);
        return new CommandResult(String.format(MESSAGE_EDIT_WEDDING_SUCCESS, editedWedding));
    }

    /**
     * Creates and returns a {@code Wedding} with the details of {@code weddingToEdit}
     * edited with {@code editWeddingDescriptor}.
     */
    private static Wedding createEditedWedding(Wedding weddingToEdit, EditWeddingDescriptor descriptor) {
        assert weddingToEdit != null;

        Name updatedName = descriptor.getName().orElse(weddingToEdit.getName());
        Date updatedDate = descriptor.getDate().orElse(weddingToEdit.getDate());
        Venue updatedVenue = descriptor.getVenue().orElse(weddingToEdit.getVenue());

        return new Wedding(updatedName, weddingToEdit.getClient(), updatedDate, updatedVenue);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof EditwCommand
                && index.equals(((EditwCommand) other).index)
                && editWeddingDescriptor.equals(((EditwCommand) other).editWeddingDescriptor));
    }

    /**
     * Stores the details to edit the wedding with. Each non-empty field value will replace the
     * corresponding field value of the wedding.
     */
    public static class EditWeddingDescriptor {
        private Name name;
        private Date date;
        private Venue venue;

        public EditWeddingDescriptor() {}

        public boolean isAnyFieldEdited() {
            return name != null || date != null || venue != null;
        }

        /**
         * Sets the {@code Name} of the wedding.
         */
        public void setName(Name name) {
            this.name = name;
        }

        /**
         * Returns an Optional containing the wedding's {@code Name} if it was set.
         */
        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        /**
         * Sets the {@code Date} of the wedding.
         */
        public void setDate(Date date) {
            this.date = date;
        }

        /**
         * Returns an Optional containing the wedding's {@code Date} if it was set.
         */
        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        /**
         * Sets the {@code Venue} of the wedding.
         */
        public void setVenue(Venue venue) {
            this.venue = venue;
        }

        /**
         * Returns an Optional containing the wedding's {@code Venue} if it was set.
         */
        public Optional<Venue> getVenue() {
            return Optional.ofNullable(venue);
        }

        @Override
        public boolean equals(Object other) {
            return other == this
                    || (other instanceof EditWeddingDescriptor
                    && getName().equals(((EditWeddingDescriptor) other).getName())
                    && getDate().equals(((EditWeddingDescriptor) other).getDate())
                    && getVenue().equals(((EditWeddingDescriptor) other).getVenue()));
        }
    }
}
