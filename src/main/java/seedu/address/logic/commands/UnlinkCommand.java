package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CHILD;

import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Parent;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Student;
import seedu.address.model.person.exceptions.IllegalPersonTypeException;
import seedu.address.model.tag.Education;
import seedu.address.model.tag.Grade;
import seedu.address.model.tag.Tag;

/**
 * Removes any links to and from a person identified with an index.
 */
public class UnlinkCommand extends Command {

    public static final String COMMAND_WORD = "unlink";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": removes any links involving the Student provided.\n"
            + "Parameters: " + PREFIX_CHILD + "CHILD_NAME\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_CHILD + "John Doe";

    public static final String MESSAGE_UNLINK_CONTACT_SUCCESS = "Successfully unlinked %1$s from %2$s";
    public static final String MESSAGE_CONTACT_HAS_NO_LINKS = "Student: %1$s has no links";
    public static final String MESSAGE_CHILD_NOT_FOUND = "Student: %1$s does not exist in Address Book";
    public static final String MESSAGE_PARENT_NOT_FOUND = "Student's Parent: %1$s does not exist in Address Book";

    private final Name name;

    /**
     * Creates an UnlinkCommand to unlink the person at the specified {@code Index}.
     */
    public UnlinkCommand(Name name) {
        requireNonNull(name);
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Person child;
        try {
            child = model.personFromName(name);
            if (!(child instanceof Student)) {
                throw new CommandException(generateChildNotFoundMessage());
            }
        } catch (IllegalValueException e) {
            throw new CommandException(generateChildNotFoundMessage());
        }

        Student castedChild = (Student) child;
        Name parentName = castedChild.getParentName();
        if (parentName == null) {
            throw new CommandException(String.format(MESSAGE_CONTACT_HAS_NO_LINKS, Messages.format(castedChild)));
        }

        Person parent;
        try {
            parent = model.personFromName(parentName);
            if (!(parent instanceof Parent)) {
                throw new CommandException(generateParentNotFoundMessage(parentName));
            }
        } catch (IllegalValueException e) {
            throw new CommandException(generateParentNotFoundMessage(parentName));
        }

        Person unlinkedStudent = unlink(child);
        Person unlinkedParent = unlink(parent);

        model.setPerson(child, unlinkedStudent);
        model.setPerson(parent, unlinkedParent);

        return new CommandResult(String.format(MESSAGE_UNLINK_CONTACT_SUCCESS,
                Messages.format(child), Messages.format(parent)));
    }

    private Person unlink(Person person) {
        Name name = person.getName();
        Phone phone = person.getPhone();
        Email email = person.getEmail();
        Address address = person.getAddress();
        Set<Tag> tags = person.getTags();
        boolean isPinned = person.isPinned();
        boolean isArchived = person.isArchived();

        if (person instanceof Student student) {
            Education education = student.getEducation();
            Grade grade = student.getGrade();

            return new Student(name, phone, email, address, education, grade, null, tags, isPinned, isArchived);
        }

        if (person instanceof Parent parent) {
            return new Parent(name, phone, email, address, null, tags, isPinned, isArchived);
        }

        throw new IllegalPersonTypeException(person);
    }

    private String generateChildNotFoundMessage() {
        return String.format(MESSAGE_CHILD_NOT_FOUND, name.fullName);
    }

    private String generateParentNotFoundMessage(Name name) {
        return String.format(MESSAGE_PARENT_NOT_FOUND, name.fullName);
    }
}
