package seedu.address.logic.commands;

import java.util.Arrays;

/**
 * Represents the keywords that are used in the command line.
 */
public enum CommandKeywords {
    add,
    addf,
    bookappt,
    clear,
    deleteappt,
    delete,
    edit,
    exit,
    filter,
    home,
    view;

    /**
     * Checks if the given keyword is a valid command keyword.
     *
     * @param keyword The keyword to check.
     * @return True if the keyword is a valid command keyword, false otherwise.
     */
    public static boolean isValidCommandKeyword(String keyword) {
        assert keyword != null;
        return Arrays.stream(CommandKeywords.values())
                .anyMatch(commandKeyword -> commandKeyword.name().equalsIgnoreCase(keyword));
    }
}
