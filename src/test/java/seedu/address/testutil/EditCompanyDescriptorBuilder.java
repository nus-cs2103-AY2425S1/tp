package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditCompanyDescriptor;
import seedu.address.model.company.Address;
import seedu.address.model.company.CareerPageUrl;
import seedu.address.model.company.Company;
import seedu.address.model.company.Email;
import seedu.address.model.company.Name;
import seedu.address.model.company.Phone;
import seedu.address.model.company.Remark;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagBuilder;

/**
 * A utility class to help with building EditCompanyDescriptor objects.
 */
public class EditCompanyDescriptorBuilder {

    private EditCommand.EditCompanyDescriptor descriptor;

    public EditCompanyDescriptorBuilder() {
        descriptor = new EditCommand.EditCompanyDescriptor();
    }

    public EditCompanyDescriptorBuilder(EditCommand.EditCompanyDescriptor descriptor) {
        this.descriptor = new EditCommand.EditCompanyDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditCompanyDescriptor} with fields containing
     * {@code company}'s details
     */
    public EditCompanyDescriptorBuilder(Company company) {
        descriptor = new EditCommand.EditCompanyDescriptor();
        descriptor.setName(company.getName());
        descriptor.setPhone(company.getPhone());
        descriptor.setEmail(company.getEmail());
        descriptor.setAddress(company.getAddress());
        descriptor.setCareerPageUrl(company.getCareerPageUrl()); // Added this line
        descriptor.setTags(company.getTags());
        descriptor.setRemark(company.getRemark()); // Added this line
    }

    /**
     * Sets the {@code Name} of the {@code EditCompanyDescriptor} that we are
     * building.
     */
    public EditCompanyDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditCompanyDescriptor} that we are
     * building.
     */
    public EditCompanyDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditCompanyDescriptor} that we are
     * building.
     */
    public EditCompanyDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditCompanyDescriptor} that we are
     * building.
     */
    public EditCompanyDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code EditCompanyDescriptor} that we are
     * building.
     */
    public EditCompanyDescriptorBuilder withRemark(String remark) {
        descriptor.setRemark(new Remark(remark));
        return this;
    }

    /**
     * Sets the {@code CareerPageUrl} of the {@code EditCompanyDescriptor} that we are
     * building.
     */
    public EditCompanyDescriptorBuilder withCareerPageUrl(String url) {
        descriptor.setCareerPageUrl(new CareerPageUrl(url));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and sets it to the
     * {@code EditCompanyDescriptor}
     * that we are building.
     */
    public EditCompanyDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags)
                .map(tag -> new TagBuilder().build(tag))
                .collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }
    public EditCompanyDescriptor build() {
        return descriptor;
    }
}
