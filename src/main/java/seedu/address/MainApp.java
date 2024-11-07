package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.CustomerOrderList;
import seedu.address.model.order.SupplyOrderList;
import seedu.address.model.product.IngredientCatalogue;
import seedu.address.model.product.PastryCatalogue;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.CustomerOrderListStorage;
import seedu.address.storage.IngredientCatalogueStorage;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonCustomerOrderListStorage;
import seedu.address.storage.JsonIngredientCatalogueStorage;
import seedu.address.storage.JsonPastryCatalogueStorage;
import seedu.address.storage.JsonSupplyOrderListStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.PastryCatalogueStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.SupplyOrderListStorage;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 2, 2, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;


    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing AddressBook ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());
        initLogging(config);

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        IngredientCatalogueStorage ingredientCatalogueStorage =
                new JsonIngredientCatalogueStorage(config.getIngredientCatalogueFilePath());
        PastryCatalogueStorage pastryCatalogueStorage =
                new JsonPastryCatalogueStorage(config.getPastryCatalogueFilePath());
        AddressBookStorage addressBookStorage = new JsonAddressBookStorage(userPrefs.getAddressBookFilePath());
        CustomerOrderListStorage customerOrderListStorage =
                new JsonCustomerOrderListStorage(userPrefs.getCustomerOrderListFilePath());
        SupplyOrderListStorage supplyOrderListStorage =
                new JsonSupplyOrderListStorage(userPrefs.getSupplyOrderListFilePath());
        storage = new StorageManager(addressBookStorage, userPrefsStorage, ingredientCatalogueStorage,
                pastryCatalogueStorage, customerOrderListStorage, supplyOrderListStorage);


        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book,
     * userPrefs, ingredientCatalogue, and pastryCatalogue.
     * Sample data is used if any data is not found, or an empty address book if errors occur.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        logger.info("Using data file : " + storage.getAddressBookFilePath());

        // Initialize AddressBook
        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook initialData;
        try {
            addressBookOptional = storage.readAddressBook();
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
            if (!addressBookOptional.isPresent()) {
                logger.info("Address book file not found. Starting with sample data.");
            }
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getAddressBookFilePath()
                    + " could not be loaded. Starting with an empty AddressBook.");
            initialData = new AddressBook();
        }

        // Initialize IngredientCatalogue
        IngredientCatalogue ingredientCatalogue;
        try {
            ingredientCatalogue = storage.readIngredientCatalogue()
                                         .orElseGet(SampleDataUtil::getSampleIngredientCatalogue);
        } catch (DataLoadingException e) {
            logger.warning("Could not load ingredient catalogue data. Using default sample catalogue.");
            ingredientCatalogue = SampleDataUtil.getSampleIngredientCatalogue();
        }

        // Initialize PastryCatalogue
        PastryCatalogue pastryCatalogue;
        try {
            pastryCatalogue = storage.readPastryCatalogue().orElseGet(SampleDataUtil::getSamplePastryCatalogue);
        } catch (DataLoadingException e) {
            logger.warning("Could not load pastry catalogue data. Using default sample catalogue.");
            pastryCatalogue = SampleDataUtil.getSamplePastryCatalogue();
        }

        Optional<CustomerOrderList> customerOrderListOptional;
        CustomerOrderList initialCustomerOrderListData;
        try {
            customerOrderListOptional = storage.readCustomerOrderList();
            if (!customerOrderListOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getCustomerOrderListFilePath()
                        + " populated with a sample CustomerOrderList.");
            }
            initialCustomerOrderListData = customerOrderListOptional
                                           .orElseGet(SampleDataUtil::getSampleCustomerOrderList);
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getCustomerOrderListFilePath() + " could not be loaded."
                    + " Will be starting with an empty CustomerOrderList.");
            initialCustomerOrderListData = new CustomerOrderList();
        }

        Optional<SupplyOrderList> supplyOrderListOptional;
        SupplyOrderList initialSupplyOrderListData;
        try {
            supplyOrderListOptional = storage.readSupplyOrderList();
            if (!supplyOrderListOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getSupplyOrderListFilePath()
                        + " populated with a sample SupplyOrderList.");
            }
            initialSupplyOrderListData = supplyOrderListOptional.orElseGet(SampleDataUtil::getSampleSupplyOrderList);
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getSupplyOrderListFilePath() + " could not be loaded."
                    + " Will be starting with an empty SupplyOrderList.");
            initialSupplyOrderListData = new SupplyOrderList();
        }

        return new ModelManager(initialData, userPrefs, ingredientCatalogue, pastryCatalogue,
                storage, initialCustomerOrderListData, initialSupplyOrderListData);
    }


    private Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified: " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataLoadingException e) {
            logger.warning("Config file at " + configFilePathUsed + " could not be loaded. Using default config.");
            initializedConfig = new Config();
        }

        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file: " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    private UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using preference file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataLoadingException e) {
            logger.warning("Preference file at " + prefsFilePath + " could not be loaded. Using default preferences.");
            initializedPrefs = new UserPrefs();
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting AddressBook " + VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("Stopping application and saving data");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
            storage.saveIngredientCatalogue(model.getIngredientCatalogue());
            storage.savePastryCatalogue(model.getPastryCatalogue());
            storage.saveCustomerOrderList(model.getCustomerOrderList());
            storage.saveSupplyOrderList(model.getSupplyOrderList());
        } catch (IOException e) {
            logger.severe("Failed to save data on application stop: " + e.getMessage());
        }
    }
}
