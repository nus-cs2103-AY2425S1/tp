package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEETUPS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.meetup.MeetUp;
import seedu.address.model.meetup.MeetUpFrom;
import seedu.address.model.meetup.MeetUpInfo;
import seedu.address.model.meetup.MeetUpName;
import seedu.address.model.meetup.MeetUpTo;

/**
 * Edits the details of an existing meetup in the address book.
 */
public class EditMeetUpCommand extends Command {

    public static final String COMMAND_WORD = "editm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the meetup in the address book. "
            + "Existing meetup will be overwritten by the input meetup.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_INFO + "MEETUP INFO] "
            + "[" + PREFIX_FROM + "YYYY-MM-DD HH:mm] "
            + "[" + PREFIX_TO + "YYYY-MM-DD HH:mm]\n"
            + "Example: " + COMMAND_WORD + " 2 "
            + PREFIX_INFO + "Review work plans while having lunch with Eswen "
            + PREFIX_FROM + "2024-02-03 12:00 "
            + PREFIX_TO + "2024-02-03 14:00 ";

    public static final String MESSAGE_EDIT_MEETUP_SUCCESS = "Edited Meetup: %1$s";
    public static final String MESSAGE_MEETUP_NOT_EDITED = "Please check for missing fields or invalid format.";

    private final Index targetIndex;
    private final EditMeetUpCommand.EditMeetUpDescriptor editMeetUpDescriptor;

    /**
     * Creates an EditMeetUpCommand to edit the specified {@code MeetUp} by its index
     */
    public EditMeetUpCommand(Index editIndex, EditMeetUpDescriptor editMeetUpDescriptor) {
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

        model.setMeetUp(meetUpToEdit, editedMeetUp);
        model.updateFilteredMeetUpList(PREDICATE_SHOW_ALL_MEETUPS);
        return new CommandResult(String.format(MESSAGE_EDIT_MEETUP_SUCCESS, Messages.format(editedMeetUp)));
    }

    /**
     * Creates and returns a {@code MeetUp} with the details of {@code meetUpToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static MeetUp createEditedMeetUp(MeetUp meetUpToEdit,
                                                 EditMeetUpCommand.EditMeetUpDescriptor editMeetUpDescriptor) {
        assert meetUpToEdit != null;

        MeetUpName updatedName = editMeetUpDescriptor.getMeetUpName().orElse(meetUpToEdit.getName());
        MeetUpInfo updatedInfo = editMeetUpDescriptor.getMeetUpInfo().orElse(meetUpToEdit.getInfo());
        MeetUpFrom updatedFromTime = editMeetUpDescriptor.getMeetUpFrom().orElse(meetUpToEdit.getFrom());
        MeetUpTo updatedToTime = editMeetUpDescriptor.getMeetUpTo().orElse(meetUpToEdit.getTo());

        return new MeetUp(updatedName, updatedInfo, updatedFromTime, updatedToTime);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditMeetUpCommand)) {
            return false;
        }

        EditMeetUpCommand otherEditMeetUpCommand = (EditMeetUpCommand) other;
        return targetIndex.equals(otherEditMeetUpCommand.targetIndex)
                && editMeetUpDescriptor.equals(otherEditMeetUpCommand.editMeetUpDescriptor);

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", targetIndex)
                .add("editMeetUpDescriptor", editMeetUpDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditMeetUpDescriptor {
        private MeetUpName name;
        private MeetUpInfo info;
        private MeetUpFrom from;
        private MeetUpTo to;

        public EditMeetUpDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditMeetUpDescriptor(EditMeetUpCommand.EditMeetUpDescriptor toCopy) {
            setMeetUpName(toCopy.name);
            setMeetUpInfo(toCopy.info);
            setMeetUpFrom(toCopy.from);
            setMeetUpTo(toCopy.to);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyMeetUpFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, info, from, to);
        }

        // This method should not be used yet.
        public void setMeetUpName(MeetUpName name) {
            this.name = name;
        }

        public Optional<MeetUpName> getMeetUpName() {
            return Optional.ofNullable(name);
        }

        public void setMeetUpInfo(MeetUpInfo info) {
            this.info = info;
        }

        public Optional<MeetUpInfo> getMeetUpInfo() {
            return Optional.ofNullable(info);
        }

        public void setMeetUpFrom(MeetUpFrom from) {
            this.from = from;
        }

        public Optional<MeetUpFrom> getMeetUpFrom() {
            return Optional.ofNullable(from);
        }

        public void setMeetUpTo(MeetUpTo to) {
            this.to = to;
        }

        public Optional<MeetUpTo> getMeetUpTo() {
            return Optional.ofNullable(to);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditMeetUpCommand.EditMeetUpDescriptor)) {
                return false;
            }

            EditMeetUpCommand.EditMeetUpDescriptor otherEditMeetUpDescriptor =
                    (EditMeetUpCommand.EditMeetUpDescriptor) other;

            return Objects.equals(name, otherEditMeetUpDescriptor.name)
                    && Objects.equals(info, otherEditMeetUpDescriptor.info)
                    && Objects.equals(from, otherEditMeetUpDescriptor.from)
                    && Objects.equals(to, otherEditMeetUpDescriptor.to);

        }
    }
}

