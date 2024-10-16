package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalProjects.ALPHA;
import static seedu.address.testutil.TypicalProjects.BETA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.assignment.Assignment;

/**
 * A utility class containing a list of {@code Assignment} objects to be used in tests.
 */
public class TypicalAssignments {
    public static final Assignment ALICE_ALPHA = new AssignmentBuilder().withAssignmentId("1").withProject(ALPHA)
            .withPerson(ALICE).build();
    public static final Assignment BENSON_BETA = new AssignmentBuilder().withAssignmentId("2").withProject(BETA)
            .withPerson(BENSON).build();

    private TypicalAssignments() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical assignments.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Assignment assignment : getTypicalAssignments()) {
            ab.addAssignment(assignment);
        }
        return ab;
    }

    public static List<Assignment> getTypicalAssignments() {
        return new ArrayList<>(Arrays.asList(ALICE_ALPHA, BENSON_BETA));
    }
}
