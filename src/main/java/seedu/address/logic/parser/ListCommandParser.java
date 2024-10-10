package seedu.address.logic.parser;

import seedu.address.logic.commands.ListCommand;

public class ListCommandParser implements Parser<ListCommand> {
    public ListCommand parse(String userInput) {
        userInput = userInput.trim();
        if (userInput.equals("pets")) {
            return new ListCommand();
        } else {
            return new ListCommand();
        }
    }
}
