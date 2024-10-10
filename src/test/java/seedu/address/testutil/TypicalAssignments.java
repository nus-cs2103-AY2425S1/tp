package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalProjects.ALPHA;
import static seedu.address.testutil.TypicalProjects.BETA;

import seedu.address.model.assignment.Assignment;

/**
 * A utility class containing a list of {@code Assignment} objects to be used in tests.
 */
public class TypicalAssignments {
    public static final Assignment ALICE_ALPHA = new AssignmentBuilder().withProject(ALPHA).withPerson(ALICE).build();
    public static final Assignment BENSON_BETA = new AssignmentBuilder().withProject(BETA).withPerson(BENSON).build();
}
