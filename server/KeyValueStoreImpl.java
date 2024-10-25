package server;

import utils.ILogger;
import utils.Logger;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of the KeyValueStoreInterface.
 * Handles the actual storage and retrieval of key-value pairs.
 */
public class KeyValueStoreImpl extends UnicastRemoteObject implements KeyValueStoreInterface {

    private static final long serialVersionUID = 1L;
    private final ConcurrentHashMap<String, String> store;
    private final ILogger logger;

    /**
     * Constructs a new KeyValueStoreImpl object and initializes the store and logger.
     *
     * @throws RemoteException If a remote communication error occurs.
     */
    protected KeyValueStoreImpl() throws RemoteException {
        super();
        store = new ConcurrentHashMap<>();
        logger = new Logger("ServerLogger", "ServerLog.log");
    }

    /**
     * Inserts a key-value pair into the store.
     *
     * @param key   The key to insert.
     * @param value The value associated with the key.
     * @return A status message indicating success or failure.
     * @throws RemoteException If a remote communication error occurs.
     */
    @Override
    public String put(String key, String value) throws RemoteException {
        if (store.putIfAbsent(key, value) == null) {
            logger.log("PUT: Key=\"" + key + "\", Value=\"" + value + "\" added successfully.");
            return "OK: Key added successfully.";
        } else {
            logger.log("PUT FAILED: Key=\"" + key + "\" already exists.");
            return "ERROR: Key already exists.";
        }
    }

    /**
     * Retrieves the value associated with a given key.
     *
     * @param key The key whose value is to be retrieved.
     * @return The value associated with the key, or an error message if the key does not exist.
     * @throws RemoteException If a remote communication error occurs.
     */
    @Override
    public String get(String key) throws RemoteException {
        if (store.containsKey(key)) {
            String value = store.get(key);
            logger.log("GET: Key=\"" + key + "\", Value=\"" + value + "\" retrieved successfully.");
            return "OK: " + value;
        } else {
            logger.log("GET FAILED: Key=\"" + key + "\" not found.");
            return "ERROR: Key not found.";
        }
    }

    /**
     * Deletes the key-value pair associated with a given key.
     *
     * @param key The key to delete.
     * @return A status message indicating success or failure.
     * @throws RemoteException If a remote communication error occurs.
     */
    @Override
    public String delete(String key) throws RemoteException {
        if (store.containsKey(key)) {
            store.remove(key);
            logger.log("DELETE: Key=\"" + key + "\" removed successfully.");
            return "OK: Key deleted successfully.";
        } else {
            logger.log("DELETE FAILED: Key=\"" + key + "\" does not exist.");
            return "ERROR: Key does not exist.";
        }
    }

    /**
     * Displays all key-value pairs in the store.
     *
     * @return A string representation of all key-value pairs, or a message if the store is empty.
     * @throws RemoteException If a remote communication error occurs.
     */
    @Override
    public String show() throws RemoteException {
        if (store.isEmpty()) {
            logger.log("SHOW: The key-value store is empty.");
            return "The key-value store is empty.";
        } else {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : store.entrySet()) {
                sb.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
            }
            logger.log("SHOW: Displaying all key-value pairs.");
            return sb.toString().trim();
        }
    }
}
