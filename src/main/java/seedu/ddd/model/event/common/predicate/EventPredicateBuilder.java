package seedu.ddd.model.event.common.predicate;

import static seedu.ddd.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ddd.logic.parser.ParserUtil.MESSAGE_INVALID_ID;
import static seedu.ddd.logic.parser.ParserUtil.verifyNoEmptyInput;

import java.util.Arrays;
import java.util.function.Predicate;

import seedu.ddd.commons.util.ToStringBuilder;
import seedu.ddd.logic.parser.ArgumentMultimap;
import seedu.ddd.logic.parser.exceptions.ParseException;
import seedu.ddd.model.common.Id;
import seedu.ddd.model.common.Name;
import seedu.ddd.model.event.common.Date;
import seedu.ddd.model.event.common.Description;
import seedu.ddd.model.event.common.Event;

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
        combinedPredicate = addNamePredicate(argMultimap, combinedPredicate);
        combinedPredicate = addDatePredicate(argMultimap, combinedPredicate);

        return combinedPredicate;
    }
    private Predicate<Event> addDescriptionPredicate(ArgumentMultimap argMultimap, Predicate<Event> combinedPredicate)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_DESC).isPresent()) {
            if (!Description.isValidDescription(argMultimap.getValue(PREFIX_DESC).get())) {
                throw new ParseException(Description.MESSAGE_CONSTRAINTS);
            }
            String args = verifyNoEmptyInput(argMultimap, PREFIX_DESC);
            String[] descriptionKeywords = args.split("\\s+");
            combinedPredicate = combinedPredicate.and(
                    new DescriptionContainsKeywordsPredicate(Arrays.asList(descriptionKeywords)));
        }
        return combinedPredicate;
    }
    private Predicate<Event> addIdPredicate(ArgumentMultimap argMultimap, Predicate<Event> combinedPredicate)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_ID).isPresent()) {
            if (!Id.isValidId(argMultimap.getValue(PREFIX_ID).get())) {
                throw new ParseException(MESSAGE_INVALID_ID);
            }
            String args = verifyNoEmptyInput(argMultimap, PREFIX_ID);
            Id eventId = new Id(args);
            combinedPredicate = combinedPredicate.and(new EventIdPredicate(eventId));
        }
        return combinedPredicate;
    }

    private Predicate<Event> addNamePredicate(ArgumentMultimap argMultimap, Predicate<Event> combinedPredicate)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            if (!Name.isValidName(argMultimap.getValue(PREFIX_NAME).get())) {
                throw new ParseException(Name.MESSAGE_CONSTRAINTS);
            }
            String args = verifyNoEmptyInput(argMultimap, PREFIX_NAME);
            String[] nameKeywords = args.split("\\s+");
            combinedPredicate = combinedPredicate.and(
                    new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        }
        return combinedPredicate;
    }

    private Predicate<Event> addDatePredicate(ArgumentMultimap argMultimap, Predicate<Event> combinedPredicate)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            if (!Date.isValidDate(argMultimap.getValue(PREFIX_DATE).get())) {
                throw new ParseException(Date.MESSAGE_CONSTRAINTS);
            }
            String args = verifyNoEmptyInput(argMultimap, PREFIX_DATE);
            combinedPredicate = combinedPredicate.and(new EventDatePredicate(new Date(args)));
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
