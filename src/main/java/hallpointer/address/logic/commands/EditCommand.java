package hallpointer.address.logic.commands;

import static hallpointer.address.logic.parser.CliSyntax.PREFIX_NAME;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_ROOM;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_TAG;
import static hallpointer.address.model.Model.PREDICATE_SHOW_ALL_MEMBERS;
import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import hallpointer.address.commons.core.index.Index;
import hallpointer.address.commons.util.CollectionUtil;
import hallpointer.address.commons.util.ToStringBuilder;
import hallpointer.address.logic.Messages;
import hallpointer.address.logic.commands.exceptions.CommandException;
import hallpointer.address.model.Model;
import hallpointer.address.model.member.Member;
import hallpointer.address.model.member.Name;
import hallpointer.address.model.member.Phone;
import hallpointer.address.model.member.Room;
import hallpointer.address.model.tag.Tag;

/**
 * Edits the details of an existing member in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the member identified "
            + "by the index number used in the displayed member list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_ROOM + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 ";

    public static final String MESSAGE_EDIT_MEMBER_SUCCESS = "Edited Member: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_MEMBER = "This member already exists in the address book.";

    private final Index index;
    private final EditMemberDescriptor editMemberDescriptor;

    /**
     * @param index of the member in the filtered member list to edit
     * @param editMemberDescriptor details to edit the member with
     */
    public EditCommand(Index index, EditMemberDescriptor editMemberDescriptor) {
        requireNonNull(index);
        requireNonNull(editMemberDescriptor);

        this.index = index;
        this.editMemberDescriptor = new EditMemberDescriptor(editMemberDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Member> lastShownList = model.getFilteredMemberList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);
        }

        Member memberToEdit = lastShownList.get(index.getZeroBased());
        Member editedMember = createEditedMember(memberToEdit, editMemberDescriptor);

        if (!memberToEdit.isSameMember(editedMember) && model.hasMember(editedMember)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEMBER);
        }

        model.setMember(memberToEdit, editedMember);
        model.updateFilteredMemberList(PREDICATE_SHOW_ALL_MEMBERS);
        return new CommandResult(String.format(MESSAGE_EDIT_MEMBER_SUCCESS, Messages.format(editedMember)));
    }

    /**
     * Creates and returns a {@code Member} with the details of {@code memberToEdit}
     * edited with {@code editMemberDescriptor}.
     */
    private static Member createEditedMember(Member memberToEdit, EditMemberDescriptor editMemberDescriptor) {
        assert memberToEdit != null;

        Name updatedName = editMemberDescriptor.getName().orElse(memberToEdit.getName());
        Phone updatedPhone = editMemberDescriptor.getPhone().orElse(memberToEdit.getPhone());
        Room updatedRoom = editMemberDescriptor.getRoom().orElse(memberToEdit.getRoom());
        Set<Tag> updatedTags = editMemberDescriptor.getTags().orElse(memberToEdit.getTags());

        return new Member(updatedName, updatedPhone, updatedRoom, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editMemberDescriptor.equals(otherEditCommand.editMemberDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editMemberDescriptor", editMemberDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the member with. Each non-empty field value will replace the
     * corresponding field value of the member.
     */
    public static class EditMemberDescriptor {
        private Name name;
        private Phone phone;
        private Room room;
        private Set<Tag> tags;

        public EditMemberDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditMemberDescriptor(EditMemberDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setRoom(toCopy.room);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, room, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setRoom(Room room) {
            this.room = room;
        }

        public Optional<Room> getRoom() {
            return Optional.ofNullable(room);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditMemberDescriptor)) {
                return false;
            }

            EditMemberDescriptor otherEditMemberDescriptor = (EditMemberDescriptor) other;
            return Objects.equals(name, otherEditMemberDescriptor.name)
                    && Objects.equals(phone, otherEditMemberDescriptor.phone)
                    && Objects.equals(room, otherEditMemberDescriptor.room)
                    && Objects.equals(tags, otherEditMemberDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("room", room)
                    .add("tags", tags)
                    .toString();
        }
    }
}
