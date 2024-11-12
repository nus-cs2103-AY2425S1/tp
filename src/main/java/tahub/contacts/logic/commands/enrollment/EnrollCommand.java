package tahub.contacts.logic.commands.enrollment;

import static java.util.Objects.requireNonNull;
import static tahub.contacts.commons.util.CollectionUtil.requireAllNonNull;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_COURSE_CODE;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_MATRICULATION_NUMBER;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_TUTORIAL_ID;

import java.util.HashSet;
import java.util.Objects;

import tahub.contacts.commons.util.ToStringBuilder;
import tahub.contacts.logic.Messages;
import tahub.contacts.logic.commands.Command;
import tahub.contacts.logic.commands.CommandResult;
import tahub.contacts.logic.commands.exceptions.CommandException;
import tahub.contacts.model.Model;
import tahub.contacts.model.course.Course;
import tahub.contacts.model.course.CourseCode;
import tahub.contacts.model.course.CourseName;
import tahub.contacts.model.person.Address;
import tahub.contacts.model.person.Email;
import tahub.contacts.model.person.MatriculationNumber;
import tahub.contacts.model.person.Name;
import tahub.contacts.model.person.Person;
import tahub.contacts.model.person.Phone;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociation;
import tahub.contacts.model.tutorial.Tutorial;

/**
 * Enrolls a person into TAHub Contacts.
 */
public class EnrollCommand extends Command {

    public static final String COMMAND_WORD = "enroll";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enrolls a student into a course and tutorial group. "
            + "Parameters: "
            + PREFIX_MATRICULATION_NUMBER + "MATRICULATION NUMBER "
            + PREFIX_COURSE_CODE + "COURSE CODE "
            + PREFIX_TUTORIAL_ID + "TUTORIAL ID \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MATRICULATION_NUMBER + "A0273176Y "
            + PREFIX_COURSE_CODE + "CS2103T "
            + PREFIX_TUTORIAL_ID + "T14 ";

    public static final String MESSAGE_SUCCESS = "New enrollment created: %1$s";


    private final MatriculationNumber matriculationNumberToAdd;
    private final CourseCode courseCodeToAdd;
    private final String tutorialIdToAdd;

    /**
     * Constructs an EnrollCommand object with the provided matriculation number, course code, and tutorial ID.
     *
     * @param matriculationNumber The matriculation number of the student to enroll. Cannot be null.
     * @param courseCode The code of the course to enroll in. Cannot be null.
     * @param tutorialId The ID of the tutorial to enroll in. Cannot be null.
     */
    public EnrollCommand(MatriculationNumber matriculationNumber, CourseCode courseCode, String tutorialId) {
        requireAllNonNull(matriculationNumber, courseCode, tutorialId);
        this.matriculationNumberToAdd = matriculationNumber;
        this.courseCodeToAdd = courseCode;
        this.tutorialIdToAdd = tutorialId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // We have to compare by matriculation number here to check for existence of a person.
        // Success of this relies on the fact that Person objects check for equality of matriculation number only.
        // Hence, other fields here are actually irrelevant.
        Person dummyPerson = new Person(
                matriculationNumberToAdd,
                new Name("dummy"),
                new Phone("98765432"),
                new Email("dummy@gmail.com"),
                new Address("dummy street"),
                new HashSet<>()
        );
        if (!model.hasPerson(dummyPerson)) {
            throw new CommandException(Messages.MESSAGE_PERSON_NOT_FOUND);
        }

        Course dummyCourse = new Course(courseCodeToAdd, new CourseName("dummy course name"));
        if (!model.hasCourse(dummyCourse)) {
            throw new CommandException(Messages.MESSAGE_COURSE_NOT_FOUND);
        }

        // Since addressbook contains a student with this matric number, this operation is guaranteed to succeed
        Person personInSca = model.getAddressBook().getPersonList().stream()
                .filter(person -> person.getMatricNumber().equals(matriculationNumberToAdd))
                .toList()
                .get(0);

        // Same logic for getting course
        Course courseInSca = model.getCourseList().getCourseList().stream()
                .filter(course -> Objects.equals(course.courseCode, courseCodeToAdd))
                .toList()
                .get(0);

        Tutorial tutorialInSca = new Tutorial(tutorialIdToAdd, courseInSca);
        StudentCourseAssociation scaToAdd = new StudentCourseAssociation(personInSca, courseInSca, tutorialInSca);

        if (model.hasSca(scaToAdd)) {
            throw new CommandException(String.format(Messages.MESSAGE_ENROLLMENT_EXISTS,
                    matriculationNumberToAdd.value,
                    tutorialIdToAdd,
                    courseCodeToAdd.courseCode));
        } else {
            model.addSca(scaToAdd);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(scaToAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof EnrollCommand)) {
            return false;
        }

        EnrollCommand e = (EnrollCommand) other;
        return matriculationNumberToAdd.equals(e.matriculationNumberToAdd)
                && courseCodeToAdd.equals(e.courseCodeToAdd)
                && tutorialIdToAdd.equals(e.tutorialIdToAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("matriculationNumberToAdd", matriculationNumberToAdd)
                .add("courseCodeToAdd", courseCodeToAdd)
                .add("tutorialIdToAdd", tutorialIdToAdd)
                .toString();
    }
}
