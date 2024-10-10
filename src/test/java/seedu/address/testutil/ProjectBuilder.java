package seedu.address.testutil;

import seedu.address.model.project.Id;
import seedu.address.model.project.Name;
import seedu.address.model.project.Project;

/**
 * A utility class to help with building Person objects.
 */
public class ProjectBuilder {

    public static final String DEFAULT_NAME = "Project Alpha";
    public static final String DEFAULT_ID = "A0276123J";

    private Name name;
    private Id id;

    /**
     * Creates a {@code ProjectBuilder} with the default details.
     */
    public ProjectBuilder() {
        name = new Name(DEFAULT_NAME);
        id = new Id(DEFAULT_ID);
    }

    /**
     * Initializes the ProjectBuilder with the data of {@code personToCopy}.
     */
    public ProjectBuilder(Project projectToCopy) {
        name = projectToCopy.getName();
        id = projectToCopy.getId();
    }

    /**
     * Sets the {@code Name} of the {@code Project} that we are building.
     */
    public ProjectBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Id} of the {@code Project} that we are building.
     */
    public ProjectBuilder withId(String id) {
        this.id = new Id(id);
        return this;
    }

    public Project build() {
        return new Project(name, id);
    }

}
