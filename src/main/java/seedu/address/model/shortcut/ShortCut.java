package seedu.address.model.shortcut;

import java.util.Objects;

/**
 * ShortCut connects an Alias and a TagName
 */
public class ShortCut {
    private Alias alias;
    private FullTagName fullTagName;

    /**
     * Creates an instance of a Shortcut mapping the alias to the fulltagName
     * @param alias
     * @param fullTagName
     */
    public ShortCut(Alias alias, FullTagName fullTagName) {
        this.alias = alias;
        this.fullTagName = fullTagName;
    }

    public Alias getAlias() {
        return alias;
    }

    public FullTagName getFullTagName() {
        return fullTagName;
    }

    @Override
    public boolean equals(Object sc) {
        if (!(sc instanceof ShortCut)) {
            return false;
        }

        ShortCut other = (ShortCut) sc;
        return this.alias.equals(other.alias) && this.fullTagName.equals(other.fullTagName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alias, fullTagName);
    }
    @Override
    public String toString() {
        return alias.toString() + " : " + fullTagName.toString();
    }
}
