package seedu.address.model.assignment;

import java.util.function.Predicate;

import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectName;

/**
 * Tests that an {@code Assignment}'s project matches the specified project name.
 */
public class AssignmentProjectPredicate implements Predicate<Assignment> {
    private final ProjectName proposedProjectName;

    public AssignmentProjectPredicate(String projectName) {
        this.proposedProjectName = new ProjectName(projectName);
    }

    public AssignmentProjectPredicate(ProjectName projectName) {
        this.proposedProjectName = projectName;
    }

    @Override
    public boolean test(Assignment assignment) {
        Project project = assignment.getProject();
        return project.getName().equals(this.proposedProjectName);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AssignmentProjectPredicate
                && proposedProjectName.equals(((AssignmentProjectPredicate) other).proposedProjectName));
    }
}
