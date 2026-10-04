package bookstore.model;

/**
 * Abstract superclass for Owner and Customer.
 */
public abstract class User {
    private String username;
    private String password;

    protected User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns true if the given credentials match this user.
     */
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    /**
     * Placeholder method for symmetry in the class design.
     * GUI logic will actually handle screen switching/logout behavior later.
     */
    public void logout() {
        // No state change needed here.
    }
}
