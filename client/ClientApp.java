//package client;
//
//import server.KeyValueStoreInterface;
//import utils.ILogger;
//import utils.Logger;
//
//import java.rmi.registry.LocateRegistry;
//import java.rmi.registry.Registry;
//import java.util.Scanner;
//
///**
// * ClientApp is the client-side application that interacts with the Key-Value Store RMI server.
// * It allows users to perform PUT, GET, DELETE, and SHOW operations.
// */
//public class ClientApp {
//
//    private final KeyValueStoreInterface keyValueStore;
//    private final ILogger logger;
//    private final Scanner scanner;
//
//    /**
//     * Constructs a new ClientApp and connects to the RMI server.
//     *
//     * @param host The hostname where the RMI server is running.
//     * @param port The port number of the RMI registry.
//     */
//    public ClientApp(String host, int port) {
//        logger = new Logger("ClientLogger", "ClientLog.log");
//        scanner = new Scanner(System.in);
//        KeyValueStoreInterface tempStore = null;
//
//        try {
//            // Locate the RMI registry and lookup the remote object
//            Registry registry = LocateRegistry.getRegistry(host, port);
//            tempStore = (KeyValueStoreInterface) registry.lookup("KeyValueStore");
//            logger.log("Connected to KeyValueStore at " + host + ":" + port + ".");
//            System.out.println("Connected to the server at " + host + ":" + port + ".");
//        } catch (Exception e) {
//            logger.log("Client exception: " + e.getMessage());
//            System.err.println("Client exception: " + e.getMessage());
//            e.printStackTrace();
//            logger.close();
//            scanner.close();
//            System.exit(1);
//        }
//
//        keyValueStore = tempStore;
//    }
//
//    /**
//     * Pre-populates the key-value store with initial data.
//     * Inserts predefined key-value pairs into the store.
//     */
//    public void prePopulate() {
//        try {
//            logger.log("Pre-populating the key-value store with initial data...");
//            System.out.println("Pre-populating the key-value store with initial data...");
//            String[] initialData = {
//                    "PUT apple red",
//                    "PUT banana yellow",
//                    "PUT grape purple",
//                    "PUT lemon yellow",
//                    "PUT cherry red"
//            };
//
//            for (String command : initialData) {
//                String[] tokens = command.split("\\s+");
//                String response = keyValueStore.put(tokens[1], tokens[2]);
//                logger.log("Executed: " + command + " | Response: " + response);
//                System.out.println("Executed: " + command + " | Response: " + response);
//            }
//
//            System.out.println("Pre-population completed.");
//            logger.log("Pre-population completed.");
//
//        } catch (Exception e) {
//            logger.log("Error during pre-population: " + e.getMessage());
//            System.out.println("ERROR: Unable to pre-populate the store.");
//        }
//    }
//
//    /**
//     * Starts the client application, allowing user interaction for CRUD operations.
//     */
//    public void start() {
//        boolean running = true;
//
//        while (running) {
//            System.out.print("Enter command (PUT key value | GET key | DELETE key | SHOW | exit): ");
//            String input = scanner.nextLine().trim();
//
//            if (input.equalsIgnoreCase("exit")) {
//                running = false;
//                break;
//            }
//
//            String[] tokens = input.split("\\s+");
//            if (tokens.length < 1) {
//                System.out.println("Invalid command. Please try again.");
//                continue;
//            }
//
//            String command = tokens[0].toUpperCase();
//            String key = tokens.length >= 2 ? tokens[1] : "";
//            String value = tokens.length >= 3 ? tokens[2] : "";
//
//            String response = "";
//            try {
//                switch (command) {
//                    case "PUT":
//                        if (value.isEmpty()) {
//                            System.out.println("PUT command requires a key and a value.");
//                            continue;
//                        }
//                        response = keyValueStore.put(key, value);
//                        break;
//                    case "GET":
//                        if (key.isEmpty()) {
//                            System.out.println("GET command requires a key.");
//                            continue;
//                        }
//                        response = keyValueStore.get(key);
//                        break;
//                    case "DELETE":
//                        if (key.isEmpty()) {
//                            System.out.println("DELETE command requires a key.");
//                            continue;
//                        }
//                        response = keyValueStore.delete(key);
//                        break;
//                    case "SHOW":
//                        if (tokens.length != 1) {
//                            System.out.println("SHOW command does not require additional arguments.");
//                            continue;
//                        }
//                        response = keyValueStore.show();
//                        break;
//                    default:
//                        System.out.println("Unknown command. Please use PUT, GET, DELETE, SHOW, or exit.");
//                        continue;
//                }
//
//                logger.log("Executed Command: " + input + " | Response: " + response);
//                System.out.println("Server response: " + response);
//
//            } catch (Exception e) {
//                logger.log("Error executing command '" + input + "': " + e.getMessage());
//                System.out.println("ERROR: " + e.getMessage());
//            }
//        }
//
//        shutdown();
//    }
//
//    /**
//     * Shuts down the client application gracefully.
//     */
//    public void shutdown() {
//        System.out.println("Client is shutting down...");
//        logger.log("Client is shutting down.");
//        scanner.close();
//        logger.close();
//    }
//
//    /**
//     * The main method serves as the entry point of the ClientApp.
//     *
//     * @param args Command-line arguments: <server-host> <server-port>
//     */
//    public static void main(String[] args) {
//        if (args.length != 2) {
//            System.out.println("Usage: java client.ClientApp <server-host> <server-port>");
//            System.exit(1);
//        }
//
//        String host = args[0];
//        int port = 0;
//
//        try {
//            port = Integer.parseInt(args[1]);
//        } catch (NumberFormatException e) {
//            System.out.println("Invalid port number. Please enter a valid integer.");
//            System.exit(1);
//        }
//
//        ClientApp app = new ClientApp(host, port);
//        app.prePopulate();
//        app.start();
//    }
//}

