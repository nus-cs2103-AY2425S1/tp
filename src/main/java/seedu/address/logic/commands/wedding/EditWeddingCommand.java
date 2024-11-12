package seedu.address.logic.commands.wedding;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_WEDDINGS;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;


/**
 * Edits the details of an existing wedding in the address book.
 */
public class EditWeddingCommand extends Command {

    public static final String COMMAND_WORD = "edit-wedding";

    public static final String COMMAND_KEYWORD = "ew";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the wedding identified "
            + "by the index number used in the displayed wedding list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters (optional parameters in square brackets): INDEX (must be a positive integer) "
            + "[" + PREFIX_WEDDING + "WEDDING]"
            + "[" + PREFIX_ADDRESS + "ADDRESS]"
            + "[" + PREFIX_DATE + "DATE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_WEDDING + "Mr and Mrs John Tan "
            + PREFIX_ADDRESS + "12 College Ave West";


    private final Index index;
    private final EditWeddingDescriptor editWeddingDescriptor;

    /**
     * @param index of the wedding in the filtered wedding list to edit
     * @param editWeddingDescriptor details to edit the wedding with
     */
    public EditWeddingCommand(Index index, EditWeddingDescriptor editWeddingDescriptor) {
        requireNonNull(index);
        requireNonNull(editWeddingDescriptor);

        this.index = index;
        this.editWeddingDescriptor = new EditWeddingDescriptor(editWeddingDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Wedding> lastShownList = model.getFilteredWeddingList();

        if (lastShownList.isEmpty()) {
            throw new CommandException(String.format(Messages.MESSAGE_NOTHING_TO_PERFORM_ON, "weddings", COMMAND_WORD));
        } else if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_WEDDING_DISPLAYED_INDEX);
        }
        Wedding weddingToEdit = lastShownList.get(index.getZeroBased());

        editWeddingDescriptor.setGuestList(weddingToEdit.getGuestList());
        Wedding editedWedding = createEditedWedding(weddingToEdit, editWeddingDescriptor);

        if (!weddingToEdit.isSameWedding(editedWedding) && model.hasWedding(editedWedding)) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_WEDDING);
        }

        model.setWedding(weddingToEdit, editedWedding);
        model.updateFilteredWeddingList(PREDICATE_SHOW_ALL_WEDDINGS);

        // Update wedding for all people in wedding
        if (editedWedding.hasPartner1()) {
            editedWedding.getPartner1().setWedding(weddingToEdit, editedWedding);
        }
        if (editedWedding.hasPartner2()) {
            editedWedding.getPartner2().setWedding(weddingToEdit, editedWedding);
        }
        for (Person person : editedWedding.getGuestList()) {
            person.setWedding(weddingToEdit, editedWedding);
        }

        // Update person list to show the latest wedding objects
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(Messages.MESSAGE_EDIT_WEDDING_SUCCESS, Messages.format(editedWedding)));
    }

    /**
     * Creates and returns a {@code Wedding} with the details of {@code weddingToEdit}
     * edited with {@code editWeddingDescriptor}.
     */
    private static Wedding createEditedWedding(Wedding weddingToEdit, EditWeddingDescriptor editWeddingDescriptor) {
        assert weddingToEdit != null;

        WeddingName updatedWeddingName = editWeddingDescriptor.getWeddingName().orElse(weddingToEdit.getWeddingName());
        Person partner1 = editWeddingDescriptor.getPartner1().orElse(weddingToEdit.getPartner1());
        Person partner2 = editWeddingDescriptor.getPartner2().orElse(weddingToEdit.getPartner2());
        ArrayList<Person> guestlist = editWeddingDescriptor.getGuestList().orElse(weddingToEdit.getGuestList());
        Address updatedAddress = editWeddingDescriptor.getAddress().orElse(weddingToEdit.getAddress());
        String date = editWeddingDescriptor.getDate().orElse(weddingToEdit.getDate());

        return new Wedding(updatedWeddingName, partner1, partner2, guestlist, updatedAddress, date);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditWeddingCommand otherEditWeddingCommand)) {
            return false;
        }

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
        private Index partner1Index;
        private Index partner2Index;
        private Person partner1;
        private Person partner2;
        private ArrayList<Person> guestList;
        private Address address;
        private String date;

        public EditWeddingDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditWeddingDescriptor(EditWeddingDescriptor toCopy) {
            setWeddingName(toCopy.weddingName);
            setPartner1Index(toCopy.partner1Index);
            setPartner2Index(toCopy.partner2Index);
            setAddress(toCopy.address);
            setDate(toCopy.date);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(weddingName, partner1Index, partner2Index, address, date);
        }

        public void setWeddingName(WeddingName weddingName) {
            this.weddingName = weddingName;
        }

        public Optional<WeddingName> getWeddingName() {
            return Optional.ofNullable(weddingName);
        }

        public Optional<Person> getPartner1() {
            return Optional.ofNullable(partner1);
        }

        public void setPartner1(Person partner1) {
            this.partner1 = partner1;
        }

        public Optional<Person> getPartner2() {
            return Optional.ofNullable(partner2);
        }

        public void setPartner2(Person partner2) {
            this.partner2 = partner2;
        }

        public void setPartner1Index(Index partner1Index) {
            this.partner1Index = partner1Index;
        }

        public void setPartner2Index(Index partner2Index) {
            this.partner2Index = partner2Index;
        }

        public Optional<ArrayList<Person>> getGuestList() {
            return Optional.ofNullable(guestList);
        }

        public void setGuestList(ArrayList<Person> guestList) {
            this.guestList = guestList;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Optional<String> getDate() {
            return Optional.ofNullable(date);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditWeddingDescriptor otherEditWeddingDescriptor)) {
                return false;
            }

            return Objects.equals(weddingName, otherEditWeddingDescriptor.weddingName)
                    && Objects.equals(partner1, otherEditWeddingDescriptor.partner1)
                    && Objects.equals(partner2, otherEditWeddingDescriptor.partner2)
                    && Objects.equals(address, otherEditWeddingDescriptor.address)
                    && Objects.equals(date, otherEditWeddingDescriptor.date);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("weddingName", weddingName)
                    .add("partner1", partner1)
                    .add("partner2", partner2)
                    .add("address", address)
                    .add("date", date)
                    .toString();
        }
    }
}
