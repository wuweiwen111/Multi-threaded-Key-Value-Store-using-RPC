package client;

/**
 * Main class to launch the ClientApp.
 */
public class Main {
    /**
     * Main method to start the client application.
     *
     * @param args Command-line arguments. Expects two arguments: hostname and port number.
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java client.Main <host> <port-number>");
            System.exit(1);
        }

        String host = args[0];
        int port;

        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid port number. Please enter a valid integer.");
            System.exit(1);
            return; // Unreachable, but added to satisfy the compiler
        }

        ClientApp client = new ClientApp(host, port);
        client.prePopulate();
        client.start();
    }
}
