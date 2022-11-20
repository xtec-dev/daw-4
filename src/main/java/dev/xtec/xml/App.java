package daw4;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.jetty.server.Server;
import org.basex.BaseXServer;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class App {

    public static void main(String[] args) {

        final ResourceConfig config = new ResourceConfig().packages("daw4.uf1");
        final Server server = JettyHttpContainerFactory.createServer(
                URI.create("http://localhost:8080/"), config);



        try {

            Database.init();

            server.start();
            server.join();
        } catch (Exception e) {
            // TODO use org.slf4j used by jersey ??
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            server.destroy();
        }
    }
}

@Path("shop")
class ShopResource {

    @GET
    @Path("hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloWorld() {
        return "Hello!";
    }
}


