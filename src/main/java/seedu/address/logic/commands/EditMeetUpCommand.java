package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.meetup.MeetUp;

/**
 * Edits the details of an existing meetup in the address book.
 */
public class EditMeetUpCommand extends Command {

    public static final String COMMAND_WORD = "editm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the meetup in the address book. "
            + "Existing meetup will be overwritten by the input meetup.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "[" + PREFIX_INFO + "MEETUP INFO] "
            + "[" + PREFIX_FROM + "YYYY-MM-DD HH:mm] "
            + "[" + PREFIX_EMAIL + "YYYY-MM-DD HH:mm]\n"
            + "Example: " + COMMAND_WORD + " 2 "
            + PREFIX_INFO + "Review work plans while having lunch with Eswen "
            + PREFIX_FROM + "2024-02-03 12:00 "
            + PREFIX_TO + "2024-02-03 14:00 ";

    public static final String MESSAGE_EDIT_MEETUP_SUCCESS = "Edited Meetup: %1$s";
    public static final String MESSAGE_MEETUP_NOT_EDITED = "Please check for missing fields or invalid format.";

    private final MeetUp editedMeetUp;
    private final Index targetIndex;

    /**
     * Creates an EditMeetUpCommand to edit the specified {@code MeetUp} by its index
     */
    public EditMeetUpCommand(Index editIndex, MeetUp meetup) {
        requireNonNull(editIndex);
        requireNonNull(meetup);
        this.targetIndex = editIndex;
        this.editedMeetUp = meetup;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

//        /**
//         * Following block of code is for when meet up is able to be stored using Storage.
//         * Need to write a setMeetUp function too, after creating a unique person list
//         * Need a filtered meetup list so that we can write the corresponding functions
//         * Also need a edit meetup descriptor to follow the standards of the given AB3
//         */
//        -------------------------------------------------------------------------------
//        List<MeetUp> lastShownList = model.getFilteredMeetUpList();
//        if (index.getZeroBased() >= lastShownList.size()) {
//            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
//        }
//
//        MeetUp meetUpToEdit = lastShownList.get(index.getZeroBased());
//        MeetUp editedMeetUp = createEditedMeetUp(meetUpToEdit, editMeetUpDescriptor);
//
//        model.setMeetUp(meetUpToEdit, editedMeetUp);
//        model.updateFilteredMeetUpList(PREDICATE_SHOW_ALL_MEETUPS);
        return new CommandResult(String.format(MESSAGE_EDIT_MEETUP_SUCCESS, Messages.format(editedMeetUp)));
//        -------------------------------------------------------------------------------
    }

//    /**
//     * Creates and returns a {@code MeetUp} with the details of {@code meetUpToEdit}
//     * edited with {@code editPersonDescriptor}.
//     */
//    private static MeetUp createEditedMeetUp(MeetUp meetUpToEdit,
//                                             EditMeetUpCommand.EditMeetUpDescriptor editMeetUpDescriptor) {
//        assert meetUpToEdit != null;
//
//        String updatedName = editMeetUpDescriptor.getName().orElse(meetUpToEdit.getName());
//        String updatedInfo = editMeetUpDescriptor.getInfo().orElse(meetUpToEdit.getInfo());
//        LocalDateTime updatedFromTime = editMeetUpDescriptor.getFrom().orElse(meetUpToEdit.getFrom());
//        LocalDateTime updatedToTime = editMeetUpDescriptor.getTo().orElse(meetUpToEdit.getTo());
//
//        return new MeetUp(updatedName, updatedInfo, updatedFromTime, updatedToTime);
//    }
@Override
public boolean equals(Object other) {
    if (other == this) {
        return true;
    }

    // instanceof handles nulls
    if (!(other instanceof EditMeetUpCommand)) {
        return false;
    }

    EditMeetUpCommand otherEditMeetupCommand = (EditMeetUpCommand) other;

//    For after editMeetUpDescriptor is implemented
//    return index.equals(otherEditMeetUpCommand.index)
//            && editMeetUpDescriptor.equals(otherEditMeetUpCommand.editMeetUpDescriptor);

    return editedMeetUp.equals(otherEditMeetupCommand.editedMeetUp);
}

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toEdit", editedMeetUp)
                .toString();
    }
}

