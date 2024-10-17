package seedu.address.model.project;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Project in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Project {

    // Identity fields
    private final ProjectName projectName;
    private final ProjectId projectId;

    /**
     * Every field must be present and not null.
     */
    public Project(ProjectName projectName, ProjectId projectId) {
        requireAllNonNull(projectName, projectId);
        this.projectName = projectName;
        this.projectId = projectId;
    }

    public ProjectName getName() {
        return projectName;
    }

    public ProjectId getId() {
        return projectId;
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameProject(Project otherProject) {
        if (otherProject == this) {
            return true;
        }

        return otherProject != null
                && otherProject.getId().equals(getId());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Project)) {
            return false;
        }

        Project otherProject = (Project) other;
        return projectName.equals(otherProject.projectName)
                && projectId.equals(otherProject.projectId);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(projectName, projectId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", projectName)
                .add("id", projectId)
                .toString();
    }

}
