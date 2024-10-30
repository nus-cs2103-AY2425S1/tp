package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonInWeddingPredicate;
import seedu.address.model.person.PersonTaggedWithPredicate;
import seedu.address.model.tag.Tag;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;

/**
 * Displays a list of contacts who have been tagged with the specified tag.
 */
public class FilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": filters contacts by one or more tags. \n"
            + "Parameters: " + PREFIX_TAG + "TAG1 [TAG2] [TAG3] ..\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TAG + "photographer videographer";

    public static final String MESSAGE_SUCCESS =
            "Listed all contacts with tags: %1$s \n"
            + "Type \"list\" to see all contacts.";

    public static final String MESSAGE_DUPLICATE_PREFIXES = "Please only include one prefix " + PREFIX_TAG + " !";

    public final Predicate<Person> predicatePersonTaggedWithTag;

    private final Set<Tag> tagSet;

    /**
     * Creates a FilterCommand object with the specified tag.
     * @param tagSet
     */
    public FilterCommand(Set<Tag> tagSet) {
        this.tagSet = tagSet;
        this.predicatePersonTaggedWithTag = new PersonTaggedWithPredicate(this.tagSet);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        WeddingName currentWeddingName = model.getCurrentWeddingName().get();
        if (currentWeddingName == null) {
            model.updateFilteredPersonList(predicatePersonTaggedWithTag);
        } else {
            Wedding currentWedding = model.getFilteredWeddingList().filtered(
                    w -> w.getWeddingName().equals(currentWeddingName)).get(0);
            PersonInWeddingPredicate personInWeddingPredicate = new PersonInWeddingPredicate(currentWedding);
            model.updateFilteredPersonList(p -> predicatePersonTaggedWithTag.test(p)
                    && personInWeddingPredicate.test(p));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, tagSet));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterCommand)) {
            return false;
        }

        FilterCommand otherFilterCommand = (FilterCommand) other;
        return tagSet.equals(otherFilterCommand.tagSet);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("tagSet", tagSet)
                .toString();
    }
}
