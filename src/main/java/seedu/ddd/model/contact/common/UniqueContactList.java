package seedu.ddd.model.contact.common;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.ddd.commons.util.CollectionUtil;
import seedu.ddd.model.common.Id;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.exceptions.ContactNotFoundException;
import seedu.ddd.model.contact.exceptions.DuplicateContactException;
import seedu.ddd.model.contact.vendor.Vendor;

/**
 * A list of contacts that enforces uniqueness between its elements and does not allow nulls.
 * A contact is considered unique by comparing using {@code Contact#isSameContact(Contact)}.
 * As such, adding and updating of contacts uses Contact#isSameContact(Contact) for equality
 * so as to ensure that the contact being added or updated is unique in terms of identity in
 * the UniqueContactList. However, the removal of a contact uses Contact#equals(Object) so
 * as to ensure that the contact with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Contact#isSameContact(Contact)
 */
public class UniqueContactList implements Iterable<Contact> {

    private final ObservableList<Contact> internalList = FXCollections.observableArrayList();
    private final ObservableList<Contact> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent contact as the given argument.
     */
    public boolean contains(Contact toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameContact);
    }

    /**
     * Returns true if the list contains a client with the id as the given argument.
     */
    public boolean containsClientId(Id contactIdToCheck) {
        requireNonNull(contactIdToCheck);
        return internalList.stream()
                .filter(contact -> contact instanceof Client)
                .map(contact -> (Client) contact)
                .anyMatch(client -> contactIdToCheck.equals(client.getId()));
    }

    /**
     * Returns true if the list contains a vendor with the id as the given argument.
     */
    public boolean containsVendorId(Id contactIdToCheck) {
        requireNonNull(contactIdToCheck);
        return internalList.stream()
                .filter(contact -> contact instanceof Vendor)
                .map(contact -> (Vendor) contact)
                .anyMatch(vendor -> contactIdToCheck.equals(vendor.getId()));
    }

    /**
     * Gets a contact from the list.
     * If contact has not been created or does not exist, a null object will be returned.
     */
    public Contact get(Id contactId) {
        requireNonNull(contactId);
        return internalList.stream().filter(contact -> contactId.equals(contact.getId())).findFirst().orElse(null);
    }

    /**
     * Adds a contact to the list.
     * The contact must not already exist in the list.
     */
    public void add(Contact toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateContactException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the contact {@code target} in the list with {@code editedContact}.
     * {@code target} must exist in the list.
     * The contact identity of {@code editedContact} must not be the same as another existing contact in the list.
     */
    public void setContact(Contact target, Contact editedContact) {
        CollectionUtil.requireAllNonNull(target, editedContact);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ContactNotFoundException();
        }

        if (!target.isSameContact(editedContact) && contains(editedContact)) {
            throw new DuplicateContactException();
        }

        // check if the id already exists
        Predicate<Contact> duplicateIdPredicate = contact ->
                contact.getId().equals(editedContact.getId()) && !contact.equals(target);
        if (internalList.stream().anyMatch(duplicateIdPredicate)) {
            throw new DuplicateContactException();
        }

        // check if same contact exists
        Predicate<Contact> duplicateContactPredicate = contact ->
                contact != target && contact.isSameContact(editedContact);
        if (internalList.stream().anyMatch(duplicateContactPredicate)) {
            throw new DuplicateContactException();
        }

        internalList.set(index, editedContact);
    }

    /**
     * Removes the equivalent contact from the list.
     * The contact must exist in the list.
     */
    public void remove(Contact toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ContactNotFoundException();
        }
    }

    public void setContacts(UniqueContactList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code contacts}.
     * {@code contacts} must not contain duplicate contacts.
     */
    public void setContacts(List<Contact> contacts) {
        CollectionUtil.requireAllNonNull(contacts);
        if (!contactsAreUnique(contacts)) {
            throw new DuplicateContactException();
        }

        internalList.setAll(contacts);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Contact> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Contact> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueContactList)) {
            return false;
        }

        UniqueContactList otherUniqueContactList = (UniqueContactList) other;
        return internalList.equals(otherUniqueContactList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code contacts} contains only unique contacts.
     */
    private boolean contactsAreUnique(List<Contact> contacts) {
        for (int i = 0; i < contacts.size() - 1; i++) {
            for (int j = i + 1; j < contacts.size(); j++) {
                if (contacts.get(i).isSameContact(contacts.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
