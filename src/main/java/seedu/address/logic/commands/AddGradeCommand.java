package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCORE;

import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Add assignment grades to an existing person in the address book.
 */
public class AddGradeCommand extends Command {
    public static final String COMMAND_WORD = "addGrade";
    public static final String MESSAGE_USAGE =
            COMMAND_WORD
                    + ": Adds a grade of an assignment to the person. "
                    + "Parameters: "
                    + PREFIX_NAME
                    + "NAME "
                    + PREFIX_ASSIGNMENT
                    + "ASSIGNMENT "
                    + PREFIX_SCORE
                    + "SCORE "
                    + "Example: "
                    + COMMAND_WORD
                    + " "
                    + PREFIX_NAME
                    + "John Doe "
                    + PREFIX_ASSIGNMENT
                    + "Ex09 "
                    + PREFIX_SCORE
                    + "9 ";
    private final AddGradeCommandFormat addGradeCommandFormat;
    private Person person;

    /**
     * @param addGradeCommandFormat assignment and grades to add to the person.
     */
    public AddGradeCommand(AddGradeCommandFormat addGradeCommandFormat) {
        this.addGradeCommandFormat = addGradeCommandFormat;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // to add error handling

        person =
                model.getAddressBook().getPersonList().stream()
                        .filter(person -> person.getName().equals(addGradeCommandFormat.getName()))
                        .toList()
                        .get(0);
        model.setPerson(person, createGradeToAddToPerson());
        System.out.println(model.getAddressBook().getPersonList().toString());
        return new CommandResult(""); // placeholder string to be added
    }

    private Person createGradeToAddToPerson() {
        assert person != null;
        Name name = person.getName();
        Phone phone = person.getPhone();
        Email email = person.getEmail();
        Address address = person.getAddress();
        Set<Tag> tags = person.getTags();
        Assignment assignment =
                new Assignment(addGradeCommandFormat.assignment, addGradeCommandFormat.score);

        return new Person(name, phone, email, address, tags, assignment);
    }

    /**
     * Wrapper for the details of the assignment to be added and person to be added to.
     */
    public static class AddGradeCommandFormat {
        private Name name;
        private String assignment;
        private float score;

        public float getScore() {
            return score;
        }

        public void setScore(float score) {
            this.score = score;
        }

        public String getAssignment() {
            return assignment;
        }

        public void setAssignment(String assignment) {
            this.assignment = assignment;
        }

        public Name getName() {
            return name;
        }

        public void setName(Name name) {
            this.name = name;
        }
    }
}
