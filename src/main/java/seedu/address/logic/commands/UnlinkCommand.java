package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.LessonTime;
import seedu.address.model.person.Name;
import seedu.address.model.person.Parent;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Student;
import seedu.address.model.person.exceptions.IllegalPersonTypeException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Education;
import seedu.address.model.tag.Grade;
import seedu.address.model.tag.Tag;

/**
 * Removes any links to and from a person identified with an index.
 */
public class UnlinkCommand extends Command {

    public static final String COMMAND_WORD = "unlink";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": removes any links to and from the contact identified"
            + "by the index number used in the displayed contact list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNLINK_CONTACT_SUCCESS = "Successfully unlinked %1$s from %2$s";
    public static final String MESSAGE_CONTACT_HAS_NO_LINKS = "Contact: %1$s has no links";

    private final Index index;

    /**
     * Creates an UnlinkCommand to unlink the person at the specified {@code Index}.
     */
    public UnlinkCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Name counterPartName;

        if (personToEdit instanceof Student studentToEdit) {
            counterPartName = studentToEdit.getParentName();
        } else if (personToEdit instanceof Parent parentToEdit) {
            counterPartName = parentToEdit.getChildrensNames().stream().findFirst().get();
        } else {
            throw new IllegalPersonTypeException(personToEdit);
        }

        if (counterPartName == null) {
            throw new CommandException(String.format(MESSAGE_CONTACT_HAS_NO_LINKS, Messages.format(personToEdit)));
        }

        Person counterPart;
        try {
            counterPart = model.personFromName(counterPartName);
        } catch (IllegalValueException e) {
            throw new PersonNotFoundException();
        }

        Person unlinkedPerson = unlink(personToEdit);
        Person unlinkedCounterPart = unlink(counterPart);

        model.setPerson(personToEdit, unlinkedPerson);
        model.setPerson(counterPart, unlinkedCounterPart);

        return new CommandResult(String.format(MESSAGE_UNLINK_CONTACT_SUCCESS,
                Messages.format(personToEdit), Messages.format(counterPart)));
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
            LessonTime lessonTime = student.getLessonTime();
            Education education = student.getEducation();
            Grade grade = student.getGrade();

            return new Student(name, phone, email, address, lessonTime, education, grade, null,
                    tags, isPinned, isArchived);
        }

        if (person instanceof Parent parent) {
            return new Parent(name, phone, email, address, null, tags, isPinned, isArchived);
        }

        throw new IllegalPersonTypeException(person);
    }
}
