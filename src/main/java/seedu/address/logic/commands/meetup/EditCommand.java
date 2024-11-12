package seedu.address.logic.commands.meetup;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDED_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEETUPS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.meetup.AddedBuyer;
import seedu.address.model.meetup.From;
import seedu.address.model.meetup.Info;
import seedu.address.model.meetup.MeetUp;
import seedu.address.model.meetup.Subject;
import seedu.address.model.meetup.To;


/**
 * Edits the details of an existing meet-up in the meet-up list.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the meet-up identified "
            + "by the index number used in the displayed meet-up list. "
            + "Existing meet-up will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "[" + PREFIX_SUBJECT + "SUBJECT] "
            + "[" + PREFIX_INFO + "INFO] "
            + "[" + PREFIX_FROM + "YYYY-MM-DD HH:mm] "
            + "[" + PREFIX_TO + "YYYY-MM-DD HH:mm] "
            + "[" + PREFIX_ADDED_BUYER + "BUYER NAME]...\n"
            + "Example: " + COMMAND_WORD + " 2 "
            + PREFIX_INFO + "Review work plans while having lunch with Jason "
            + PREFIX_FROM + "2024-02-03 12:00 "
            + PREFIX_TO + "2024-02-03 14:00 "
            + PREFIX_ADDED_BUYER + "Alex Yeoh "
            + PREFIX_ADDED_BUYER + "David Li ";

    public static final String MESSAGE_EDIT_MEETUP_SUCCESS = "Edited meet-up: %1$s";
    public static final String MESSAGE_MEETUP_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_MEETUP = "This meet-up already exists in the meet-up list.";
    public static final String MESSAGE_INVALID_TO_FROM = "TO (%1$s) must be after FROM (%2$s)";

    private final Index targetIndex;
    private final EditMeetUpDescriptor editMeetUpDescriptor;

    /**
     * Creates an EditCommand to edit the specified {@code MeetUp} by its index
     */
    public EditCommand(Index editIndex, EditMeetUpDescriptor editMeetUpDescriptor) {
        requireNonNull(editIndex);
        requireNonNull(editMeetUpDescriptor);

        this.targetIndex = editIndex;
        this.editMeetUpDescriptor = editMeetUpDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<MeetUp> lastShownList = model.getFilteredMeetUpList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEETUP_DISPLAYED_INDEX);
        }

        MeetUp meetUpToEdit = lastShownList.get(targetIndex.getZeroBased());
        MeetUp editedMeetUp = createEditedMeetUp(meetUpToEdit, editMeetUpDescriptor);

        if (!meetUpToEdit.isSameMeetUp(editedMeetUp) && model.hasMeetUp(editedMeetUp)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEETUP);
        }

        if (!editedMeetUp.hasValidToFrom()) {
            throw new CommandException(String.format(MESSAGE_INVALID_TO_FROM,
                    editedMeetUp.getTo(), editedMeetUp.getFrom()));
        }

        model.setMeetUp(meetUpToEdit, editedMeetUp);
        model.updateFilteredMeetUpList(PREDICATE_SHOW_ALL_MEETUPS);
        return new CommandResult(String.format(MESSAGE_EDIT_MEETUP_SUCCESS, Messages.format(editedMeetUp)),
                false, false, true, false, false);
    }

    /**
     * Creates and returns a {@code MeetUp} with the details of {@code meetUpToEdit}
     * edited with {@code editBuyerDescriptor}.
     */
    private static MeetUp createEditedMeetUp(MeetUp meetUpToEdit,
                                             EditMeetUpDescriptor editMeetUpDescriptor) {
        requireNonNull(meetUpToEdit);
        requireNonNull(editMeetUpDescriptor);

        Subject updatedSubject = editMeetUpDescriptor.getSubject().orElse(meetUpToEdit.getSubject());
        Info updatedInfo = editMeetUpDescriptor.getInfo().orElse(meetUpToEdit.getInfo());
        From updatedFrom = editMeetUpDescriptor.getFrom().orElse(meetUpToEdit.getFrom());
        To updatedTo = editMeetUpDescriptor.getTo().orElse(meetUpToEdit.getTo());
        Set<AddedBuyer> updatedAddedBuyers = editMeetUpDescriptor.getAddedBuyers()
                .orElse(meetUpToEdit.getAddedBuyers());
        return new MeetUp(updatedSubject, updatedInfo, updatedFrom, updatedTo, updatedAddedBuyers);
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
        return targetIndex.equals(otherEditCommand.targetIndex)
                && editMeetUpDescriptor.equals(otherEditCommand.editMeetUpDescriptor);

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", targetIndex)
                .add("editMeetUpDescriptor", editMeetUpDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the meet-up with. Each non-empty field value will replace the
     * corresponding field value of the meet-up.
     */
    public static class EditMeetUpDescriptor {
        private Subject subject;
        private Info info;
        private From from;
        private To to;
        private Set<AddedBuyer> addedBuyers;

        public EditMeetUpDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code addedBuyers} is used internally.
         */
        public EditMeetUpDescriptor(EditMeetUpDescriptor toCopy) {
            setSubject(toCopy.subject);
            setInfo(toCopy.info);
            setFrom(toCopy.from);
            setTo(toCopy.to);
            setAddedBuyers(toCopy.addedBuyers);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyMeetUpFieldEdited() {
            return CollectionUtil.isAnyNonNull(subject, info, from, to, addedBuyers);
        }

        public void setSubject(Subject subject) {
            this.subject = subject;
        }

        public Optional<Subject> getSubject() {
            return Optional.ofNullable(subject);
        }

        public void setInfo(Info info) {
            this.info = info;
        }

        public Optional<Info> getInfo() {
            return Optional.ofNullable(info);
        }

        public void setFrom(From from) {
            this.from = from;
        }

        public Optional<From> getFrom() {
            return Optional.ofNullable(from);
        }

        public void setTo(To to) {
            this.to = to;
        }

        public Optional<To> getTo() {
            return Optional.ofNullable(to);
        }

        /**
         * Sets {@code addedBuyers} to this object's {@code addedBuyers}.
         * A defensive copy of {@code addedBuyers} is used internally.
         */
        public void setAddedBuyers(Set<AddedBuyer> addedBuyers) {
            this.addedBuyers = (addedBuyers != null) ? new HashSet<>(addedBuyers) : null;
        }

        /**
         * Returns an unmodifiable addedBuyer set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code addedBuyers} is null.
         */
        public Optional<Set<AddedBuyer>> getAddedBuyers() {
            return (addedBuyers != null) ? Optional.of(Collections.unmodifiableSet(addedBuyers)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditMeetUpDescriptor)) {
                return false;
            }

            EditMeetUpDescriptor otherEditMeetUpDescriptor =
                    (EditMeetUpDescriptor) other;

            return Objects.equals(subject, otherEditMeetUpDescriptor.subject)
                    && Objects.equals(info, otherEditMeetUpDescriptor.info)
                    && Objects.equals(from, otherEditMeetUpDescriptor.from)
                    && Objects.equals(to, otherEditMeetUpDescriptor.to)
                    && Objects.equals(addedBuyers, otherEditMeetUpDescriptor.addedBuyers);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("subject", subject)
                    .add("info", info)
                    .add("from", from)
                    .add("to", to)
                    .add("addedBuyers", addedBuyers)
                    .toString();
        }
    }
}

