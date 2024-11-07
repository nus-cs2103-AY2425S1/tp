package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
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
import seedu.address.model.tag.Education;
import seedu.address.model.tag.Grade;
import seedu.address.model.tag.Tag;

/**
 * Deletes a person identified using its displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the persons identified by the index numbers used in the displayed person list.\n"
            + "Parameters: INDEX (one or more, all must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_DELETE_PEOPLE_SUCCESS = "Deleted People: \n%1$s";

    private final List<Index> targetIndices;

    public DeleteCommand(List<Index> targetIndices) {
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        List<Person> peopleToDelete = new ArrayList<>();
        for (Index index : targetIndices) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Person personToDelete = lastShownList.get(index.getZeroBased());
            peopleToDelete.add(personToDelete);
        }

        assert peopleToDelete.size() == targetIndices.size();

        List<String> resultMessages = new ArrayList<>();
        for (Person person : peopleToDelete) {
            removeLinks(person, model);
            model.deletePerson(person);
            resultMessages.add(Messages.format(person));
        }

        assert resultMessages.size() == targetIndices.size();

        if (resultMessages.size() == 1) {
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, resultMessages.get(0)));
        } else {
            return new CommandResult(String.format(MESSAGE_DELETE_PEOPLE_SUCCESS, String.join("\n", resultMessages)));
        }
    }

    private void removeLinks(Person person, Model model) throws CommandException {
        if (person instanceof Student student) {
            Person link = model.personFromName(student.getParentName());
            if (!(link instanceof Parent parent)) {
                throw new CommandException("Invalid linked parent found: " + Messages.format(link));
            }
            Parent unlinkedParent = createUnlinkedParent(parent, person);
            model.setPerson(parent, unlinkedParent);
        } else if (person instanceof Parent parent) {
            Set<Name> childrenNames = parent.getChildrensNames();
            Set<Student> children = new HashSet<>();

            for (Name childName : childrenNames) {
                Person link = model.personFromName(childName);
                if (!(link instanceof Student child)) {
                    throw new CommandException("Invalid linked child found: " + Messages.format(person));
                }
                children.add(child);
            }

            for (Student child : children) {
                Student unlinkedChild = createUnlinkedChild(child);
                model.setPerson(child, unlinkedChild);
            }
        } else {
            throw new IllegalPersonTypeException(person);
        }
    }

    private Student createUnlinkedChild(Student child) {
        Name name = child.getName();
        Phone phone = child.getPhone();
        Email email = child.getEmail();
        Address address = child.getAddress();
        LessonTime lessonTime = child.getLessonTime();
        Education education = child.getEducation();
        Grade grade = child.getGrade();
        Set<Tag> tags = child.getTags();
        boolean isPinned = child.isPinned();
        boolean isArchived = child.isArchived();

        return new Student(name, phone, email, address, lessonTime, education, grade, null, tags, isPinned, isArchived);
    }

    private Parent createUnlinkedParent(Parent parent, Person personToDelete) {
        Name parentName = parent.getName();
        Phone parentPhone = parent.getPhone();
        Email parentEmail = parent.getEmail();
        Address parentAddress = parent.getAddress();
        Set<Name> childrenNames = parent.getChildrensNames().stream().filter(name -> name != personToDelete.getName())
                .collect(Collectors.toSet());
        Set<Tag> parentTags = parent.getTags();
        boolean isPinned = parent.isPinned();
        boolean isArchived = parent.isArchived();

        return new Parent(parentName, parentPhone, parentEmail, parentAddress, childrenNames, parentTags, isPinned,
                isArchived);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetIndices.equals(otherDeleteCommand.targetIndices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("targetIndices", targetIndices).toString();
    }
}
