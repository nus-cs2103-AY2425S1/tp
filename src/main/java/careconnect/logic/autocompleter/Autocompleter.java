package careconnect.logic.autocompleter;

import static careconnect.logic.Messages.MESSAGE_NO_AUTOCOMPLETE_OPTIONS;
import static java.util.Objects.requireNonNull;

import java.util.List;

import careconnect.logic.autocompleter.exceptions.AutocompleteException;

/**
 * Provides functionality for autocomplete features.
 */
public class Autocompleter {

    /**
     * Provides an autocomplete suggestion based on the given prefix and options. Ties for
     * autocomplete suggestions will be broken by lexicographical order.
     * @param prefix The prefix text for which to generate an autocomplete suggestion.
     * @return A string containing the lexicographically smallest autocomplete suggestion.
     * @throws AutocompleteException If no valid autocomplete suggestion can be generate.
     */
    public String autocompleteWithLexicalPriority(
            String prefix, List<String> options) throws AutocompleteException {
        requireNonNull(prefix);
        requireNonNull(options);
        String ret = "";
        for (String option : options) {
            if (option.startsWith(prefix)) {
                if (ret.isEmpty()) {
                    ret = option;
                } else if (option.compareTo(ret) < 0) {
                    ret = option; // lexical priority
                }
            }
        }
        if (ret.isEmpty()) {
            throw new AutocompleteException(String.format(MESSAGE_NO_AUTOCOMPLETE_OPTIONS, prefix));
        }
        return ret;
    }
}
