package seedu.address.logic.handler;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Tags people that share the same phone number
 */
public class DuplicatePhoneTagger {
    private static final Tag DUPLICATE_TAG = new Tag("DuplicatePhone");
    private final HashMap<Phone, Integer> phoneFrequencies = new HashMap<>();

    /**
     * Uses the model to update the person list based on duplicate phone numbers
     * @param model that represents the current state of the address book
     */
    public void tagPhoneDuplicates(Model model) {
        List<Person> persons = model.getFilteredPersonList();
        updateFrequenciesOfPhones(persons);
        updatePersonsList(model, persons);
    }

    /**
     * Counts and stores the frequency of each phone number
     * @param persons the list of persons in the address book
     */
    public void updateFrequenciesOfPhones(List<Person> persons) {
        clearFrequencies();
        for (Person person : persons) {
            phoneFrequencies.put(person.getPhone(), phoneFrequencies.getOrDefault(person.getPhone(), 0) + 1);
        }
    }
    /**
     * Checks if there exists a duplicate phone number
     * @return true if there is a duplicate, else false
     */
    public boolean isDuplicatePresent() {
        for (Phone phone : phoneFrequencies.keySet()) {
            if (isPhoneDuplicate(phone)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPhoneDuplicate(Phone phone) {
        return phoneFrequencies.getOrDefault(phone, 0) > 1;
    }

    public void clearFrequencies() {
        phoneFrequencies.clear();
    }

    private void updatePersonsList(Model model, List<Person> persons) {
        for (Person personToUpdate : persons) {
            Phone phone = personToUpdate.getPhone();
            boolean isPhoneDuplicate = isPhoneDuplicate(phone);
            Person updatedPerson = updatePerson(personToUpdate, isPhoneDuplicate);
            model.setPerson(personToUpdate, updatedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        }
    }

    private Person updatePerson(Person person, boolean isPhoneDuplicate) {
        Name name = person.getName();
        Phone phone = person.getPhone();
        Email email = person.getEmail();
        Address address = person.getAddress();

        Set<Tag> newTags = new HashSet<>();
        newTags.addAll(person.getTags());

        if (isPhoneDuplicate) {
            newTags.add(DUPLICATE_TAG);
        } else {
            newTags.removeIf(tag -> tag.equals(DUPLICATE_TAG));
        }
        return new Person(name, phone, email, address, newTags);
    }

}
