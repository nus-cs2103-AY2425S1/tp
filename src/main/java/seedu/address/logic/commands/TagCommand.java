package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

import java.util.List;
import java.util.Set;

/**
 * Adds tags to an existing person in the address book
 */
public class TagCommand extends Command {
    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds new tags to the person "
            + "identified by the index number used in the displayed person list. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TAG + "TAG... \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TAG + "friends"
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_TAG_PERSON_SUCCESS = "Tagged person: %1$s with tags: %2$s";

    private final Index targetIndex;
    private final Set<Tag> addedTags;


    public TagCommand(Index index, Set<Tag> tagList) {
        this.targetIndex = index;
        this.addedTags = tagList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToTag = lastShownList.get(targetIndex.getZeroBased());
        String addedTagsString = tagSetToString(addedTags);
        // Union of existing tags and new tags
        addedTags.addAll(personToTag.getTags());

        // Creating new Person
        Person newPerson = new Person(personToTag.getName(), personToTag.getPhone(),
                personToTag.getEmail(), personToTag.getAddress(), addedTags);

        // Updating addressBook
        model.setPerson(personToTag, newPerson);
        return new CommandResult(String.format(MESSAGE_TAG_PERSON_SUCCESS, personToTag.getName(), addedTagsString));
    }

    private String tagSetToString(Set<Tag> tagList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Tag t:tagList) {
            stringBuilder.append(t);
        }
        return stringBuilder.toString();
    }
}
