package seedu.address.logic.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handles the toggling of views in the UI based on the command feedback.
 */
public class ViewToggler {

    public static final String LIST_PET_COMMAND = "List Pets";
    public static final String LIST_OWNER_COMMAND = "List Owners";
    public static final String LIST_BOTH_COMMAND = "List Both";
    public static final String LINK_OWNER_TO_PET_COMMAND = "Link owner to pet";
    public static final String OTHER_COMMAND = "Command does not change GUI";
    private static final String TEMPLATE = "Linked %1$s pet(s) to %2$s";
    private static final String REGEX = "^Linked \\d+ pet\\(s\\) to .+$";

    private final String commandType;

    /**
     * Constructs a {@code ViewToggler} based on the feedback message.
     * Determines the type of command that affects the UI view.
     *
     * @param feedbackToUser The feedback string returned by the command.
     *                       Must match one of the known feedback strings
     *                       that affect the view.
     */
    public ViewToggler(String feedbackToUser) {

        // Set command type based on feedback
        if (feedbackToUser.equals(ListPetCommand.MESSAGE_SUCCESS)) {
            this.commandType = LIST_PET_COMMAND;
        } else if (feedbackToUser.equals(ListOwnerCommand.MESSAGE_SUCCESS)) {
            this.commandType = LIST_OWNER_COMMAND;
        } else if (feedbackToUser.equals(ListBothCommand.MESSAGE_SUCCESS)) {
            this.commandType = LIST_BOTH_COMMAND;
        } else if (matchesTemplate(feedbackToUser)) {
            this.commandType = LINK_OWNER_TO_PET_COMMAND;
        } else {
            this.commandType = OTHER_COMMAND;
        }
    }

    /**
     * Returns the type of command that affects the view.
     *
     * @return A string representing the command type, which
     *         can be one of the pre-defined constants such as
     *         {@code LIST_PET_COMMAND}, {@code LIST_OWNER_COMMAND}, etc.
     */
    public String getCommandType() {
        return commandType;
    }

    /**
     * Checks whether the given input matches the feedback template
     * for linking owners to pets.
     *
     * @param input The input string to be checked.
     * @return {@code true} if the input matches the template for
     *         linking pets, {@code false} otherwise.
     */
    public static boolean matchesTemplate(String input) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    /**
     * Checks if this {@code ViewToggler} is equal to another object.
     *
     * @param other The other object to compare with.
     * @return {@code true} if both objects are of type {@code ViewToggler}
     *         and have the same command type, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewToggler)) {
            return false;
        }

        ViewToggler otherViewToggler = (ViewToggler) other;
        return this.commandType.equals(otherViewToggler.commandType);
    }
}
