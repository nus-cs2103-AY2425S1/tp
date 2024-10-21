package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.regex.Pattern;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;

public class SwitchCommand extends Command {
    public static final HashMap<String, ReadOnlyAddressBook> savedProfiles = new HashMap<>();
    public static final String COMMAND_WORD = "switch";
    public static final String COMMAND_ALIAS = "s";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " lorem ipsum blah";

    public static final String MESSAGE_SUCCESS = "Switched to profile %1$s";
    public static final String MESSAGE_CONSTRAINT = "msg constraint lorem";
    private final String profileName;
    public SwitchCommand(String profileName) {
        this.profileName = profileName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Path filePath = Paths.get("data", profileName + ".json");

        //save current profile
        String curProfileName = getProfileName(model.getAddressBookFilePath());
        ReadOnlyAddressBook curAddressBook = model.getFrozenAddressBook();
        savedProfiles.put(curProfileName, curAddressBook);

        //get the new profile
        ReadOnlyAddressBook nextAddressBook;
        if (savedProfiles.containsKey(profileName)) {
            // profile exists
            nextAddressBook = savedProfiles.get(profileName);
        } else {
            nextAddressBook = new AddressBook();
        }

        // set next profile
        System.out.println(model.getAddressBookFilePath());
        model.setAddressBookFilePath(filePath);
        System.out.println(model.getAddressBookFilePath());
        model.setAddressBook(nextAddressBook);
        return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SwitchCommand)) {
            return false;
        }

        SwitchCommand otherSwitchCommand = (SwitchCommand) other;
        return profileName.equals(otherSwitchCommand.profileName);
    }

    private static String getProfileName(Path filePath) {
        boolean startsWithData = filePath.startsWith("data");
        boolean singleNameFile = filePath.getNameCount() == 2;
        boolean endsWithJson = filePath.toString().endsWith(".json");
        assert startsWithData && singleNameFile && endsWithJson : "This file path is not supported";
        String fileName = filePath.getFileName().toString();
        return fileName.substring(0, fileName.length() - 5);
    }

    public static boolean isValidProfile(String filePath) {
        String alphanumericWithHyphenRegex = "^[a-zA-Z0-9-]+$";
        Pattern pattern = Pattern.compile(alphanumericWithHyphenRegex);
        return filePath.length() <= 10 && pattern.matcher(filePath).matches();
    }
}
