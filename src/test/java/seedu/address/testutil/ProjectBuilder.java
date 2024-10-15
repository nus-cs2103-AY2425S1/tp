package seedu.address.testutil;

import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectId;
import seedu.address.model.project.ProjectName;

/**
 * A utility class to help with building Person objects.
 */
public class ProjectBuilder {

    public static final String DEFAULT_NAME = "Project Alpha";
    public static final String DEFAULT_ID = "A0276123J";

    private ProjectName projectName;
    private ProjectId projectId;

    /**
     * Creates a {@code ProjectBuilder} with the default details.
     */
    public ProjectBuilder() {
        projectName = new ProjectName(DEFAULT_NAME);
        projectId = new ProjectId(DEFAULT_ID);
    }

    /**
     * Initializes the ProjectBuilder with the data of {@code personToCopy}.
     */
    public ProjectBuilder(Project projectToCopy) {
        projectName = projectToCopy.getName();
        projectId = projectToCopy.getId();
    }

    /**
     * Sets the {@code Name} of the {@code Project} that we are building.
     */
    public ProjectBuilder withName(String name) {
        this.projectName = new ProjectName(name);
        return this;
    }

    /**
     * Sets the {@code Id} of the {@code Project} that we are building.
     */
    public ProjectBuilder withId(String id) {
        this.projectId = new ProjectId(id);
        return this;
    }

    public Project build() {
        return new Project(projectName, projectId);
    }

}
