package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote interface for the Key-Value Store.
 * Defines methods that can be invoked remotely by clients.
 */
public interface KeyValueStoreInterface extends Remote {

    /**
     * Inserts a key-value pair into the store.
     *
     * @param key   The key to insert.
     * @param value The value associated with the key.
     * @return A status message indicating success or failure.
     * @throws RemoteException If a remote communication error occurs.
     */
    String put(String key, String value) throws RemoteException;

    /**
     * Retrieves the value associated with a given key.
     *
     * @param key The key whose value is to be retrieved.
     * @return The value associated with the key, or an error message if the key does not exist.
     * @throws RemoteException If a remote communication error occurs.
     */
    String get(String key) throws RemoteException;

    /**
     * Deletes the key-value pair associated with a given key.
     *
     * @param key The key to delete.
     * @return A status message indicating success or failure.
     * @throws RemoteException If a remote communication error occurs.
     */
    String delete(String key) throws RemoteException;

    /**
     * Displays all key-value pairs in the store.
     *
     * @return A string representation of all key-value pairs, or a message if the store is empty.
     * @throws RemoteException If a remote communication error occurs.
     */
    String show() throws RemoteException;
}
