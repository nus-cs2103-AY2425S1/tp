package seedu.address.model.assignment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.person.Person;
import seedu.address.model.project.Project;

/**
 * Represents a (Project) Assignment in the address book.
 * Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Assignment {
    private final Project project;
    private final Person person;

    /**
     * Every field must be present and not null.
     */
    public Assignment(Project project, Person person) {
        requireAllNonNull(project, person);
        this.project = project;
        this.person = person;
    }

    public Project getProject() {
        return project;
    }

    public Person getPerson() {
        return person;
    }

    /**
     * Returns true if both {@code Project#isSameProject(Project)} and
     * {@code Person#isSamePerson(Person)} returns true.
     * This defines a weaker notion of equality between two projects.
     */
    public boolean isSameAssignment(Assignment otherAssignment) {
        if (otherAssignment == this) {
            return true;
        }

        Person otherPerson = otherAssignment.getPerson();
        boolean isSamePerson = otherPerson != null && person.isSamePerson(otherPerson);

        Project otherProject = otherAssignment.getProject();
        boolean isSameProject = otherProject != null && project.isSameProject(otherProject);

        return isSamePerson && isSameProject;
    }
}
