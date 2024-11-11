package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.RsvpStatus;
import seedu.address.model.person.RsvpedPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;


/**
 * Filters the address book by a given prefix and displays the filtered list to the user
 * Users can only filter by one field at a time
 */
public class FilterCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "filter";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters the displayed list with the given criteria\n"
            + "Parameters: [s/RSVPSTATUS] [t/TAG]...\n" + "At least one parameter must be provided.\n"
            + "Example: " + COMMAND_WORD + " s/1 t/bride's side";
    public static final String MESSAGE_TAG_NOT_CREATED = "must be created before being used to filter.";
    public static final String MESSAGE_FILTER_ALREADY_EXISTS = "is already being filtered.";

    private final Set<Tag> tagSet;
    private final Set<RsvpStatus> statusSet;
    private final Set<Predicate<Person>> predicateSet = new HashSet<>();
    private Predicate<Person> previousPredicate;

    private Predicate<Person> predicate = new Predicate<Person>() {
        @Override
        public boolean test(Person person) {
            return true;
        }
    };

    /**
     * Creates a FilterCommand with the given set of tags and RSVP statuses to filter by.
     */
    public FilterCommand(Set<Tag> tagSet, Set<RsvpStatus> statusSet) {
        requireNonNull(tagSet);
        requireNonNull(statusSet);
        this.tagSet = tagSet;
        this.statusSet = statusSet;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        previousPredicate = model.getCurrentPredicate();

        for (Tag tag: tagSet) {
            if (model.checkTagFilterAlreadyExists(tag)) {
                throw new CommandException(tag + " " + MESSAGE_FILTER_ALREADY_EXISTS);
            }

            if (!model.hasTag(tag)) {
                throw new CommandException("Tag " + tag + " " + MESSAGE_TAG_NOT_CREATED);
            }
        }

        for (RsvpStatus status: statusSet) {
            if (model.checkStatusFilterAlreadyExists(status)) {
                throw new CommandException("[" + status + "] " + MESSAGE_FILTER_ALREADY_EXISTS);
            }
        }

        for (Tag tag: tagSet) {
            if (!model.hasTag(tag)) {
                throw new CommandException("Tag " + tag + " " + MESSAGE_TAG_NOT_CREATED);
            }
            predicateSet.add(new TagContainsKeywordsPredicate(tag));
        }

        predicateSet.addAll(tagSet.stream().map(TagContainsKeywordsPredicate::new).toList());
        predicateSet.addAll(statusSet.stream().map(RsvpedPredicate::new).toList());

        for (Predicate<Person> predicateToAdd: predicateSet) {
            predicate = predicate.and(predicateToAdd);
        }

        model.addTagFilters(tagSet);
        model.addStatusFilters(statusSet);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public void undo(Model model) {
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        model.removeFilters(tagSet, statusSet);
        if (previousPredicate != null) {
            model.updateFilteredPersonList(previousPredicate);
        }
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
        return this.tagSet.equals(otherFilterCommand.tagSet) && this.statusSet.equals(otherFilterCommand.statusSet);
    }
}
