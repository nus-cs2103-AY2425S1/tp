package seedu.eventfulnus.model.person.role.committee;

import static seedu.eventfulnus.commons.util.CollectionUtil.requireAllNonNull;

import seedu.eventfulnus.model.person.role.Role;

/**
 * Represents a CommitteeMember role in the address book.
 */
public class CommitteeMember extends Role {
    private final Branch branch;
    private final Position position;

    /**
     * Creates an {@code CommitteeMember} object with the given {@link Branch} and {@link Position}.
     *
     * @param branch   CommitteeMember's home {@link Branch}.
     * @param position CommitteeMember's participating {@link Position}.
     */
    public CommitteeMember(Branch branch, Position position) {
        super("Committee - " + branch + " - " + PositionString.getPositionString(position));
        requireAllNonNull(branch, position);
        this.branch = branch;
        this.position = position;
    }
}
