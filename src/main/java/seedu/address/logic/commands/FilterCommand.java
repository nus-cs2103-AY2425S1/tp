package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonTaggedWithPredicate;
import seedu.address.model.tag.Tag;

/**
 * Displays a list of contacts who have been tagged with the specified tag.
 */
public class FilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": filters contacts by tags. \n"
            + "Parameters: t/ [TAG]\n"
            + "Example: " + COMMAND_WORD + "t/ friends";

    public static final String MESSAGE_SUCCESS =
            "Listed all contacts with tag: %1$s \n"
            + "Type \"list\" to see all contacts.";

    public final Predicate<Person> predicatePersonTaggedWithTag;

    private final Tag tag;

    /**
     * Creates a FilterCommand object with the specified tag.
     * @param tag
     */
    public FilterCommand(Tag tag) {
        this.tag = tag;
        this.predicatePersonTaggedWithTag = new PersonTaggedWithPredicate(this.tag);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(predicatePersonTaggedWithTag);
        return new CommandResult(String.format(MESSAGE_SUCCESS, tag));
    }
}
