package seedu.address.model.util;

import seedu.address.model.assignment.PredefinedAssignment;
import seedu.address.model.assignment.PredefinedAssignmentsData;
import seedu.address.model.assignment.ReadOnlyPredefinedAssignmentsData;

/**
 * Contains utility methods for populating
 * {@code PredefinedAssignmentsData} with sample data.
 */
public class SampleAssignmentsUtil {
    public static PredefinedAssignment[] getSampleAssignments() {
        return new PredefinedAssignment[]{
            new PredefinedAssignment("Ex01", 10f),
            new PredefinedAssignment("Ex02", 10f),
            new PredefinedAssignment("Ex03", 25f)
        };
    }

    /**
     * Returns predefined assignments database populated with the sample assignments.
     *
     * @return The sample predefined assignment database.
     */
    public static ReadOnlyPredefinedAssignmentsData getSamplePredefinedAssignments() {
        PredefinedAssignmentsData samplePredefinedAssignment =
                new PredefinedAssignmentsData();
        for (PredefinedAssignment sample : getSampleAssignments()) {
            samplePredefinedAssignment.add(sample);
        }
        return samplePredefinedAssignment;
    }
}
