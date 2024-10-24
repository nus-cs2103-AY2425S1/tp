package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Deletes a person identified using its tag name in the Wedlinker.
 */
public class DeleteTagCommand extends Command {
    public static final String COMMAND_WORD = "delete-tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the tag identified by the tag name.\n"
            + "Parameters: " + PREFIX_TAG + "TAG (must exist in the AddressBook)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TAG + "florist";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted Tag: %1$s.";
    public static final String MESSAGE_DELETE_TAG_FAILURE_STILL_TAGGED = "The Tag: %1$s is still used.";
    public static final String MESSAGE_DELETE_TAG_FAILURE_NOT_FOUND = "The Tag: %1$s does not exist.";

    private final Tag targetTag;
    private boolean force = false;

    /**
     * Initialises a DeleteTagCommand object with default force is false
     * @param targetTag A tag object as the target
     */
    public DeleteTagCommand(Tag targetTag) {
        this.targetTag = targetTag;
    }

    /**
     * Initialises a DeleteTagCommand object with a specific force
     * @param targetTag A Tag object as the target
     * @param force A boolean representing if the command should be forced
     */
    public DeleteTagCommand(Tag targetTag, boolean force) {
        this.targetTag = targetTag;
        this.force = force;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tag> allTags = model.getFilteredTagList();

        for (Tag tag : allTags) {
            if (tag.getTagName().equals(targetTag.getTagName())) {
                if (tag.canBeDeleted()) {
                    model.deleteTag(tag);
                    return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, Messages.format(targetTag)));
                } else {
                    if (this.force) {
                        for (Person person : model.getFilteredPersonList()) {
                            HashSet<Tag> personTags = new HashSet<>(person.getTags());
                            if (personTags.contains(tag)) {
                                personTags.remove(tag);
                                Person newPerson = new Person(
                                        person.getName(),
                                        person.getPhone(),
                                        person.getEmail(),
                                        person.getAddress(),
                                        personTags,
                                        person.getWeddings(),
                                        person.getTasks()
                                );
                                model.setPerson(person, newPerson);
                                model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
                            }
                        }
                        model.deleteTag(tag);
                        return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, Messages.format(targetTag)));
                    } else {
                        throw new CommandException(
                                String.format(MESSAGE_DELETE_TAG_FAILURE_STILL_TAGGED, Messages.format(targetTag))
                                        + "\n"
                                        + Messages.MESSAGE_FORCE_DELETE_TAG);
                    }
                }
            }
        }
        model.updateFilteredTagList(Model.PREDICATE_SHOW_ALL_TAGS);
        throw new CommandException(String.format(MESSAGE_DELETE_TAG_FAILURE_NOT_FOUND, Messages.format(targetTag)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteTagCommand)) {
            return false;
        }

        DeleteTagCommand otherDeleteTagCommand = (DeleteTagCommand) other;
        return targetTag.equals(otherDeleteTagCommand.targetTag);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetTag", targetTag)
                .toString();
    }
}
