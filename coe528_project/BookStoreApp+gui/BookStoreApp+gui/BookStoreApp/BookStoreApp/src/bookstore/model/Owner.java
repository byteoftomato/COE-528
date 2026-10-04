package bookstore.model;

/**
 * Represents the single owner/admin of the app.
 * According to the project doc:
 * username = admin
 * password = admin
 */
public class Owner extends User {

    public Owner() {
        super("admin", "admin");
    }
}
