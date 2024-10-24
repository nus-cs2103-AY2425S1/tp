package seedu.ddd.model.event.common.predicate;

import static seedu.ddd.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_ID;

import java.util.Arrays;
import java.util.function.Predicate;

import seedu.ddd.commons.util.ToStringBuilder;
import seedu.ddd.logic.commands.ListEventCommand;
import seedu.ddd.logic.parser.ArgumentMultimap;
import seedu.ddd.logic.parser.exceptions.ParseException;
import seedu.ddd.model.event.common.Event;
import seedu.ddd.model.event.common.EventId;

/**
 * Builds a chain of predicates to the List event command depending on the PREFIX present in argMultimap.
 */
public class EventPredicateBuilder {
    private ArgumentMultimap argMultimap;

    public EventPredicateBuilder(ArgumentMultimap argMultimap) {
        this.argMultimap = argMultimap;
    }
    /**
     * Builds a combined {@code Predicate<Event>} based on all the provided flags and prefixes.
     * The method dynamically chains predicates to filter events.
     *
     * @return a {@code Predicate<Event>} that filters contacts based on multiple criteria.
     * @throws ParseException if any input is invalid.
     */
    public Predicate<Event> build() throws ParseException {
        Predicate<Event> combinedPredicate = event -> true; // Start with a default predicate (all events).

        // Check for each prefix and chain predicates accordingly.
        combinedPredicate = addDescriptionPredicate(argMultimap, combinedPredicate);
        combinedPredicate = addIdPredicate(argMultimap, combinedPredicate);

        return combinedPredicate;
    }
    private Predicate<Event> addDescriptionPredicate(ArgumentMultimap argMultimap, Predicate<Event> combinedPredicate)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_DESC).isPresent()) {
            String trimmedArgs = argMultimap.getValue(PREFIX_DESC).get().trim();
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListEventCommand.MESSAGE_EVENT_USAGE));
            }
            String[] descriptionKeywords = trimmedArgs.split("\\s+");
            combinedPredicate = combinedPredicate.and(
                    new DescriptionContainsKeywordsPredicate(Arrays.asList(descriptionKeywords)));
        }
        return combinedPredicate;
    }
    private Predicate<Event> addIdPredicate(ArgumentMultimap argMultimap, Predicate<Event> combinedPredicate)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_ID).isPresent()) {
            String trimmedArgs = argMultimap.getValue(PREFIX_ID).get();
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListEventCommand.MESSAGE_EVENT_USAGE));
            }
            EventId eventId = new EventId(trimmedArgs);
            combinedPredicate = combinedPredicate.and(new EventIdPredicate(eventId));
        }
        return combinedPredicate;
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventPredicateBuilder)) {
            return false;
        }

        EventPredicateBuilder otherEventPredicateBuilder = (EventPredicateBuilder) other;
        return argMultimap.equals(otherEventPredicateBuilder.argMultimap);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
