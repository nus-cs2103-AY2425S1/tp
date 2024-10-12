package hallpointer.address.logic.commands;

import static hallpointer.address.logic.parser.CliSyntax.PREFIX_DATE;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_POINTS;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_SESSION_NAME;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import hallpointer.address.commons.core.index.Index;
import hallpointer.address.commons.util.CollectionUtil;
import hallpointer.address.commons.util.ToStringBuilder;
import hallpointer.address.logic.Messages;
import hallpointer.address.logic.commands.exceptions.CommandException;
import hallpointer.address.model.Model;
import hallpointer.address.model.session.Date;
import hallpointer.address.model.session.Session;
import hallpointer.address.model.session.SessionName;

/**
 * Edits the details of an existing session in the address book.
 */
public class EditSessionCommand extends Command {
    public static final String COMMAND_WORD = "edit_session";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the session identified "
            + "by the index number used in the displayed session list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_SESSION_NAME + "NAME] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_POINTS + "POINTS]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_SESSION_NAME + "Rehearsal "
            + PREFIX_DATE + "2024-09-19 "
            + PREFIX_POINTS + "2";

    public static final String MESSAGE_EDIT_SESSION_SUCCESS = "Edited Session: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_SESSION = "This session already exists in the address book.";

    private final Index index;
    private final EditSessionDescriptor editSessionDescriptor;

    /**
     * @param index of the session in the filtered session list to edit
     * @param editSessionDescriptor details to edit the session with
     */
    public EditSessionCommand(Index index, EditSessionDescriptor editSessionDescriptor) {
        requireNonNull(index);
        requireNonNull(editSessionDescriptor);

        this.index = index;
        this.editSessionDescriptor = new EditSessionDescriptor(editSessionDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Session> lastShownList = model.getSessionList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SESSION_DISPLAYED_INDEX);
        }

        Session sessionToEdit = lastShownList.get(index.getZeroBased());
        Session editedSession = createEditedSession(sessionToEdit, editSessionDescriptor);

        if (!sessionToEdit.isSameSession(editedSession) && model.hasSession(editedSession)) {
            throw new CommandException(MESSAGE_DUPLICATE_SESSION);
        }

        model.setSession(sessionToEdit, editedSession);
        return new CommandResult(String.format(MESSAGE_EDIT_SESSION_SUCCESS, editedSession.getSessionName()));
    }

    /**
     * Creates and returns a {@code Session} with the details of {@code sessionToEdit}
     * edited with {@code editSessionDescriptor}.
     */
    private static Session createEditedSession(Session sessionToEdit, EditSessionDescriptor editSessionDescriptor) {
        assert sessionToEdit != null;

        SessionName updatedName = editSessionDescriptor.getName().orElse(sessionToEdit.getSessionName());
        Date updatedDate = editSessionDescriptor.getDate().orElse(sessionToEdit.getDate());
        int updatedPoints = editSessionDescriptor.getPoints().orElse(sessionToEdit.getPoints());

        return new Session(updatedName, updatedDate, updatedPoints, sessionToEdit.getMembers());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditSessionCommand)) {
            return false;
        }

        EditSessionCommand otherEditSessionCommand = (EditSessionCommand) other;
        return index.equals(otherEditSessionCommand.index)
                && editSessionDescriptor.equals(otherEditSessionCommand.editSessionDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editSessionDescriptor", editSessionDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the session with. Each non-empty field value will replace the
     * corresponding field value of the session.
     */
    public static class EditSessionDescriptor {
        private SessionName name;
        private Date date;
        private Integer points;

        public EditSessionDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditSessionDescriptor(EditSessionDescriptor toCopy) {
            setName(toCopy.name);
            setDate(toCopy.date);
            setPoints(toCopy.points);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, date, points);
        }

        public void setName(SessionName name) {
            this.name = name;
        }

        public Optional<SessionName> getName() {
            return Optional.ofNullable(name);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        public void setPoints(Integer points) {
            this.points = points;
        }

        public Optional<Integer> getPoints() {
            return Optional.ofNullable(points);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditSessionDescriptor)) {
                return false;
            }

            EditSessionDescriptor otherEditSessionDescriptor = (EditSessionDescriptor) other;
            return Objects.equals(name, otherEditSessionDescriptor.name)
                    && Objects.equals(date, otherEditSessionDescriptor.date)
                    && Objects.equals(points, otherEditSessionDescriptor.points);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("date", date)
                    .add("points", points)
                    .toString();
        }
    }

}
