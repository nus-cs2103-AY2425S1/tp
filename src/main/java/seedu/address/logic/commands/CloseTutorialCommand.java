package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_TUTORIAL_NOT_FOUND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tutorial.Tutorial;

/**
 * Closes a tutorial in the system by unenrolling all students and marking the tutorial as closed.
 */
public class CloseTutorialCommand extends Command {

    public static final String COMMAND_WORD = "closetut";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Closes the tutorial identified by the subject used in the displayed tutorial list.\n"
            + "Parameters: "
            + PREFIX_TUTORIAL + "SUBJECT_NAME\n"
            + "Example: " + COMMAND_WORD
            + PREFIX_TUTORIAL + "Chemistry";

    public static final String MESSAGE_CLOSE_TUTORIAL_SUCCESS = "Successfully closed tutorial: %1$s class";

    private final Tutorial toCloseTutorial;

    /**
     * Constructs a {@code CloseTutorialCommand} with the specified tutorial to close.
     *
     * @param tutorial The tutorial to close.
     */
    public CloseTutorialCommand(Tutorial tutorial) {
        requireNonNull(tutorial);
        toCloseTutorial = tutorial;
    }

    /**
     * Executes the command to close a tutorial. If the tutorial exists, all students will be unenrolled,
     * and the tutorial will be marked as closed. (Check UnEnrollCommand to see its implementation)
     *
     * @param model {@code Model} which the command operates on.
     * @return CommandResult which indicates the success of the tutorial closure.
     * @throws CommandException if the tutorial does not exist.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTutorial(toCloseTutorial)) {
            throw new CommandException(String.format(MESSAGE_TUTORIAL_NOT_FOUND, toCloseTutorial.getSubject()));
        }

        List<Tutorial> fullTutorialList = model.getTutorialList();
        Tutorial tutorialToCloseFromList = fullTutorialList.stream()
                .filter(eachTutorial -> eachTutorial.equals(toCloseTutorial))
                .findFirst()
                .orElseThrow(() -> new CommandException(
                        String.format(MESSAGE_TUTORIAL_NOT_FOUND, toCloseTutorial.getSubject())));

        removeStudentsFromTutorialParticipation(model, tutorialToCloseFromList);
        model.deleteTutorial(tutorialToCloseFromList);

        return new CommandResult(String.format(
                MESSAGE_CLOSE_TUTORIAL_SUCCESS,
                Messages.formatTutorial(tutorialToCloseFromList))
        );
    }

    /**
     * Unenrolls all students from the given tutorial by executing {@code UnEnrollCommand} for each student.
     *
     * @param model    The {@code Model} on which the command operates.
     * @param tutorial The tutorial from which students will be unenrolled.
     * @throws CommandException if an error occurs during the unenrollment process.
     */
    private void removeStudentsFromTutorialParticipation(Model model, Tutorial tutorial) throws CommandException {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        List<Person> lastShownStudentList = model.getFilteredPersonList();
        List<Participation> tutorialParticipationCopy = new ArrayList<>(tutorial.getParticipationList());
        for (Participation eachParticipation : tutorialParticipationCopy) {
            Person student = eachParticipation.getStudent();
            Index studentIndex = getIndexOfPerson(lastShownStudentList, student);
            new UnEnrollCommand(studentIndex, tutorial.getSubject()).execute(model);
        }
    }

    /**
     * Finds the index of the given student in a list of all students.
     *
     * @param allPersons The list of all persons in the system.
     * @param student    The student whose index is to be found.
     * @return The index of the student.
     */
    private Index getIndexOfPerson(List<Person> allPersons, Person student) {
        int index = allPersons.indexOf(student);
        if (index == -1) {
            throw new PersonNotFoundException();
        }
        return Index.fromZeroBased(index);
    }

}
