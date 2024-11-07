package spleetwaise.commons;

import static spleetwaise.commons.logic.LogicManager.FILE_OPS_ERROR_FORMAT;
import static spleetwaise.commons.logic.LogicManager.FILE_OPS_PERMISSION_ERROR_FORMAT;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import spleetwaise.address.model.AddressBook;
import spleetwaise.address.model.AddressBookModel;
import spleetwaise.address.model.AddressBookModelManager;
import spleetwaise.address.model.ReadOnlyAddressBook;
import spleetwaise.address.model.util.SampleDataUtil;
import spleetwaise.address.storage.AddressBookStorage;
import spleetwaise.address.storage.JsonAddressBookStorage;
import spleetwaise.commons.core.Config;
import spleetwaise.commons.core.LogsCenter;
import spleetwaise.commons.core.Version;
import spleetwaise.commons.exceptions.DataLoadingException;
import spleetwaise.commons.logic.Logic;
import spleetwaise.commons.logic.LogicManager;
import spleetwaise.commons.model.CommonModelManager;
import spleetwaise.commons.model.UserPrefs;
import spleetwaise.commons.storage.JsonUserPrefsStorage;
import spleetwaise.commons.storage.Storage;
import spleetwaise.commons.storage.StorageManager;
import spleetwaise.commons.storage.UserPrefsStorage;
import spleetwaise.commons.ui.Ui;
import spleetwaise.commons.ui.UiManager;
import spleetwaise.commons.util.ConfigUtil;
import spleetwaise.commons.util.StringUtil;
import spleetwaise.transaction.model.ReadOnlyTransactionBook;
import spleetwaise.transaction.model.TransactionBook;
import spleetwaise.transaction.model.TransactionBookModel;
import spleetwaise.transaction.model.TransactionBookModelManager;
import spleetwaise.transaction.storage.JsonTransactionBookStorage;
import spleetwaise.transaction.storage.TransactionBookStorage;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 5, 1, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;

    protected AddressBookModel addressBookModel;
    protected TransactionBookModel transactionBookModel;

    protected Config config;

    private volatile boolean isStopped = false;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing SpleetWaise]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());
        initLogging(config);

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        AddressBookStorage addressBookStorage = new JsonAddressBookStorage(userPrefs.getAddressBookFilePath());
        TransactionBookStorage transactionBookStorage =
                new JsonTransactionBookStorage(userPrefs.getTransactionBookFilePath());
        storage = new StorageManager(addressBookStorage, userPrefsStorage, transactionBookStorage);

        initModelManagers(storage);

        // Initialise Common Model
        CommonModelManager.initialise(addressBookModel, transactionBookModel, userPrefs);

        logic = new LogicManager(storage);

        ui = new UiManager(logic);

        // Add shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(this::cleanUp));
    }

    private void initModelManagers(Storage storage) {
        logger.info("Using Ab file : " + storage.getAddressBookFilePath());
        logger.info("Using Tb file : " + storage.getTransactionBookFilePath());

        try {
            Optional<ReadOnlyAddressBook> addressBookOptional = storage.readAddressBook();
            if (!addressBookOptional.isPresent()) {
                // If the addressbook is not present start from sample data
                logger.info("Creating new data files " + storage.getAddressBookFilePath() + " and "
                        + storage.getTransactionBookFilePath()
                        + " populated with sample data.");
                addressBookModel = new AddressBookModelManager(SampleDataUtil.getSampleAddressBook());
                // Since addressbook is nonexistent, whatever state transactionbook is in should become irrelevant.
                // We shall therefore start from sample data with transactionbook as well.
                transactionBookModel = new TransactionBookModelManager(SampleDataUtil.getSampleTransactionBook());
                return;
            }
            // Read the address book
            addressBookModel = new AddressBookModelManager(addressBookOptional.get());
            Optional<ReadOnlyTransactionBook> txnBookOptional = storage.readTransactionBook(addressBookModel);
            if (!txnBookOptional.isPresent()) {
                logger.info("Transaction book was not found and will be initialised as empty.");
            }
            // Read the transaction book
            ReadOnlyTransactionBook transactionBook = txnBookOptional.orElse(new TransactionBook());
            transactionBookModel = new TransactionBookModelManager(transactionBook);
        } catch (DataLoadingException e) {
            // Files could be corrupted beyond recovery, and therefore we should start from a blank slate
            logger.warning("Data files could not be loaded: " + e.getMessage()
                    + " Will be starting with an empty AddressBook and TransactionBook.");
            addressBookModel = new AddressBookModelManager(new AddressBook());
            transactionBookModel = new TransactionBookModelManager(new TransactionBook());
        }
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br> The default file path
     * {@code Config#DEFAULT_CONFIG_FILE} will be used instead if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            if (!configOptional.isPresent()) {
                logger.info("Creating new config file " + configFilePathUsed);
            }
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataLoadingException e) {
            logger.warning("Config file at " + configFilePathUsed + " could not be loaded."
                    + " Using default config properties.");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path, or a new {@code UserPrefs}
     * with default configuration if errors occur when reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using preference file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            if (!prefsOptional.isPresent()) {
                logger.info("Creating new preference file " + prefsFilePath);
            }
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataLoadingException e) {
            logger.warning("Preference file at " + prefsFilePath + " could not be loaded."
                    + " Using default preferences.");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting SpleetWaise " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    private void cleanUp() {
        if (!isStopped) {
            logger.info("Cleaning up resources...");
            try {
                storage.saveUserPrefs(CommonModelManager.getInstance().getUserPrefs());
                storage.saveAddressBook(addressBookModel.getAddressBook());
                storage.saveTransactionBook(transactionBookModel.getTransactionBook());
                logger.info("Resources saved...");
            } catch (AccessDeniedException e) {
                logger.severe(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()));
            } catch (IOException e) {
                logger.severe(String.format(FILE_OPS_ERROR_FORMAT, e.getMessage()));
            }
        }
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping SpleetWaise ] =============================");
        cleanUp();
        isStopped = true; // Ensures `cleanUp()` only runs once
    }
}
