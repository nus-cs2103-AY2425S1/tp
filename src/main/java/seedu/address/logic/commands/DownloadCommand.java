package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.commons.util.CsvUtil;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.storage.StorageManager;


/**
 * Downloads the csv file.
 */
public class DownloadCommand extends Command {

    public static final String COMMAND_WORD = "download";
    public static final String MESSAGE_SUCCESS = "Downloaded the csv file.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ObservableList<Person> addressBookJson = model.getAddressBook().getPersonList();
        String addressBookCsv = CsvUtil.convertObservableListToCsv(addressBookJson);
        StorageManager.saveCsvToFile(addressBookCsv);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
