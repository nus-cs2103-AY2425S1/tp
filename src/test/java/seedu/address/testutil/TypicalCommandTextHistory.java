package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.CommandTextHistory;

public class TypicalCommandTextHistory {

    /**
     * Returns a typical {@code CommandTextHistory}.
     */
    public static CommandTextHistory getTypicalCommandTextHistory() {
        CommandTextHistory cth = new CommandTextHistory();
        for (String commandText : getTypicalCommandTextList()) {
            cth.addCommandText(commandText);
        }
        return cth;
    }

    public static List<String> getTypicalCommandTextList() {
        return new ArrayList<>(Arrays.asList("help", "listemployees", "listprojects", "listassignments"));
    }

}
