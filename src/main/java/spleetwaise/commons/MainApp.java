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

    public static final Version VERSION = new Version(1, 4, 0, true);

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

        addressBookModel = initAddressBookModelManager(storage);
        transactionBookModel = initTransactionModelManager(storage, addressBookModel);

        // Initialise Common Model
        CommonModelManager.initialise(addressBookModel, transactionBookModel, userPrefs);

        logic = new LogicManager(storage);

        ui = new UiManager(logic);

        // Add shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(this::cleanUp));
    }

    /**
     * Returns a {@code AddressBookModelManager} with the data from {@code storage}'s address book and
     * {@code userPrefs}. <br> The data from the sample address book will be used instead if {@code storage}'s address
     * book is not found, or an empty address book will be used instead if errors occur when reading {@code storage}'s
     * address book.
     */
    private AddressBookModel initAddressBookModelManager(Storage storage) {
        logger.info("Using data file : " + storage.getAddressBookFilePath());

        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook initialData;
        try {
            addressBookOptional = storage.readAddressBook();
            if (!addressBookOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getAddressBookFilePath()
                        + " populated with a sample AddressBook.");
            }
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getAddressBookFilePath() + " could not be loaded."
                    + " Will be starting with an empty AddressBook.");
            initialData = new AddressBook();
        }

        return new AddressBookModelManager(initialData);
    }

    private TransactionBookModel initTransactionModelManager(Storage storage, AddressBookModel addressBookModel) {
        logger.info("Using data file : " + storage.getTransactionBookFilePath());

        Optional<ReadOnlyTransactionBook> txnBookOptional;
        ReadOnlyTransactionBook initialData;
        try {
            txnBookOptional = storage.readTransactionBook(addressBookModel);
            if (!txnBookOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getTransactionBookFilePath()
                        + " populated with a sample TransactionBook.");
            }
            initialData = txnBookOptional.orElseGet(SampleDataUtil::getSampleTransactionBook);
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getAddressBookFilePath() + " could not be loaded."
                    + " Will be starting with an empty TransactionBook.");
            initialData = new TransactionBook();
        }
        return new TransactionBookModelManager(initialData);
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
        logger.info("Starting AddressBook " + MainApp.VERSION);
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
