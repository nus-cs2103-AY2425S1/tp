package seedu.address.model.person.company;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.student.StudentID;
import seedu.address.model.tag.Tag;

/**
 * Represents a Company in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Company extends Person {

    private final Industry industry;

    /**
     * Create a company object
     * @param name
     * @param industry
     * @param phone
     * @param email
     * @param address
     * @param tags
     */
    public Company(Name name, Industry industry, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(name, phone, email, address, tags);
        requireAllNonNull(industry);
        this.industry = industry;
    }

    /**
     * Return the industry of company
     * @return industry
     */
    @Override
    public Industry getIndustry() {
        return industry;
    }

    /**
     * Return null as company does not have student id
     * @return null
     */
    @Override
    public StudentID getStudentID() { return null; }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", super.getName())
                .add("industry", industry)
                .add("phone", super.getPhone())
                .add("email", super.getEmail())
                .add("address", super.getAddress())
                .add("tags", super.getTags())
                .toString();
    }

    /**
     * Return the string representation of this category
     * @return "Company"
     */
    public String getCategoryDisplayName() {
        return "Company";
    }

}

