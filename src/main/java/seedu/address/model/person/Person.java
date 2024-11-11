package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final ContactType contactType;
    private final Name name;
    private final Optional<Phone> phone;
    private final Optional<Email> email;

    // Data fields
    private final Optional<TelegramHandle> telegramHandle;
    private final Optional<ModuleName> moduleName;
    private final Optional<Remark> remark;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(ContactType contactType, Name name, Optional<Phone> phone, Optional<Email> email,
                  Optional<TelegramHandle> telegramHandle, Optional<ModuleName> moduleName,
                  Optional<Remark> remark, Set<Tag> tags) {
        requireAllNonNull(contactType, name, phone, email, telegramHandle, moduleName, tags);
        this.contactType = contactType;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.telegramHandle = telegramHandle;
        this.moduleName = moduleName;
        this.remark = remark;

        this.tags.addAll(tags);
    }

    public ContactType getContactType() {
        return contactType;
    }

    public Name getName() {
        return name;
    }

    public Optional<Phone> getPhone() {
        return phone;
    }

    public Optional<Email> getEmail() {
        return email;
    }

    public Optional<TelegramHandle> getTelegramHandle() {
        return telegramHandle;
    }

    public Optional<ModuleName> getModuleName() {
        return moduleName;
    }

    public Optional<Remark> getRemark() {
        return remark;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName())
                && ((otherPerson.getPhone().isPresent()
                            && getPhone().isPresent() && otherPerson.getPhone().get().equals(getPhone().get()))
                            || (otherPerson.getEmail().isPresent() && getEmail().isPresent()
                            && otherPerson.getEmail().get().equals(getEmail().get()))
                            || (otherPerson.getTelegramHandle().isPresent() && getTelegramHandle().isPresent()
                            && otherPerson.getTelegramHandle().get().equals(getTelegramHandle().get()))
            );
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return contactType.equals(otherPerson.contactType)
                && name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && telegramHandle.equals(otherPerson.telegramHandle)
                && moduleName.equals(otherPerson.moduleName)
                && remark.equals(otherPerson.remark)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, telegramHandle, tags, moduleName, remark);

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("contactType", contactType)
                .add("name", name)
                .add("phone", phone.map(Phone::toString).orElse(" "))
                .add("email", email.map(Email::toString).orElse(" "))
                .add("telegramHandle", telegramHandle.map(TelegramHandle::toString).orElse(" "))
                .add("moduleName", moduleName.map(ModuleName::toString).orElse(" "))
                .add("remark", remark.map(Remark::toString).orElse(" "))
                .add("tags", tags)
                .toString();
    }

    /**
     * Looks inside the person class, and returns a {@Code String[]} containing the simple names of all the fields.
     * Extracts out the field names from class wrapped within a {@Code Optional} as well.
     * @return A {@Code String[]} containing the simple names of the fields that comprise a Person
     */
    public static String[] getFieldSimpleNames() {
        Class<Person> clazz = Person.class;
        return Arrays.stream(clazz.getDeclaredFields())
                .map(Person::getFieldSimpleName)
                .toArray(String[]::new);
    }

    private static String getFieldSimpleName(Field field) {
        Class<?> fieldType = field.getType();

        if (fieldType.equals(Optional.class)) {
            Type genericType = field.getGenericType();
            if (genericType instanceof ParameterizedType) {
                Type actualType = ((ParameterizedType) genericType).getActualTypeArguments()[0];
                return ((Class<?>) actualType).getSimpleName();
            }
        }

        return fieldType.getSimpleName();
    }


}
