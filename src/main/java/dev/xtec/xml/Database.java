package dev.xtec.xml;

import org.basex.*;
import org.basex.api.client.*;
import org.basex.core.*;
import org.basex.core.cmd.*;

import java.io.IOException;

public class Database {

    public static void init() throws Exception {
        System.out.println("=== ServerAndLocal ===");

        // Create global context
        final Context context = new Context();

        // Start server
        System.out.println("\n* Start server.");
        BaseXServer server = new BaseXServer();

        // Create a client session with host name, port, user name and password
        System.out.println("\n* Create a client session.");

        try (ClientSession session = new ClientSession("localhost", 1984, "admin", "admin")) {
            // Locally cache the result of a server-side query
            System.out.println("\n* Cache server-side query results.");

            String result = send(session,
                    "XQUERY for $x in doc('src/main/resources/xml/input.xml') return $x");

            // Create a local database from the XML result string
            System.out.println("\n* Create a local database.");

            new CreateDB("LocalDB", result).execute(context);

            // Run a query on the locally cached results
            System.out.print("\n* Run a local query: ");

            System.out.println(new XQuery("//title").execute(context));
        }

        // Stop the server
        System.out.println("\n* Stop the server.");
        server.stop();

        // Drop the local database
        System.out.println("\n* Drop the local database.");

        new DropDB("LocalDB").execute(context);

        // Close context
        context.close();

    }

    private static String send(final ClientSession session, final String command) throws IOException {
        // Execute the command
        return session.execute(command);
    }
}
