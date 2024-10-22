package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.ContactMap;
import seedu.address.model.wedding.Role;

/**
 * Jackson-friendly version of {@link ContactMap}.
 */
class JsonAdaptedContactMap {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "ContactMap entry's %s field is missing!";
    public static final String DUPLICATE_ROLE_MESSAGE = "Duplicate role found in contact map: %s";

    private final List<JsonAdaptedContactEntry> entries = new ArrayList<>();

    /**
     * Represents a single entry in the contact map for JSON adaptation.
     */
    private static class JsonAdaptedContactEntry {
        private final String role;
        private final JsonAdaptedPerson person;

        @JsonCreator
        public JsonAdaptedContactEntry(
                @JsonProperty("role") String role,
                @JsonProperty("person") JsonAdaptedPerson person) {
            this.role = role;
            this.person = person;
        }

        public JsonAdaptedContactEntry(Role role, Person person) {
            this.role = role.toString();
            this.person = new JsonAdaptedPerson(person);
        }
    }

    /**
     * Constructs a {@code JsonAdaptedContactMap} with the given entries.
     */
    @JsonCreator
    public JsonAdaptedContactMap(@JsonProperty("entries") List<JsonAdaptedContactEntry> entries) {
        if (entries != null) {
            this.entries.addAll(entries);
        }
    }

    /**
     * Converts a given {@code ContactMap} into this class for Jackson use.
     */
    public JsonAdaptedContactMap(ContactMap contactMap) {
        // Using reflection to access the private map field since it's encapsulated
        try {
            java.lang.reflect.Field mapField = ContactMap.class.getDeclaredField("map");
            mapField.setAccessible(true);
            @SuppressWarnings("unchecked")
            Map<Role, Person> map = (Map<Role, Person>) mapField.get(contactMap);

            for (Map.Entry<Role, Person> entry : map.entrySet()) {
                entries.add(new JsonAdaptedContactEntry(entry.getKey(), entry.getValue()));
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // Handle reflection errors
            throw new RuntimeException("Error accessing ContactMap private fields", e);
        }
    }

    /**
     * Converts this Jackson-friendly adapted contact map object into the model's {@code ContactMap} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted contact map.
     */
    public ContactMap toModelType() throws IllegalValueException {
        ContactMap contactMap = new ContactMap();

        for (JsonAdaptedContactEntry entry : entries) {
            if (entry.role == null) {
                throw new IllegalValueException(
                        String.format(MISSING_FIELD_MESSAGE_FORMAT, "role"));
            }
            if (entry.person == null) {
                throw new IllegalValueException(
                        String.format(MISSING_FIELD_MESSAGE_FORMAT, "person"));
            }

            // Convert role string to Role
            Role role;
            try {
                role = new Role(entry.role);
            } catch (IllegalArgumentException e) {
                throw new IllegalValueException("Invalid role: " + entry.role);
            }

            // Convert person
            Person person = entry.person.toModelType();

            // Check if role already exists
            if (contactMap.hasRole(role)) {
                throw new IllegalValueException(
                        String.format(DUPLICATE_ROLE_MESSAGE, role.toString()));
            }

            contactMap.addToMap(role, person);
        }

        return contactMap;
    }
}
