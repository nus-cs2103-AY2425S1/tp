package spleetwaise.commons.model;

import java.nio.file.Path;

import spleetwaise.address.model.AddressBookModel;
import spleetwaise.commons.core.GuiSettings;
import spleetwaise.transaction.model.TransactionBookModel;

/**
 * <p>Interface representing the model layer that encapsulates the business logic.</p>
 *
 * @see AddressBookModel
 * @see TransactionBookModel
 */
public interface CommonModel extends AddressBookModel, TransactionBookModel {
    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);
}
