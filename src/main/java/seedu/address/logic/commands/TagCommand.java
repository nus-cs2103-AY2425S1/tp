package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Adds tags to an existing person in the address book
 */
public class TagCommand extends Command {
    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds new one-word tags to the person "
            + "identified by their index number. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TAG + "TAG (specify at least 1)... \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TAG + "photographer foodCaterer";

    public static final String MESSAGE_TAG_PERSON_SUCCESS = "Tagged person: %1$s with tags: %2$s";

    private final Index targetIndex;
    private final Set<Tag> addedTags;


    /**
     * @param index The Index of the person to tag
     * @param tagSet The Set of Tags to add to the person
     */
    public TagCommand(Index index, Set<Tag> tagSet) {
        this.targetIndex = index;
        this.addedTags = tagSet;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToTag = lastShownList.get(targetIndex.getZeroBased());
        String addedTagsString = Tag.tagSetToString(addedTags);
        for (Tag t:addedTags) {
            if (personToTag.getTags().contains(t)) {
                throw new CommandException("Person already has that Tag!");
            }
        }
        // Union of existing tags and new tags
        addedTags.addAll(personToTag.getTags());

        // Creating new Person
        Person newPerson = new Person(personToTag.getId(), personToTag.getName(), personToTag.getPhone(),
                personToTag.getEmail(), personToTag.getAddress(), addedTags);

        if (newPerson.getTags().size() > 6) {
            throw new CommandException("Each person can only have up to 6 tags!");
        }

        // Updating addressBook
        model.setPerson(personToTag, newPerson);
        model.getActiveTags().incrementTags(addedTags);
        return new CommandResult(String.format(MESSAGE_TAG_PERSON_SUCCESS, personToTag.getName(), addedTagsString));
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TagCommand)) {
            return false;
        }

        TagCommand otherTagCommand = (TagCommand) other;
        return Objects.equals(this.targetIndex, otherTagCommand.targetIndex)
                && Objects.equals(this.addedTags, otherTagCommand.addedTags);
    }
}
