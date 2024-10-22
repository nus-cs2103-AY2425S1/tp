package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONCERTS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.commons.Address;
import seedu.address.model.commons.Name;
import seedu.address.model.concert.Concert;
import seedu.address.model.concert.ConcertContact;
import seedu.address.model.concert.ConcertDate;

/**
 * Edits the details of an existing concert.
 */
public class EditConcertCommand extends Command {

    public static final String COMMAND_WORD = "editc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the concert indentified "
            + "by the index number used in the displayed concert list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME" + "] "
            + "[" + PREFIX_ADDRESS + "ADDRESS" + "] "
            + "[" + PREFIX_DATE + "DATE" + "]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Coachella "
            + PREFIX_ADDRESS + "1 Stadium drive "
            + PREFIX_DATE + "2024-10-11 2200";

    public static final String MESSAGE_EDIT_CONCERT_SUCCESS = "Edited Concert: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CONCERT = "This concert already exists in the address book";

    private final Index index;
    private final EditConcertDescriptor editConcertDescriptor;

    /**
     * @param index of the concert to edit.
     * @param editConcertDescriptor details to edit the concert with.
     */
    public EditConcertCommand(Index index, EditConcertDescriptor editConcertDescriptor) {
        requireAllNonNull(index, editConcertDescriptor);

        this.index = index;
        this.editConcertDescriptor = new EditConcertDescriptor(editConcertDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Concert> lastShownList = model.getFilteredConcertList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONCERT_DISPLAYED_INDEX);
        }

        Concert concertToEdit = lastShownList.get(index.getZeroBased());
        Concert editedConcert = createEditedConcert(concertToEdit, editConcertDescriptor);

        if (!concertToEdit.isSameConcert(editedConcert) && model.hasConcert(editedConcert)) {
            throw new CommandException(MESSAGE_DUPLICATE_CONCERT);
        }

        // replace all occurences of the concertToEdit with editedConcert
        // use the concertContact list instead of filtered list
        for (ConcertContact cc : model.getAddressBook().getConcertContactList()) {
            if (cc.getConcert().equals(concertToEdit)) {
                model.setConcertContact(cc, new ConcertContact(cc.getPerson(), editedConcert));
            }
        }

        model.setConcert(concertToEdit, editedConcert);
        model.updateFilteredConcertList(PREDICATE_SHOW_ALL_CONCERTS);
        return new CommandResult(String.format(MESSAGE_EDIT_CONCERT_SUCCESS, Messages.format(editedConcert)));
    }

    /**
     * Creates and returns a {@code Concert} with the details of {@code concertToEdit}
     * edited with the {@code editConcertDescriptor}
     * @param concertToEdit
     * @param editConcertDescriptor
     * @return
     */
    public static Concert createEditedConcert(Concert concertToEdit, EditConcertDescriptor editConcertDescriptor) {
        assert concertToEdit != null : "Concert to edit must not be null";

        Name updatedName = editConcertDescriptor.getName().orElse(concertToEdit.getName());
        Address updatAddress = editConcertDescriptor.getAddress().orElse(concertToEdit.getAddress());
        ConcertDate updatedDate = editConcertDescriptor.getDate().orElse(concertToEdit.getDate());

        return new Concert(updatedName, updatAddress, updatedDate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // handles null
        if (!(other instanceof EditConcertCommand)) {
            return false;
        }

        EditConcertCommand otherEditConcertCommand = (EditConcertCommand) other;
        return index.equals(otherEditConcertCommand.index)
                && editConcertDescriptor.equals(otherEditConcertCommand.editConcertDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editConcertDescriptor", editConcertDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the concert with. Each non-empty field value will replace the
     * corresponding field value of the concert.
     */
    public static class EditConcertDescriptor {
        private Name name;
        private Address address;
        private ConcertDate date;

        public EditConcertDescriptor() {}

        /**
         * Copy constructor.
         * @param toCopy The EditConcertDescriptor to be copied.
         */
        public EditConcertDescriptor(EditConcertDescriptor toCopy) {
            setName(toCopy.name);
            setAddress(toCopy.address);
            setDate(toCopy.date);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, address, date);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setDate(ConcertDate date) {
            this.date = date;
        }

        public Optional<ConcertDate> getDate() {
            return Optional.ofNullable(date);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditConcertDescriptor)) {
                return false;
            }

            EditConcertDescriptor otherEditConcertDescriptor = (EditConcertDescriptor) other;
            return Objects.equals(name, otherEditConcertDescriptor.name)
                    && Objects.equals(address, otherEditConcertDescriptor.address)
                    && Objects.equals(date, otherEditConcertDescriptor.date);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("address", address)
                    .add("date", date)
                    .toString();
        }
    }
}
