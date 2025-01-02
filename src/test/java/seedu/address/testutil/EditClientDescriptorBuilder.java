package seedu.address.testutil;

import java.math.BigInteger;

import seedu.address.logic.commands.EditCommand.EditClientDescriptor;
import seedu.address.model.client.Address;
import seedu.address.model.client.Client;
import seedu.address.model.client.Email;
import seedu.address.model.client.Income;
import seedu.address.model.client.Job;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;
import seedu.address.model.client.Remark;
import seedu.address.model.status.Status;
import seedu.address.model.tier.Tier;

/**
 * A utility class to help with building EditClientDescriptor objects.
 */
public class EditClientDescriptorBuilder {

    private EditClientDescriptor descriptor;

    public EditClientDescriptorBuilder() {
        descriptor = new EditClientDescriptor();
    }

    public EditClientDescriptorBuilder(EditClientDescriptor descriptor) {
        this.descriptor = new EditClientDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditClientDescriptor} with fields containing {@code client}'s details
     */
    public EditClientDescriptorBuilder(Client client) {
        descriptor = new EditClientDescriptor();
        descriptor.setName(client.getName());
        descriptor.setPhone(client.getPhone());
        descriptor.setEmail(client.getEmail());
        descriptor.setAddress(client.getAddress());
        descriptor.setJob(client.getJob());
        descriptor.setIncome(client.getIncome());
        descriptor.setTier(client.getTier());
        descriptor.setNewRemark(client.getRemark());
    }

    /**
     * Sets the {@code Name} of the {@code EditClientDescriptor} that we are building.
     */
    public EditClientDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditClientDescriptor} that we are building.
     */
    public EditClientDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditClientDescriptor} that we are building.
     */
    public EditClientDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditClientDescriptor} that we are building.
     */
    public EditClientDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }
    /**
     * Sets the {@code Job} of the {@code EditClientDescriptor} that we are building.
     */
    public EditClientDescriptorBuilder withJob(String job) {
        descriptor.setJob(new Job(job));
        return this;
    }

    /**
     * Sets the {@code Income} of the {@code EditClientDescriptor} that we are building.
     */
    public EditClientDescriptorBuilder withIncome(BigInteger income) {
        descriptor.setIncome(new Income(income));
        return this;
    }
    /**
     * Parses the {@code tier} into a {@code Set<Tier>} and set it to the {@code EditClientDescriptor}
     * that we are building.
     */
    public EditClientDescriptorBuilder withTier(String tier) {
        descriptor.setTier(new Tier(tier));
        return this;
    }

    /**
     * Sets the {@code remark} of the {@code EditClientDescriptor} that we are building.
     */
    public EditClientDescriptorBuilder withNewRemark(String remark) {
        descriptor.setNewRemark(new Remark(remark));
        return this;
    }

    /**
     * Sets the {@code appendedRemark} of the {@code EditClientDescriptor} that we are building.
     */
    public EditClientDescriptorBuilder withAppendedRemark(String remark) {
        descriptor.setAppendedRemark(new Remark(remark));
        return this;
    }

    /**
     * Sets the {@code status} of the {@code EditClientDescriptor} that we are building.
     */
    public EditClientDescriptorBuilder withStatus(String status) {
        descriptor.setStatus(new Status(status));
        return this;
    }

    public EditClientDescriptor build() {
        return descriptor;
    }
}
