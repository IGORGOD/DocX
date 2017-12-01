package logic;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.codec.digest.DigestUtils;

/***
 * Class that realises functionality of User
 */
public class User {

    @JsonProperty("Username")
    private String username;
    @JsonProperty("Password")
    private String password;
    @JsonProperty("PublicKey")
    private String publicKey;
    @JsonProperty("Home")
    private String home;

    public User(){
    }

    /***
     * Create user with given nickname and password
     * @param username Username
     * @param password Password
     */
    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.home = username.toLowerCase();
    }

    /***
     * Create user with given username, password, public key and home directory
     * @param username Username
     * @param password Password
     * @param publicKey User's public key
     * @param home Path to user's home directory
     */
    public User(String username, String password, String publicKey, String home){
        this.username = username;
        this.password = password;
        this.publicKey = publicKey;
        this.home = home;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    @Override
    public String toString(){
        return String.format("User[Username: %s, Password: %s, PublicKey: %s, Home: %s]", username,
                password, publicKey, home);
    }
}
