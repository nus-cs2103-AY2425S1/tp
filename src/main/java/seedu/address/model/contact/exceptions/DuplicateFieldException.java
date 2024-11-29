//@@author somethingfishyfishy
package seedu.address.model.contact.exceptions;

/**
 * Signals that the operation will result in duplicate fields that are in conflict with other Contacts in
 * UniqueContactList
 */
public class DuplicateFieldException extends RuntimeException {
    public static final String DEFAULT_MESSAGE =
            "Operation would result in adding a field that conflicts with another contact";

    public DuplicateFieldException() {
        super(DEFAULT_MESSAGE);
    }
}
