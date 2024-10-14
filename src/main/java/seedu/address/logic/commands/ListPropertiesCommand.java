package seedu.address.logic.commands;

import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

public class ListPropertiesCommand extends ListCommand {
    public static final String KEY_WORD = "properties";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // Logic to list properties
        model.setDisplayProperties();
        System.out.println("Listing all properties");
        return new CommandResult(String.format(ListCommand.MESSAGE_SUCCESS, KEY_WORD));
    }
}
