//@@author wuzengfu
package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.contact.Contact;

/**
 * An UI component that displays a list of {@code Contact} with {@code Pagination}.
 */
public class PaginationPanel extends Pagination {
    public static final int ROWS_PER_PAGE = 10;
    private static int currentPageIndex = 0;
    private static final Logger logger = LogsCenter.getLogger(PaginationPanel.class);
    private final ObservableList<Contact> contactList;

    /**
     * Creates a panel that contains a list of {@code Contact} with {@code Pagination}.
     * @param contactList A list of contacts to display.
     */
    public PaginationPanel(ObservableList<Contact> contactList) {
        super();
        requireNonNull(contactList);
        this.contactList = contactList;
        this.contactList.addListener(this::onListItemsChanged);
        this.initPagination();
    }

    /**
     * Converts the index of the item of the current page to the index in {@code contactList}.
     * @param itemIndex The index of the item of the current page.
     * @return The index of the item in {@code contactList}.
     */
    public static int convertItemIndexToDisplayIndex(int itemIndex) {
        assert itemIndex >= 0;
        return currentPageIndex * ROWS_PER_PAGE + itemIndex + 1;
    }

    private void initPagination() {
        final int minPageCount = 1;
        int expectedPageCount = (int) Math.ceil((double) contactList.size() / ROWS_PER_PAGE);
        int pageCount = Math.max(expectedPageCount, minPageCount);
        this.setPageCount(pageCount);
        this.setPageFactory(this::createPage);
        logger.info("A pagination which contains " + pageCount + " page(s) has been created.");
    }

    private Node createPage(int pageIndex) {
        assert pageIndex >= 0;
        PaginationPanel.currentPageIndex = pageIndex;
        int fromIndex = pageIndex * PaginationPanel.ROWS_PER_PAGE;
        int endIndex = Math.min(fromIndex + PaginationPanel.ROWS_PER_PAGE, contactList.size());
        List<Contact> subList = contactList
                .stream()
                .toList()
                .subList(fromIndex, endIndex);
        ContactListPanel contactListPanel = new ContactListPanel(FXCollections.observableList(subList));
        return contactListPanel.getRoot();
    }

    private void onListItemsChanged(ListChangeListener.Change<? extends Contact> unused) {
        this.initPagination();
        logger.info("Re-render pagination due to the change of list items.");
    }
}
