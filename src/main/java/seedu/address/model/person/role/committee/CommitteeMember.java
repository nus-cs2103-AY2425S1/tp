package seedu.address.model.person.role.committee;

import seedu.address.model.person.role.Role;

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
        this.branch = branch;
        this.position = position;
    }

    public Branch getBranch() {
        return branch;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "[" + getRoleName() + " - " + branch + " " + PositionString.getPositionString(position) + "]";
    }
}
