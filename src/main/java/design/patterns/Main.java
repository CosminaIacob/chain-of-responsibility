package design.patterns;

import design.patterns.middleware.Middleware;
import design.patterns.middleware.RoleCheckMiddleware;
import design.patterns.middleware.TrottlingMiddleware;
import design.patterns.middleware.UserExistsMiddleware;
import design.patterns.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader((System.in)));

    private static Server server;

    private static void init() {
        server = new Server();
        server.register("admin@example.com", "admin_pass");
        server.register("user@example.com", "user_pass");

        // all checks are linked.
        // client can build various chain using the same components.
        Middleware middleware = Middleware.link(
                new TrottlingMiddleware(2),
                new UserExistsMiddleware(server),
                new RoleCheckMiddleware()
        );

        // server gets a chain from client code.
        server.setMiddleware(middleware);
    }


    public static void main(String[] args) throws IOException {
        init();

        boolean success;
        do {
            System.out.println("Enter email: ");
            String email = reader.readLine();

            System.out.println("Input password: ");
            String password = reader.readLine();

            success = server.login(email, password);
        } while (!success);
    }
}