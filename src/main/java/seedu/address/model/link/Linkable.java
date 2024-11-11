package seedu.address.model.link;

/**
 * Represents an entity that can be linked to another linkable entity.
 */
public interface Linkable {
    public void addLinkedEntity(Linkable entity);

    public void removeLinkedEntity(Linkable entity);
    public String getUniqueID();

    public String getInfo();
}
