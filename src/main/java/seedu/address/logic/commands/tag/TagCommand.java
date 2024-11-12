package seedu.address.logic.commands.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Vendor;
import seedu.address.model.tag.Tag;

/**
 * Adds a tag to an existing person in the Wedlinker.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds one or multiple tags to the person identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TAG + "TAG... (can specify multiple tags)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TAG + "florist " + PREFIX_TAG + "photographer.";

    private final Index index;
    private final HashSet<Tag> tagsToAdd;
    private boolean force = false;

    /**
     * Constructs a {@code TagCommand} to add tags to a person.
     *
     * @param index The index of the person in the person list.
     * @param tagsToAdd The list of tags to be added.
     */
    public TagCommand(Index index, HashSet<Tag> tagsToAdd) {
        requireNonNull(index);
        requireNonNull(tagsToAdd);
        this.index = index;
        this.tagsToAdd = tagsToAdd;
    }

    /**
     * Constructs a {@code TagCommand} to add tags to a person.
     *
     * @param index The index of the person in the person list.
     * @param tagsToAdd The list of tags to be added.
     * @param force A boolean representing if the command should be forced.
     */
    public TagCommand(Index index, HashSet<Tag> tagsToAdd, boolean force) {
        requireNonNull(index);
        requireNonNull(tagsToAdd);
        this.index = index;
        this.tagsToAdd = tagsToAdd;
        this.force = force;
    }

    /**
     * Generates a command execution success message showing the added tags and the person.
     *
     * @param personToEdit The person to whom the tags were added.
     * @return A success message indicating the tags that were added and the name of the person.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String addedTags = tagsToAdd.stream()
                .map(tag -> tag.toString().replaceAll("[\\[\\]]", ""))
                .collect(Collectors.joining(", "));
        return String.format(
                Messages.MESSAGE_ASSIGN_TAG_SUCCESS, addedTags, personToEdit.getName().toString()
        );
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (lastShownList.isEmpty()) {
            throw new CommandException(String.format(Messages.MESSAGE_NOTHING_TO_PERFORM_ON, "contacts", COMMAND_WORD));
        } else if (index.getZeroBased() >= lastShownList.size() || index.getZeroBased() < 0) {
            throw new CommandException(String.format(
                    Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, 1, lastShownList.size()
            ));
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Set<Tag> updatedTags = new HashSet<>(personToEdit.getTags());

        for (Tag tag : tagsToAdd) {
            if (personToEdit.hasTag(tag)) {
                throw new CommandException(Messages.MESSAGE_CONTACT_ALREADY_TAGGED);
            }
        }

        for (Tag tag : tagsToAdd) {
            if (!model.hasTag(tag)) {
                if (this.force) {
                    CreateTagCommand createTagCommand = new CreateTagCommand(tag);
                    createTagCommand.execute(model);
                    tag.increaseTaggedCount();
                    updatedTags.add(tag);
                } else {
                    throw new CommandException(
                            Messages.MESSAGE_TAG_NOT_FOUND + "\n" + Messages.MESSAGE_FORCE_TAG_TO_CONTACT
                    );
                }
            } else {
                Tag tagToEdit = model.getTag(tag);
                tagToEdit.increaseTaggedCount();
                updatedTags.remove(tagToEdit);
                updatedTags.add(tagToEdit);
            }
        }

        Person editedPerson;
        if (personToEdit instanceof Vendor) {
            editedPerson = new Vendor(
                    personToEdit.getName(),
                    personToEdit.getPhone(),
                    personToEdit.getEmail(),
                    personToEdit.getAddress(),
                    updatedTags,
                    personToEdit.getWeddings(),
                    personToEdit.getTasks());
        } else {
            editedPerson = new Person(
                    personToEdit.getName(),
                    personToEdit.getPhone(),
                    personToEdit.getEmail(),
                    personToEdit.getAddress(),
                    updatedTags,
                    personToEdit.getWeddings(),
                    personToEdit.getTasks());
        }

        model.setPerson(personToEdit, editedPerson);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        TagCommand otherCommand = (TagCommand) other;
        return index.equals(otherCommand.index)
                && tagsToAdd.equals(otherCommand.tagsToAdd);
    }


}
