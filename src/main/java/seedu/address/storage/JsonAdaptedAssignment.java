package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentId;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.EmployeeId;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectId;

/**
 * Jackson-friendly version of {@link Assignment}.
 */
public class JsonAdaptedAssignment {
    public static final String MISSING_PROJECT_ID_MESSAGE = "Project id is missing!";
    public static final String MISSING_PERSON_ID_MESSAGE = "Person id is missing!";
    public static final String MISSING_ASSIGNMENT_ID_MESSAGE = "Assignment id is missing!";
    public static final String PROJECT_NOT_FOUND_MESSAGE = "Project not found!";
    public static final String PERSON_NOT_FOUND_MESSAGE = "Person not found!";

    private final String assignmentId;
    private final String projectId;
    private final String personId;


    /**
     * Constructs a {@code JsonAdaptedPersonProjectAssignment} with the given project and person details.
     */
    @JsonCreator
    public JsonAdaptedAssignment(@JsonProperty("assignmentId") String assignmentId,
                                 @JsonProperty("projectId") String projectId,
                                 @JsonProperty("personId") String personId) {
        this.assignmentId = assignmentId;
        this.projectId = projectId;
        this.personId = personId;
    }

    /**
     * Converts a given {@code Assignment} into this class for Jackson use.
     */
    public JsonAdaptedAssignment(Assignment source) {
        this.assignmentId = source.getAssignmentId().value;
        this.projectId = source.getProject().getId().fullId;
        this.personId = source.getPerson().getEmployeeId().value;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Assignment toModelType(AddressBook addressBook) throws IllegalValueException {
        if (personId == null) {
            throw new IllegalValueException(MISSING_PERSON_ID_MESSAGE);
        }

        if (projectId == null) {
            throw new IllegalValueException(MISSING_PROJECT_ID_MESSAGE);
        }

        if (assignmentId == null) {
            throw new IllegalValueException(MISSING_ASSIGNMENT_ID_MESSAGE);
        }

        if (!ProjectId.isValidId(projectId)) {
            throw new IllegalValueException(ProjectId.MESSAGE_CONSTRAINTS);
        }

        if (!AssignmentId.isValidAssignmentId(assignmentId)) {
            throw new IllegalValueException(AssignmentId.MESSAGE_CONSTRAINTS);
        }

        if (!EmployeeId.isValidEmployeeId(personId)) {
            throw new IllegalValueException(EmployeeId.MESSAGE_CONSTRAINTS);
        }

        final Project modelProject = addressBook
                                        .getProjectList()
                                        .stream()
                                        .filter(project -> project.getId().equals(new ProjectId(projectId)))
                                        .findFirst()
                                        .orElse(null);

        if (modelProject == null) {
            throw new IllegalValueException(PROJECT_NOT_FOUND_MESSAGE);
        }

        final Employee modelPerson = addressBook
                                    .getPersonList()
                                    .stream()
                                    .filter(person -> person.getEmployeeId().equals(new EmployeeId(personId)))
                                    .findFirst()
                                    .orElse(null);

        if (modelPerson == null) {
            throw new IllegalValueException(PERSON_NOT_FOUND_MESSAGE);
        }

        final AssignmentId modelAssignmentId = new AssignmentId(assignmentId);

        return new Assignment(modelAssignmentId, modelProject, modelPerson);
    }

}
