package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.person.predicates.PersonContainsTagsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Edit all person with specified tag to change the specified tag with new tag.
 */
public class BatchEditCommand extends Command {
    public static final String COMMAND_WORD = "batch-edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the specified tag with new tag\n"
            + "Parameters: "
            + PREFIX_TAG + "OLD_TAG" + " "
            + PREFIX_TAG + "NEW_TAG\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TAG + "sec2 " + PREFIX_TAG + "sec3";

    public static final String MESSAGE_BATCH_EDIT_EACH_PERSON_CHANGED = "Tag Changed: %s -> %s";
    public static final String MESSAGE_BATCH_EDIT_NO_PERSON_WITH_TAG = "No person with Tag(s) %s is found";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private final Tag oldTag;
    private final Tag newTag;
    private final PersonContainsTagsPredicate predicate;

    /**
     * Initializes command to batch edit all person with specified tag with new tag from the address book.
     *
     * @param oldTag    The current tag the person has.
     * @param newTag    The new tag to replace the current tag with.
     * @param predicate Predicate to find all person with the specified tag.
     */
    public BatchEditCommand(Tag oldTag, Tag newTag, PersonContainsTagsPredicate predicate) {
        requireNonNull(oldTag);
        requireNonNull(newTag);
        requireNonNull(predicate);

        this.oldTag = oldTag;
        this.newTag = newTag;
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("----------------Execute batch-edit----------------");
        requireNonNull(model);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredPersonList(predicate);
        ArrayList<Person> nonObservableList = new ArrayList<>(model.getFilteredPersonList());
        StringBuilder feedbackToUser = new StringBuilder();

        if (nonObservableList.isEmpty()) {
            logger.info("No Person edited");
            return new CommandResult(String.format(MESSAGE_BATCH_EDIT_NO_PERSON_WITH_TAG, oldTag));
        }
        feedbackToUser.append(String.format(MESSAGE_BATCH_EDIT_EACH_PERSON_CHANGED, oldTag, newTag));

        switchTagListOfPerson(nonObservableList, model);

        logger.info("Person(s) edited: "
                + nonObservableList.stream().map(person -> person.getName().toString()).toList());

        PersonContainsTagsPredicate newPredicate = new PersonContainsTagsPredicate(Set.of(newTag));
        model.updateFilteredPersonList(newPredicate);

        logger.info("----------------Execute batch-edit successful----------------");
        return new CommandResult(feedbackToUser.toString());
    }

    private void switchTagListOfPerson(ArrayList<Person> personList, Model model) {
        for (Person person : personList) {
            if (person instanceof Student student) {
                Student updatedStudent = changeTagStudent(student);
                model.setPerson(person, updatedStudent);
            } else {
                Person updatedPerson = changeTagPerson(person);
                model.setPerson(person, updatedPerson);
            }
        }
    }

    private Set<Tag> switchTag(Person person) {
        Set<Tag> withoutOldTag = person
                .getTags()
                .stream()
                .filter(tag -> !tag.equals(this.oldTag)).collect(Collectors.toSet());
        withoutOldTag.add(this.newTag);
        return withoutOldTag;
    }

    private Person changeTagPerson(Person person) {
        Set<Tag> newTagList = switchTag(person);
        return new Person(
                person.getName(),
                person.getSex(),
                person.getRole(),
                person.getPhone(),
                person.getEmail(),
                person.getAddress(),
                newTagList
        );
    }

    private Student changeTagStudent(Student student) {
        Set<Tag> newTagList = switchTag(student);
        return new Student(
                student.getName(),
                student.getSex(),
                student.getRole(),
                student.getPhone(),
                student.getEmail(),
                student.getAddress(),
                newTagList,
                student.getAttendanceCount()
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof BatchEditCommand)) {
            return false;
        }

        BatchEditCommand otherBatchEditCommand = (BatchEditCommand) other;
        return otherBatchEditCommand.newTag.equals(this.newTag)
                && otherBatchEditCommand.oldTag.equals(this.oldTag)
                && otherBatchEditCommand.predicate.equals(this.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("oldTag", oldTag)
                .add("newTag", newTag)
                .add("predicate", predicate)
                .toString();
    }
}
