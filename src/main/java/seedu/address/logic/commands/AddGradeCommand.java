package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCORE;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

import java.util.Optional;
import java.util.Set;

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

    public AddGradeCommand(AddGradeCommandFormat addGradeCommandFormat) {
        this.addGradeCommandFormat = addGradeCommandFormat;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Find the person based on the provided name
        Optional<Person> personOptional = model.getAddressBook().getPersonList().stream()
                .filter(p -> p.getName().equals(addGradeCommandFormat.getName()))
                .findFirst();

        if (personOptional.isEmpty()) {
            throw new CommandException("Person not found");
        }

        Person person = personOptional.get();

        // Create a new Person object with the updated assignment
        Person updatedPerson = createGradeToAddToPerson(person);

        // Update the model with the new Person
        model.setPerson(person, updatedPerson);

        return new CommandResult("Grade added successfully for " + addGradeCommandFormat.getName());
    }

    private Person createGradeToAddToPerson(Person person) {
        Name name = person.getName();
        Phone phone = person.getPhone();
        Email email = person.getEmail();
        Address address = person.getAddress();
        Set<Tag> tags = person.getTags();

        // Create new assignment with the given score
        Assignment assignment = new Assignment(addGradeCommandFormat.assignment, addGradeCommandFormat.score);

        // Return a new Person object with the same details but with the updated assignment
        return new Person(name, phone, email, address, tags, assignment);
    }

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
