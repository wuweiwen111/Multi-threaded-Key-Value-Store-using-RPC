package server;

import utils.ILogger;
import utils.Logger;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * ServerApp is the entry point for the Key-Value Store RMI server.
 * It initializes the remote object and binds it to the RMI registry.
 */
public class ServerApp {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java server.ServerApp <port-number>");
            System.exit(1);
        }

        int port;
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid port number. Please enter a valid integer.");
            System.exit(1);
            return; // Unreachable, but added to satisfy the compiler
        }

        ILogger logger = new Logger("ServerLogger", "ServerLog.log");

        try {
            // Instantiate the remote object
            KeyValueStoreImpl keyValueStore = new KeyValueStoreImpl();

            // Create RMI registry on the specified port
            Registry registry = LocateRegistry.createRegistry(port);

            // Bind the remote object to the registry with the name "KeyValueStore"
            registry.rebind("KeyValueStore", keyValueStore);
            logger.log("Server is ready and bound to registry on port " + port + ".");

            System.out.println("Server is ready on port " + port + ".");

            // Add shutdown hook for graceful termination
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    UnicastRemoteObject.unexportObject(keyValueStore, true);
                    logger.log("Server shut down gracefully.");
                    logger.close();
                    System.out.println("Server shut down gracefully.");
                } catch (Exception e) {
                    logger.log("Error during server shutdown: " + e.getMessage());
                    System.err.println("Error during server shutdown: " + e.getMessage());
                }
            }));

            // Keep the server running indefinitely
            while (true) {
                try {
                    Thread.sleep(Long.MAX_VALUE);
                } catch (InterruptedException e) {
                    // Interruption can be ignored as shutdown hook handles cleanup
                }
            }

        } catch (Exception e) {
            logger.log("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
