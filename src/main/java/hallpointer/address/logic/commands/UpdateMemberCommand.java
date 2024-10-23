package hallpointer.address.logic.commands;

import static hallpointer.address.logic.parser.CliSyntax.PREFIX_NAME;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_ROOM;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_TAG;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;
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
import hallpointer.address.model.member.Room;
import hallpointer.address.model.member.Telegram;
import hallpointer.address.model.point.Point;
import hallpointer.address.model.session.Session;
import hallpointer.address.model.tag.Tag;

/**
 * Updates the details of an existing member in the CCA system.
 */
public class UpdateMemberCommand extends Command {

    public static final String COMMAND_WORD = "update_member";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the details of the member identified "
            + "by the index number used in the displayed member list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_TELEGRAM + "TELEGRAM_HANDLE] "
            + "[" + PREFIX_ROOM + "ROOM_NUMBER] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "John Doe "
            + PREFIX_ROOM + "9/10/203 "
            + PREFIX_TELEGRAM + "johnDoe123 "
            + PREFIX_TAG + "friend";

    public static final String MESSAGE_UPDATE_MEMBER_SUCCESS = "Member %1$s's details updated successfully.";
    public static final String MESSAGE_NOT_UPDATED = "At least one field to update must be provided.";
    public static final String MESSAGE_DUPLICATE_MEMBER = "A member with the same name already exists.";
    public static final String MESSAGE_INVALID_INDEX = "Error: Invalid index specified.";

    private final Index index;
    private final UpdateMemberDescriptor updateMemberDescriptor;

    /**
     * @param index of the member in the filtered member list to update
     * @param updateMemberDescriptor details to update the member with
     */
    public UpdateMemberCommand(Index index, UpdateMemberDescriptor updateMemberDescriptor) {
        requireNonNull(index);
        requireNonNull(updateMemberDescriptor);

        this.index = index;
        this.updateMemberDescriptor = new UpdateMemberDescriptor(updateMemberDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Member> lastShownList = model.getFilteredMemberList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        Member memberToUpdate = lastShownList.get(index.getZeroBased());
        Member updatedMember = createUpdatedMember(memberToUpdate, updateMemberDescriptor);

        if (!memberToUpdate.isSameMember(updatedMember) && model.hasMember(updatedMember)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEMBER);
        }

        model.setMember(memberToUpdate, updatedMember);
        model.updateFilteredMemberList(PREDICATE_SHOW_ALL_MEMBERS);
        return new CommandResult(String.format(MESSAGE_UPDATE_MEMBER_SUCCESS, Messages.format(updatedMember)));
    }

    /**
     * Creates and returns a {@code Member} with the details of {@code memberToUpdate}
     * updated with {@code updateMemberDescriptor}.
     */
    private static Member createUpdatedMember(Member memberToUpdate, UpdateMemberDescriptor updateMemberDescriptor) {
        assert memberToUpdate != null;

        Name updatedName = updateMemberDescriptor.getName().orElse(memberToUpdate.getName());
        Telegram updatedTelegram = updateMemberDescriptor.getTelegram().orElse(memberToUpdate.getTelegram());
        Room updatedRoom = updateMemberDescriptor.getRoom().orElse(memberToUpdate.getRoom());
        Set<Tag> updatedTags = updateMemberDescriptor.getTags().orElse(memberToUpdate.getTags());
        Point updatedPoints = updateMemberDescriptor.getTotalPoints().orElse(memberToUpdate.getTotalPoints());
        Set<Session> updatedSessions = updateMemberDescriptor.getSessions().orElse(memberToUpdate.getSessions());

        return new Member(updatedName, updatedTelegram, updatedRoom, updatedTags, updatedPoints, updatedSessions);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateMemberCommand)) {
            return false;
        }

        UpdateMemberCommand otherUpdateMemberCommand = (UpdateMemberCommand) other;
        return index.equals(otherUpdateMemberCommand.index)
                && updateMemberDescriptor.equals(otherUpdateMemberCommand.updateMemberDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("UpdateMemberDescriptor", new UpdateMemberDescriptor())
                .toString();
    }
    /**
     * Stores the details to update the member with. Each non-empty field value will replace the
     * corresponding field value of the member.
     */
    public static class UpdateMemberDescriptor {
        private Name name;
        private Telegram telegram;
        private Room room;
        private Set<Tag> tags;
        private Point totalPoints;
        private List<Session> sessions;

        public UpdateMemberDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public UpdateMemberDescriptor(UpdateMemberDescriptor toCopy) {
            setName(toCopy.name);
            setTelegram(toCopy.telegram);
            setRoom(toCopy.room);
            setTags(toCopy.tags);
            setTotalPoints(toCopy.totalPoints);
            setSessions(toCopy.sessions);
        }

        /**
         * Returns true if at least one field is updated.
         */
        public boolean isAnyFieldUpdated() {
            return CollectionUtil.isAnyNonNull(name, telegram, room, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setTelegram(Telegram telegram) {
            this.telegram = telegram;
        }

        public Optional<Telegram> getTelegram() {
            return Optional.ofNullable(telegram);
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

        public void setTotalPoints(Point totalPoints) {
            this.totalPoints = totalPoints;
        }

        public Optional<Point> getTotalPoints() {
            return Optional.ofNullable(totalPoints);
        }

        public void setSessions(List<Session> sessions) {
            this.sessions = sessions;
        }

        public Optional<Set<Session>> getSessions() {
            return (sessions != null)
                ? Optional.of(Collections.unmodifiableSet(new HashSet<>(sessions))) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof UpdateMemberDescriptor)) {
                return false;
            }

            UpdateMemberDescriptor otherUpdateMemberDescriptor = (UpdateMemberDescriptor) other;
            return Objects.equals(name, otherUpdateMemberDescriptor.name)
                    && Objects.equals(telegram, otherUpdateMemberDescriptor.telegram)
                    && Objects.equals(room, otherUpdateMemberDescriptor.room)
                    && Objects.equals(tags, otherUpdateMemberDescriptor.tags)
                    && Objects.equals(totalPoints, otherUpdateMemberDescriptor.totalPoints)
                    && Objects.equals(sessions, otherUpdateMemberDescriptor.sessions);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("telegram", telegram)
                    .add("room", room)
                    .add("tags", tags)
                    .add("totalPoints", totalPoints)
                    .add("sessions", sessions)
                    .toString();
        }
    }
}