package client;

import server.KeyValueStoreInterface;
import utils.ILogger;
import utils.Logger;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * ClientApp is the client-side application that interacts with the Key-Value Store RMI server.
 * It allows users to perform PUT, GET, DELETE, and SHOW operations.
 */
public class ClientApp {

    private final KeyValueStoreInterface keyValueStore;
    private final ILogger logger;
    private final Scanner scanner;

    /**
     * Constructs a new ClientApp and connects to the RMI server.
     *
     * @param host The hostname where the RMI server is running.
     * @param port The port number of the RMI registry.
     */
    public ClientApp(String host, int port) {
        logger = new Logger("ClientLogger", "ClientLog.log");
        scanner = new Scanner(System.in);
        KeyValueStoreInterface tempStore = null;

        try {
            // Locate the RMI registry and lookup the remote object
            Registry registry = LocateRegistry.getRegistry(host, port);
            tempStore = (KeyValueStoreInterface) registry.lookup("KeyValueStore");
            logger.log("Connected to KeyValueStore at " + host + ":" + port + ".");
            System.out.println("Connected to the server at " + host + ":" + port + ".");
        } catch (Exception e) {
            logger.log("Client exception: " + e.getMessage());
            System.err.println("Client exception: " + e.getMessage());
            e.printStackTrace();
            logger.close();
            scanner.close();
            System.exit(1);
        }

        keyValueStore = tempStore;
    }

    /**
     * Pre-populates the key-value store with initial data.
     * Inserts predefined key-value pairs into the store.
     */
    public void prePopulate() {
        try {
            logger.log("Pre-populating the key-value store with initial data...");
            System.out.println("Pre-populating the key-value store with initial data...");
            String[] initialData = {
                    "PUT apple red",
                    "PUT banana yellow",
                    "PUT grape purple",
                    "PUT lemon yellow",
                    "PUT cherry red"
            };

            for (String command : initialData) {
                String[] tokens = command.split("\\s+");
                String response = keyValueStore.put(tokens[1], tokens[2]);
                logger.log("Executed: " + command + " | Response: " + response);
                System.out.println("Executed: " + command + " | Response: " + response);
            }

            System.out.println("Pre-population completed.");
            logger.log("Pre-population completed.");

        } catch (Exception e) {
            logger.log("Error during pre-population: " + e.getMessage());
            System.out.println("ERROR: Unable to pre-populate the store.");
        }
    }

    /**
     * Starts the client application, allowing user interaction for CRUD operations.
     */
    public void start() {
        boolean running = true;

        while (running) {
            System.out.print("Enter command (PUT key value | GET key | DELETE key | SHOW | exit): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                running = false;
                break;
            }

            String[] tokens = input.split("\\s+");
            if (tokens.length < 1) {
                System.out.println("Invalid command. Please try again.");
                continue;
            }

            String command = tokens[0].toUpperCase();
            String key = tokens.length >= 2 ? tokens[1] : "";
            String value = tokens.length >= 3 ? tokens[2] : "";

            String response = "";
            try {
                switch (command) {
                    case "PUT":
                        if (value.isEmpty()) {
                            System.out.println("PUT command requires a key and a value.");
                            continue;
                        }
                        response = keyValueStore.put(key, value);
                        break;
                    case "GET":
                        if (key.isEmpty()) {
                            System.out.println("GET command requires a key.");
                            continue;
                        }
                        response = keyValueStore.get(key);
                        break;
                    case "DELETE":
                        if (key.isEmpty()) {
                            System.out.println("DELETE command requires a key.");
                            continue;
                        }
                        response = keyValueStore.delete(key);
                        break;
                    case "SHOW":
                        if (tokens.length != 1) {
                            System.out.println("SHOW command does not require additional arguments.");
                            continue;
                        }
                        response = keyValueStore.show();
                        break;
                    default:
                        System.out.println("Unknown command. Please use PUT, GET, DELETE, SHOW, or exit.");
                        continue;
                }

                logger.log("Executed Command: " + input + " | Response: " + response);
                System.out.println("Server response: " + response);

            } catch (Exception e) {
                logger.log("Error executing command '" + input + "': " + e.getMessage());
                System.out.println("ERROR: " + e.getMessage());
            }
        }

        shutdown();
    }

    /**
     * Shuts down the client application gracefully.
     */
    public void shutdown() {
        System.out.println("Client is shutting down...");
        logger.log("Client is shutting down.");
        scanner.close();
        logger.close();
    }

    /**
     * The main method serves as the entry point of the ClientApp.
     *
     * @param args Command-line arguments: <server-host> <server-port>
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java client.ClientApp <server-host> <server-port>");
            System.exit(1);
        }

        String host = args[0];
        int port = 0;

        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid port number. Please enter a valid integer.");
            System.exit(1);
        }

        ClientApp app = new ClientApp(host, port);
        app.prePopulate();
        app.start();
    }

}

