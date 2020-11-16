package edu.byu.cs.tweeter.model.service.request;

/**
 * Contains all the information needed to make a login request.
 */
public class RegisterRequest {
    private final String firstName;
    private final String lastName;
    private final String alias;
    private final String password;

    /**
     * Creates an instance.
     *
     * @param firstName the first name of the user to be registered.
     * @param lastName the last name of the user to be registered.
     * @param alias the alias of the user to be registered.
     * @param password the password of the user to be registered.
     */
    public RegisterRequest(String firstName, String lastName, String alias, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.alias = alias;
        this.password = password;
    }

    /**
     * Returns the first name of the user to be registered by this request.
     *
     * @return the first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name of the user to be registered by this request.
     *
     * @return the last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the formatted full name of the user to be registered by this request.
     *
     * @return the formatted full name.
     */
    public String getName() {
        return String.format("%s %s", firstName, lastName);
    }

    /**
     * Returns the alias of the user to be registered by this request.
     *
     * @return the alias.
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Returns the password of the user to be registered by this request.
     *
     * @return the password.
     */
    public String getPassword() {
        return password;
    }
}
