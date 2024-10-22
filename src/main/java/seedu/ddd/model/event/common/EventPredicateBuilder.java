package seedu.ddd.model.event.common;

import java.util.function.Predicate;

//import seedu.ddd.logic.commands.ListCommand;
import seedu.ddd.logic.parser.ArgumentMultimap;
//import seedu.ddd.logic.parser.ParserUtil;
import seedu.ddd.logic.parser.exceptions.ParseException;

//import java.util.Arrays;
//import java.util.Set;

//import static seedu.ddd.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.ddd.logic.parser.CliFlags.FLAG_CLIENT;
//import static seedu.ddd.logic.parser.CliFlags.FLAG_VENDOR;
//import static seedu.ddd.logic.parser.CliSyntax.*;
//import static seedu.ddd.logic.parser.CliSyntax.PREFIX_ADDRESS;
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

        return combinedPredicate;
    }
}
