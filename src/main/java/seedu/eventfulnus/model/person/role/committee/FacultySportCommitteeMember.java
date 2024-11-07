package seedu.eventfulnus.model.person.role.committee;

import static seedu.eventfulnus.commons.util.CollectionUtil.requireAllNonNull;

import seedu.eventfulnus.model.person.role.Faculty;

/**
 * Represents a Faculty Sports Committee Member role in the address book.
 */
public class FacultySportCommitteeMember extends CommitteeMember {
    private final Faculty faculty;

    /**
     * Creates a Faculty Sports Committee Member object with the given {@link Faculty}.
     *
     * @param faculty Faculty of the Faculty Sport Committee Member.
     * @param position Position of the Faculty Sport Committee Member.
     */
    public FacultySportCommitteeMember(Faculty faculty, Position position) {
        super(Branch.SPORTS, position);
        requireAllNonNull(faculty, position);
        this.faculty = faculty;
        setRoleName(getRoleName() + " - " + faculty);
    }
}
