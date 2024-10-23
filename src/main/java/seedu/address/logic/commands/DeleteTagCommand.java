package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Adds a new predefined tag.
 */
public class DeleteTagCommand extends Command {
    public static final String COMMAND_WORD = "deletetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an existing tag (case insensitive).\n"
            + "Parameters: TAG_NAME (MAX 50 alphanumeric characters, spaces, parenthesis and apostrophes)\n"
            + "Example: " + COMMAND_WORD + " t/bride's friend t/groom's friend";

    public static final String MESSAGE_SUCCESS = "Tag(s) deleted: ";
    public static final String YOUR_TAGS_PREFIX = "Your tags: ";
    public static final String MESSAGE_NONEXISTENT = "Some tag(s) provided have not been added before.\n";
    private final List<Tag> tags;

    /**
     * @param tags The tag object to be added.
     */
    public DeleteTagCommand(List<Tag> tags) {
        requireAllNonNull(tags);
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);
        boolean isSuccessful = model.deleteTags(tags);
        if (!isSuccessful) {
            throw new CommandException(MESSAGE_NONEXISTENT);
        }

        for (Tag tag : tags) {
            removeTagFromPersons(model, tag);
        }

        String successMessage = MESSAGE_SUCCESS + tags + "\n";
        String currentTags = YOUR_TAGS_PREFIX + model.getTagList();
        return new CommandResult(successMessage + currentTags);
    }

    /**
     * Removes the deleted {@code Tag} from all persons in the address book.
     */
    private void removeTagFromPersons(Model model, Tag tag) {
        List<Person> persons = model.getFullPersonList();
        for (Person person : persons) {
            if (person.hasTag(tag)) {
                replacePerson(model, person, tag);
            }
        }
    }

    private void replacePerson(Model model, Person person, Tag tag) {
        Set<Tag> newTags = new HashSet<>(person.getTags());
        newTags.remove(tag);

        Person updatedPerson = new Person(person.getName(), person.getPhone(),
                person.getEmail(), person.getRsvpStatus(), newTags);
        model.setPerson(person, updatedPerson);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof DeleteTagCommand)) {
            return false;
        }

        DeleteTagCommand otherCommand = (DeleteTagCommand) other;
        return tags.equals(otherCommand.tags);
    }
}
