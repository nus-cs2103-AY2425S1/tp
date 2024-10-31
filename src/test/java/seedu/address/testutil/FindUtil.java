package seedu.address.testutil;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.PredicateGroup;

/**
 * A utility class for Find command.
 */
public class FindUtil {
    /**
     * Returns a criteria for find command using provided {@code Prefix} and keywords.
     */
    public static String getFindCriteria(Prefix prefix, Set<String> keywords) {
        return prefix + keywords.stream().collect(Collectors.joining(" "));
    }

    /**
     * Returns a {@code PredicateGroup} with provided predicates.
     */
    @SafeVarargs
    public static PredicateGroup getPredicateGroup(Predicate<Person>... predicates) {
        // The only arguments that will be passed into this constuctor is of type
        // `Predicate<Person>` so it is safe to accept Variable Arguments.
        PredicateGroup predicateGroup = new PredicateGroup();
        for (Predicate<Person> predicate : predicates) {
            predicateGroup.add(predicate);
        }
        return predicateGroup;
    }
}
