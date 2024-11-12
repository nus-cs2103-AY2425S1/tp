package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCORE;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.person.Email;
import seedu.address.model.person.Github;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Telegram;
import seedu.address.model.tag.Tag;

/**
 * Add assignment grades to an existing person in the address book.
 */
public class AddGradeCommand extends Command {
    public static final String COMMAND_WORD = "addGrade";
    public static final String COMMAND_WORD_SHORT_FORM = "ag";
    public static final String MESSAGE_USAGE =
            "Adds a grade of an assignment to the person.\n"
            + "Command: " + COMMAND_WORD + " or " + COMMAND_WORD_SHORT_FORM + "\n"
            + "Parameters: "
            + PREFIX_NAME
            + "NAME "
            + PREFIX_ASSIGNMENT
            + "ASSIGNMENT "
            + PREFIX_SCORE
            + "SCORE\n"
            + "Example: "
            + COMMAND_WORD
            + " "
            + PREFIX_NAME
            + "John Doe "
            + PREFIX_ASSIGNMENT
            + "Ex01 "
            + PREFIX_SCORE
            + "9\n"
            + "Example: "
            + COMMAND_WORD_SHORT_FORM
            + " "
            + PREFIX_NAME.getShortPrefix()
            + "John Doe "
            + PREFIX_ASSIGNMENT.getShortPrefix()
            + "Ex01 "
            + PREFIX_SCORE.getShortPrefix()
            + "9";
    public static final String COMMAND_WORD_LOWER_CASE = "addgrade";
    public static final String MESSAGE_SUCCESS = "New assignment added: %1$s";
    public static final String HELP_MESSAGE =
            "Input addGrade without any fields to see list of assignments specified in database.";
    private static final AddGradeCommand showAssignmentDefault = new AddGradeCommand(true);

    private final Name personName;
    private final Float score;
    private final String assignmentName;
    private boolean showAssignments;


    private AddGradeCommand(boolean showAssignments) {
        this.personName = null;
        this.score = null;
        this.assignmentName = null;
        this.showAssignments = showAssignments;
    }

    /**
     * @param personName     Name of the person.
     * @param score          Score of the assignment.
     * @param assignmentName Name of assignment.
     */
    public AddGradeCommand(Name personName, Float score, String assignmentName) {
        requireAllNonNull(personName, score, assignmentName);
        this.personName = personName;
        this.score = score;
        this.assignmentName = assignmentName;
    }

    public static AddGradeCommand showAssignmentDefault() {
        return showAssignmentDefault;
    }

    /**
     * Creates a new {@code Person} object with an updated assignment and score added to the existing person's record.
     *
     * This method updates the assignments of the given person by adding a new assignment with the specified name and
     * score.
     * A new {@code Person} object is returned with the updated assignments.
     *
     * @param person The original {@code Person} object to which the grade will be added.
     * @param assignmentName The name of the assignment to be added.
     * @param score The score for the assignment to be added.
     * @return A new {@code Person} object with the updated assignment and score.
     */
    private static Person createGradeToAddToPerson(Person person, String assignmentName, float score) {
        assert person != null;
        Name name = person.getName();
        Email email = person.getEmail();
        Set<Tag> tags = person.getTags();
        Telegram telegram = person.getTelegram();
        Github github = person.getGithub();
        Set<Integer> weeksAttended = person.getWeeksPresent();

        Map<String, Assignment> assignment = person.getAssignment();
        assignment.put(assignmentName, new Assignment(assignmentName, score));

        return new Person(name, email, telegram, github, assignment, weeksAttended, tags);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (showAssignments) {
            return new CommandResult(model.getPredefinedAssignments().toString());
        }

        // check if assignment is in predefined list
        if (!model.hasAssignment(assignmentName)) {
            throw new CommandException("Invalid assignment name: " + assignmentName + "\n" + HELP_MESSAGE);
        }

        // check if score is valid
        if (score > model.getMaxScore(assignmentName) || score < 0) {
            throw new CommandException("Score must be between 0.0 and " + model.getMaxScore(assignmentName));
        }

        Person person = model.getPerson(personName)
                .orElseThrow(() ->
                        new CommandException("Person " + personName + " not in address book"));

        model.setPerson(person, createGradeToAddToPerson(person, model.getAssignmentName(assignmentName), score));
        return new CommandResult(String.format(MESSAGE_SUCCESS, assignmentName));
    }

    @Override
    public String toString() {
        if (showAssignments) {
            return "Show assignments command";
        }

        return personName + " " + assignmentName + " " + score;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof AddGradeCommand otherCommand) {
            return Objects.equals(otherCommand.personName, personName)
                    && Objects.equals(otherCommand.assignmentName, assignmentName)
                    && Objects.equals(otherCommand.score, score)
                    && otherCommand.showAssignments == showAssignments;
        }
        return false;
    }
}
