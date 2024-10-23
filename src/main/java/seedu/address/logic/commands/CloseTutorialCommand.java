package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.index.Index.fromOneBased;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tutorial.Tutorial;

public class CloseTutorialCommand extends Command {

    public static final String COMMAND_WORD = "closetut";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Closes the tutorial identified by the subject used in the displayed tutorial list.\n"
            + "Parameters: "
            + PREFIX_TUTORIAL + "SUBJECT_NAME\n"
            + "Example: " + COMMAND_WORD
            + PREFIX_TUTORIAL + "Chemistry";

    public static final String MESSAGE_CLOSE_TUTORIAL_SUCCESS = "Successfully closed tutorial: %1$s class";
    public static final String MESSAGE_TUTORIAL_NOT_FOUND = "No tutorial class with the name %1$s is found.";

    private final Tutorial toClose;
    public CloseTutorialCommand(Tutorial tutorial) {
        requireNonNull(tutorial);
        toClose = tutorial;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTutorial(toClose)) {
            throw new CommandException(String.format(MESSAGE_TUTORIAL_NOT_FOUND, toClose.getSubject()));
        }

        Tutorial tutorialToCloseFromList = model.getTutorial(toClose);
        removeStudentsFromTutorialParticipation(model, tutorialToCloseFromList);
        model.closeTutorial(tutorialToCloseFromList);

        return new CommandResult(String.format(
                MESSAGE_CLOSE_TUTORIAL_SUCCESS,
                Messages.formatTutorial(tutorialToCloseFromList))
        );
    }

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

    private Index getIndexOfPerson(List<Person> allPersons, Person student) throws CommandException {
        int index = allPersons.indexOf(student);
        if (index == -1) {
            throw new PersonNotFoundException();
        }
        return Index.fromZeroBased(index);
    }

}
