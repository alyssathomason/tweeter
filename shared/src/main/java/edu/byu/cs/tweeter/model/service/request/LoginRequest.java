package edu.byu.cs.tweeter.model.service.request;

/**
 * Contains all the information needed to make a login request.
 */
public class LoginRequest {

<<<<<<< Updated upstream:shared/src/main/java/edu/byu/cs/tweeter/model/service/request/LoginRequest.java
    private String username;
    private String password;

    /**
     * Allows construction of the object from Json. Private so it won't be called in normal code.
     */
    private LoginRequest() {}
=======
    private final String alias;
    private final String password;
>>>>>>> Stashed changes:app/src/main/java/edu/byu/cs/tweeter/model/service/request/LoginRequest.java

    /**
     * Creates an instance.
     *
     * @param alias the username of the user to be logged in.
     * @param password the password of the user to be logged in.
     */
    public LoginRequest(String alias, String password) {
        this.alias = alias;
        this.password = password;
    }

    /**
     * Returns the username of the user to be logged in by this request.
     *
     * @return the alias.
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Sets the username.
     *
     * @param username the username.
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Returns the password of the user to be logged in by this request.
     *
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the password.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
