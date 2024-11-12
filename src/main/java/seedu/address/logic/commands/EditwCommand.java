package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Venue;
import seedu.address.model.wedding.Wedding;

/**
 * Edits the details of an existing wedding in wedding list. Details that can be edited are name, date and venue.
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
    public static final String MESSAGE_INVALID_WEDDING_INDEX = "The wedding index provided is invalid, please "
            + "enter an index that is between 1 and %1$d";

    public static final String MESSAGE_EDIT_EMPTY_LIST_ERROR = "There is nothing to edit.";

    private final Index index;
    private final EditWeddingDescriptor editWeddingDescriptor;

    /**
     * Creates a {@code EditwCommand} object to edit the wedding at the specified {@code Index}.
     *
     * @param index {@code Index} of the wedding in the filtered wedding list to edit.
     * @param editWeddingDescriptor details of what is to be edited in the wedding.
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

        if (!editWeddingDescriptor.isAnyFieldEdited()) {
            throw new CommandException("No fields specified to edit.");
        }

        List<Wedding> lastShownList = model.getFilteredWeddingList();

        if (lastShownList.isEmpty()) {
            throw new CommandException(MESSAGE_EDIT_EMPTY_LIST_ERROR);
        }

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_WEDDING_INDEX,
                    lastShownList.size()));
        }

        Wedding weddingToEdit = lastShownList.get(index.getZeroBased());
        Wedding editedWedding = createEditedWedding(weddingToEdit, editWeddingDescriptor);
        model.updatePersonEditedWedding(weddingToEdit, editedWedding);


        model.setWedding(weddingToEdit, editedWedding);
        return new CommandResult(String.format(MESSAGE_EDIT_WEDDING_SUCCESS, Messages.format(editedWedding)));
    }

    /**
     * Returns a {@code Wedding} with updated name, date or venue.
     *
     * @param weddingToEdit the target wedding.
     * @param descriptor details of what is to be edited in the wedding.
     * @return updated {@code Wedding} with the edited information.
     */
    static Wedding createEditedWedding(Wedding weddingToEdit, EditWeddingDescriptor descriptor) {
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

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        public void setVenue(Venue venue) {
            this.venue = venue;
        }

        public Optional<Venue> getVenue() {
            return Optional.ofNullable(venue);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditWeddingDescriptor)) {
                return false;
            }

            EditWeddingDescriptor e = (EditWeddingDescriptor) other;
            return getName().equals(e.getName())
                    && getDate().equals(e.getDate())
                    && getVenue().equals(e.getVenue());
        }
    }
}
