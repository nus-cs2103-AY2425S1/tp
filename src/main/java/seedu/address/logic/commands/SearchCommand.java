package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.person.EventIdsContainsIdsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.TempPredicate;

/**
 * Finds and lists all persons in address book whose specified field contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose specified field contain any "
            + "of the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: " + "{PREFIX}/KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " n/alice bob charlie";

    private final Predicate<Person> predicate;

    public SearchCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (!(this.predicate instanceof TempPredicate)) {
            model.updateFilteredPersonList(predicate);
            return new CommandResult(
                    String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
        }
        try {
            TempPredicate tempPredicate = (TempPredicate) this.predicate;
            List<String> keywords = tempPredicate.getKeywords();
            List<EventName> eventNames = keywords.stream().map(EventName::new).toList();
            List<Event> events = eventNames.stream().flatMap(
                    eventName -> model.findEventsWithName(eventName).stream()).toList();
            List<Integer> eventIds = events.stream().map(Event::getEventId).toList();
            List<Integer> uniqueEventIds = new HashSet<Integer>(eventIds).stream().toList();
            EventIdsContainsIdsPredicate eventIdsContainsIdsPredicate =
                    new EventIdsContainsIdsPredicate(uniqueEventIds);
            model.updateFilteredPersonList(eventIdsContainsIdsPredicate);
            return new CommandResult(
                    String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
        } catch (IllegalArgumentException e) {
            return new CommandResult(EventName.MESSAGE_CONSTRAINTS);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SearchCommand)) {
            return false;
        }

        SearchCommand otherSearchCommand = (SearchCommand) other;
        return predicate.equals(otherSearchCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
