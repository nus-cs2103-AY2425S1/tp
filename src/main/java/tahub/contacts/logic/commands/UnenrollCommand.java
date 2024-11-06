package tahub.contacts.logic.commands;

import static java.util.Objects.requireNonNull;
import static tahub.contacts.commons.util.CollectionUtil.requireAllNonNull;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_COURSE_CODE;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_MATRICULATION_NUMBER;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_TUTORIAL_ID;

import java.util.HashSet;
import java.util.Objects;

import tahub.contacts.commons.util.ToStringBuilder;
import tahub.contacts.logic.Messages;
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
public class UnenrollCommand extends Command {

    public static final String COMMAND_WORD = "unenroll";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unenrolls a student into a course and tutorial group. "
            + "Parameters: "
            + PREFIX_MATRICULATION_NUMBER + "MATRICULATION NUMBER "
            + PREFIX_COURSE_CODE + "COURSE CODE "
            + PREFIX_TUTORIAL_ID + "TUTORIAL ID \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MATRICULATION_NUMBER + "A0273176Y "
            + PREFIX_COURSE_CODE + "CS2103T "
            + PREFIX_TUTORIAL_ID + "T14 ";

    public static final String MESSAGE_SUCCESS = "Enrollment successfully removed: %1$s";


    private final MatriculationNumber matriculationNumberToRemove;
    private final CourseCode courseCodeToRemove;
    private final String tutorialIdToRemove;

    /**
     * Constructs an UnenrollCommand object with the provided matriculation number, course code, and tutorial ID.
     *
     * @param matriculationNumber The matriculation number of the student to unenroll. Cannot be null.
     * @param courseCode The code of the course to unenroll from. Cannot be null.
     * @param tutorialId The ID of the tutorial to unenroll from. Cannot be null.
     */
    public UnenrollCommand(MatriculationNumber matriculationNumber, CourseCode courseCode, String tutorialId) {
        requireAllNonNull(matriculationNumber, courseCode, tutorialId);
        this.matriculationNumberToRemove = matriculationNumber;
        this.courseCodeToRemove = courseCode;
        this.tutorialIdToRemove = tutorialId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // We have to compare by matriculation number here to check for existence of a person.
        // Success of this relies on the fact that Person objects check for equality of matriculation number only.
        // Hence, other fields here are actually irrelevant.
        Person dummyPerson = new Person(
                matriculationNumberToRemove,
                new Name("dummy"),
                new Phone("98765432"),
                new Email("dummy@gmail.com"),
                new Address("dummy street"),
                new HashSet<>()
        );
        if (!model.hasPerson(dummyPerson)) {
            throw new CommandException(Messages.MESSAGE_PERSON_NOT_FOUND);
        }

        Course dummyCourse = new Course(courseCodeToRemove, new CourseName("dummy course name"));
        if (!model.hasCourse(dummyCourse)) {
            throw new CommandException(Messages.MESSAGE_COURSE_NOT_FOUND);
        }

        // Since addressbook contains a student with this matric number, this operation is guaranteed to succeed
        Person personInSca = model.getAddressBook().getPersonList().stream()
                .filter(person -> person.getMatricNumber().equals(matriculationNumberToRemove))
                .toList()
                .get(0);

        // Same logic for getting course
        Course courseInSca = model.getCourseList().getCourseList().stream()
                .filter(course -> Objects.equals(course.courseCode, courseCodeToRemove))
                .toList()
                .get(0);

        Tutorial tutorialInSca = new Tutorial(tutorialIdToRemove, courseInSca);
        StudentCourseAssociation scaToRemove = new StudentCourseAssociation(personInSca, courseInSca, tutorialInSca);

        // If there is no such sca to be unenrolled, throw an exception
        if (!model.hasSca(scaToRemove)) {
            throw new CommandException(String.format(Messages.MESSAGE_ENROLLMENT_DOES_NOT_EXIST,
                    matriculationNumberToRemove.value,
                    tutorialIdToRemove,
                    courseCodeToRemove.courseCode));
        } else {
            // Note: The temporary sca object created here is identical to the one stored in the sca list,
            // as primary key is defined as the combination of matric number, course code, and tutorial id
            model.deleteSca(scaToRemove);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(scaToRemove)));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof UnenrollCommand)) {
            return false;
        }

        UnenrollCommand e = (UnenrollCommand) other;
        return matriculationNumberToRemove.equals(e.matriculationNumberToRemove)
                && courseCodeToRemove.equals(e.courseCodeToRemove)
                && tutorialIdToRemove.equals(e.tutorialIdToRemove);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("matriculationNumberToAdd", matriculationNumberToRemove)
                .add("courseCodeToAdd", courseCodeToRemove)
                .add("tutorialIdToAdd", tutorialIdToRemove)
                .toString();
    }
}
