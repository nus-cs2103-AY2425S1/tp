package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Filters the list of contacts displayed based on a given tag
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters contacts for a given tag. "
            + "The given tag must be an exact match with the intended tag to find. "
            + "If there is no contact with the given tag, "
            + "an empty list of contacts will be displayed.\n"
            + "Parameters: TAG\n"
            + "Example: " + COMMAND_WORD + " supplier";

    public static final String MESSAGE_SUCCESS = "Filtered for tag: %s";
    public static final String MESSAGE_FAILURE = "Unable to filter. "
            + "Please ensure that provided tag is strictly alphanumeric, without any spaces.\n"
            + "Listing all contacts instead.";

    private Optional<Tag> tag;

    /**
     * Creates a FilterCommand, filtering for the given {@code tag}
     * @param tag
     */
    public FilterCommand(String tag) {
        requireAllNonNull(tag);

        try {
            this.tag = Optional.of(new Tag(tag));
        } catch (IllegalArgumentException e) {
            this.tag = Optional.empty();
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Predicate<Person> filterPredicate = this.tag
                .map(tagItem -> (Predicate<Person>) person -> person.getTags().contains(tagItem))
                .orElse(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredPersonList(filterPredicate);
        return new CommandResult(this.tag
                .map(tagItem -> String.format(MESSAGE_SUCCESS, tagItem.tagName))
                .orElse(MESSAGE_FAILURE));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FilterCommand)) {
            return false;
        }

        FilterCommand e = (FilterCommand) other;
        return this.tag.equals(e.tag);
    }
}
