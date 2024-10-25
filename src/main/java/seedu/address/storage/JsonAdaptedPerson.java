package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.role.Role;
import seedu.address.model.wedding.Wedding;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String role;

    //Own wedding is stored as hashcode
    private final int ownWedding;
    // Wedding jobs are stored as hashcodes
    private final List<Integer> weddingJobs = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(
            @JsonProperty("name") String name,
            @JsonProperty("phone") String phone,
            @JsonProperty("email") String email,
            @JsonProperty("address") String address,
            @JsonProperty("role") String role,
            @JsonProperty("ownWedding") int ownWedding,
            @JsonProperty("weddingJobs") List<Integer> weddingJobs) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.role = role;
        this.ownWedding = ownWedding;
        if (weddingJobs != null) {
            this.weddingJobs.addAll(weddingJobs);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        role = source.getRole().roleName;
        ownWedding = source.getOwnWedding() != null ? source.getOwnWedding().hashCode() : 0;
        weddingJobs.addAll(source.getWeddingJobs().stream()
                .map(Wedding::hashCode)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType(List<Wedding> weddingList) throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        Role modelRole = null;
        if (role != null) {
            if (!Role.isValidRoleName(role)) {
                throw new IllegalValueException(Role.MESSAGE_CONSTRAINTS);
            }
            modelRole = new Role(role);
        }

        Wedding modelOwnWedding = ownWedding != 0 ? lookupWeddingByHashCode(ownWedding, weddingList) : null;

        Set<Wedding> modelWeddingJobs = new HashSet<>();
        for (Integer weddingHashCode : weddingJobs) {
            Wedding wedding = lookupWeddingByHashCode(weddingHashCode, weddingList);
            if (wedding != null) {
                modelWeddingJobs.add(wedding);
            }
        }

        Person person = new Person(modelName, modelPhone, modelEmail, modelAddress, modelRole, modelOwnWedding);
        person.getWeddingJobs().addAll(modelWeddingJobs);
        return person;
    }

    private Wedding lookupWeddingByHashCode(int hashCode, List<Wedding> weddings) {
        for (Wedding wedding : weddings) {
            if (wedding.hashCode() == hashCode) {
                return wedding;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof JsonAdaptedPerson)) {
            return false;
        }

        JsonAdaptedPerson otherPerson = (JsonAdaptedPerson) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && ((role == null && otherPerson.role == null) || (role != null && role.equals(otherPerson.role)))
                && ownWedding == otherPerson.ownWedding
                && weddingJobs.equals(otherPerson.weddingJobs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, email, address, role, ownWedding, weddingJobs);
    }
}
