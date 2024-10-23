package seedu.address.logic.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.Messages;

public class ViewToggler {

    public static final String LIST_PET_COMMAND = "List Pets";
    public static final String LIST_OWNER_COMMAND = "List Owners";
    public static final String LIST_BOTH_COMMAND = "List Both";

    public static final String LINK_OWNER_TO_PET_COMMAND = "Link owner to pet";

    public static final String OTHER_COMMAND = "Command does not change GUI";
    private static final String TEMPLATE = "Linked %1$s pet(s) to %2$s";
    private static final String REGEX = "^Linked \\d+ pet\\(s\\) to .+$";

    private final String commandType;

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

    public String getCommandType() {
        return commandType;
    }

    public static boolean matchesTemplate(String input) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

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
