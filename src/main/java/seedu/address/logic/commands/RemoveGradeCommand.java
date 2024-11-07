package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_ASSIGNMENT_NAME;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.ParserUtil.parseName;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.person.Email;
import seedu.address.model.person.Github;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Telegram;
import seedu.address.model.tag.Tag;

/**
 * Remove assignment grades from an existing person in the address book.
 */
public class RemoveGradeCommand extends Command {
    public static final String COMMAND_WORD = "removeGrade";
    public static final String COMMAND_WORD_LOWER_CASE = "removegrade";

    public static final String COMMAND_WORD_SHORT_FORM = "rg";

    public static final String MESSAGE_USAGE =
                    "Removes a grade of an assignment from the person.\n"
                        + "Command: " + COMMAND_WORD + " or " + COMMAND_WORD_SHORT_FORM + "\n"
                    + "Parameters: "
                    + PREFIX_NAME
                    + "NAME "
                    + PREFIX_ASSIGNMENT
                    + "ASSIGNMENT\n"
                    + "Example: "
                    + COMMAND_WORD
                    + " "
                    + PREFIX_NAME
                    + "John Doe "
                    + PREFIX_ASSIGNMENT
                    + "Ex01\n"
                    + "Example: "
                    + COMMAND_WORD_SHORT_FORM
                    + " "
                    + PREFIX_NAME.getShortPrefix()
                    + "John Doe "
                    + PREFIX_ASSIGNMENT.getShortPrefix()
                    + "Ex01";

    public static final String MESSAGE_SUCCESS = "Assignment %1$s removed from %2$s";
    public static final String MESSAGE_FAILURE = "Assignment %s does not exist for %s.";
    private final Name personName;
    private final String assignmentName;

    /**
     * @param personName     Name of the person.
     * @param assignmentName Name of assignment.
     */
    public RemoveGradeCommand(String personName, String assignmentName) throws ParseException {
        requireAllNonNull(personName, assignmentName);
        this.personName = parseName(personName);
        this.assignmentName = assignmentName;
    }


    static Person createPersonWithRemovedGrade(Person person, String assignmentName) {
        assert person != null;
        Name name = person.getName();
        Email email = person.getEmail();
        Set<Tag> tags = person.getTags();
        Telegram telegram = person.getTelegram();
        Github github = person.getGithub();
        Set<Integer> weeksAttended = person.getWeeksPresent();

        Map<String, Assignment> assignment = person.getAssignment();
        assignment.remove(assignmentName);

        return new Person(name, email, telegram, github, assignment, weeksAttended, tags);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // check if assignment is in predefined list
        if (!model.hasAssignment(assignmentName)) {
            throw new CommandException(String.format(MESSAGE_INVALID_ASSIGNMENT_NAME, assignmentName));
        }

        Person person = model.getPerson(personName)
                .orElseThrow(() ->
                        new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_NAME));

        // Check if the assignment is already missing from the person's record
        if (!person.getAssignment().containsKey(assignmentName)) {
            throw new CommandException(
                    String.format(MESSAGE_FAILURE, assignmentName, personName));
        }

        model.setPerson(person, createPersonWithRemovedGrade(person, model.getAssignmentName(assignmentName)));
        return new CommandResult(String.format(MESSAGE_SUCCESS, assignmentName, personName));
    }

    @Override
    public String toString() {
        return personName + " " + assignmentName;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof RemoveGradeCommand otherCommand) {
            return Objects.equals(otherCommand.personName, personName)
                    && Objects.equals(otherCommand.assignmentName, assignmentName);
        }
        return false;
    }
}
