package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name, Phone, Email, Address, RegisterNumber, Sex, Class, EcName, EcNumber, Tag}
 * matches any of the keywords given.
 */
public class PersonPredicate implements Predicate<Person> {
    private final List<String> names;
    private final List<String> phones;
    private final List<String> emails;
    private final List<String> addresses;
    private final List<String> registerNumbers;
    private final List<String> sexes;
    private final List<String> classes;
    private final List<String> ecNames;
    private final List<String> ecNumbers;
    private final List<String> tags;

    /**
     * Constructs a {@code PersonPredicate} with the given attributes to filter persons.
     * Each attribute is represented as a list, and the predicate will test if a person matches
     * any of the specified values for each attribute.
     * @param names A list of {@code name} to filter by.
     * @param phones A list of {@code phone} to filter by.
     * @param emails A list of {@code email} to filter by.
     * @param addresses A list of {@code address} to filter by.
     * @param registerNumbers A list of {@code registerNumber} to filter by.
     * @param sexes A list of {@code sex} to filter by.
     * @param classes A list of {@code studentClass} to filter by.
     * @param ecNames A list of {@code ecName} to filter by.
     * @param ecNumbers A list of {@code ecNumber} to filter by.
     * @param tags A list of {@code tag} to filter by.
     */
    public PersonPredicate(List<String> names, List<String> phones, List<String> emails,
                   List<String> addresses, List<String> registerNumbers, List<String> sexes,
                   List<String> classes, List<String> ecNames, List<String> ecNumbers, List<String> tags) {
        this.names = names;
        this.phones = phones;
        this.emails = emails;
        this.addresses = addresses;
        this.registerNumbers = registerNumbers;
        this.sexes = sexes;
        this.classes = classes;
        this.ecNames = ecNames;
        this.ecNumbers = ecNumbers;
        this.tags = tags;
    }

    /**
     * Tests if the given {@code Person} matches the specified filter criteria. The method evaluates multiple
     * attributes of the person, such as name, phone, email, address, register number, sex, class,
     * emergency contact details, and tags. If any attribute filter is empty, it will be treated as an empty value
     * for that field, meaning any value will match.
     * However, if all the attributes are empty, the test will return false as there will be no filtering in that case
     * @param person the input argument.
     * @return boolean value depending on whether the values and the keywords match
     */
    @Override
    public boolean test(Person person) {

        boolean matchName = names.isEmpty() || names.stream()
                .anyMatch(name -> StringUtil.containsWordIgnoreCase(person.getName().fullName, name));

        boolean matchPhone = phones.isEmpty() || phones.stream()
                .anyMatch(phone -> person.getPhone().value.contains(phone));

        boolean matchEmail = emails.isEmpty() || emails.stream()
                .anyMatch(email -> person.getEmail().value.equalsIgnoreCase(email));

        boolean matchAddress = addresses.isEmpty() || addresses.stream()
                .anyMatch(address -> StringUtil.containsWordIgnoreCase(person.getAddress().value, address));

        boolean matchRegisterNumber = registerNumbers.isEmpty() || registerNumbers.stream()
                .anyMatch(r -> person.getRegisterNumber().value.equals(r));

        boolean matchSex = sexes.isEmpty() || sexes.stream()
                .anyMatch(sex -> person.getSex().value.equalsIgnoreCase(sex));

        boolean matchClass = classes.isEmpty() || classes.stream()
                .anyMatch(studentClass -> person.getStudentClass().value.equals(studentClass));

        boolean matchEcName = ecNames.isEmpty() || ecNames.stream()
                .anyMatch(ecName -> StringUtil.containsWordIgnoreCase(person.getEcName().value, ecName));

        boolean matchEcNumber = ecNumbers.isEmpty() || ecNumbers.stream()
                .anyMatch(ecNum -> person.getEcNumber().value.contains(ecNum));

        boolean matchtag = tags.isEmpty() || tags.stream().anyMatch(tag -> person.getTags().stream()
                        .anyMatch(personTag -> personTag.tagName.equalsIgnoreCase(tag)));

        System.out.println("Match result: " + matchName);

        if (this.isEmpty()) {
            return false;
        }

        return matchName && matchPhone && matchEmail && matchAddress && matchRegisterNumber && matchSex
                && matchClass && matchEcName && matchEcNumber && matchtag;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonPredicate)) {
            return false;
        }

        PersonPredicate otherPersonPredicate = (PersonPredicate) other;
        return names.equals(otherPersonPredicate.names)
                && phones.equals(otherPersonPredicate.phones)
                && emails.equals(otherPersonPredicate.emails)
                && addresses.equals(otherPersonPredicate.addresses)
                && registerNumbers.equals(otherPersonPredicate.registerNumbers)
                && sexes.equals(otherPersonPredicate.sexes)
                && classes.equals(otherPersonPredicate.classes)
                && ecNames.equals(otherPersonPredicate.ecNames)
                && ecNumbers.equals(otherPersonPredicate.ecNumbers)
                && tags.equals(otherPersonPredicate.tags);
    }

    public boolean isEmpty() {
        return names.isEmpty() && phones.isEmpty() && emails.isEmpty() && addresses.isEmpty()
                && registerNumbers.isEmpty() && sexes.isEmpty() && classes.isEmpty()
                && ecNames.isEmpty() && ecNumbers.isEmpty() && tags.isEmpty();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("names", names)
                .add("phones", phones)
                .add("emails", emails)
                .add("addresses", addresses)
                .add("registerNumbers", registerNumbers)
                .add("sexes", sexes)
                .add("classes", classes)
                .add("ecNames", ecNames)
                .add("ecNumbers", ecNumbers)
                .add("tags", tags)
                .toString();
    }
}
