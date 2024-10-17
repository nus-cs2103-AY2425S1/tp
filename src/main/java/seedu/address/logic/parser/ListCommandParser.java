package seedu.address.logic.parser;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListOwnerCommand;
import seedu.address.logic.commands.ListPetCommand;

public class ListCommandParser implements Parser<ListCommand> {
    public ListCommand parse(String userInput) {
        userInput = userInput.trim();
        if (userInput.equals("pets")) {
            return new ListPetCommand();
        } else {
            return new ListOwnerCommand();
        }
    }
}
