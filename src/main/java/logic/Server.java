package logic;

import managers.FileManager;
import managers.JsonManager;

import java.io.File;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/***
 * Class that presents functional of file server
 */
public class Server {

    //Filename of server public key
    private static final String publicKey = "public.key";
    //Filename of server private key
    private static final String privateKey = "private.key";
    //Phrase to check keys on valid
    private static final String phrase = "Neque porro quisquam est qui dolorem ipsum";
    private static boolean isInited = false;
    private static boolean isAuthenticate = false;
    private static User usr;
    private static RSA rsa;
    //Reserved names of dirs
    private static String[] reserved = {"users", "keys", "temp"};

    /***
     * Method checks keys, if they are created and are valid they load, otherwise they create and write in file
     */
    private static void init() {
        File publicKey = new File(Server.publicKey);
        File privateKey = new File(Server.privateKey);
        rsa = new RSA(publicKey.getName(), privateKey.getName());
        if (!publicKey.exists() || !privateKey.exists() ||
                !new String(rsa.decrypt(rsa.encrypt(phrase, rsa.getPublicKey()))).equals(phrase)) {
            rsa = new RSA();
            FileManager.writeInFile(rsa.getPublicKey(), Server.publicKey);
            FileManager.writeInFile(rsa.getPrivateKey(), Server.privateKey);
        }
        File file = new File("keys");
        if (!file.exists() || !file.isDirectory())
            file.mkdir();
        file = new File("users");
        if (!file.exists() || !file.isDirectory())
            file.mkdir();
        file = new File("tmp");
        if (!file.exists() || !file.isDirectory())
            file.mkdir();
        isInited = true;
    }

    /***
     * Method stands by process of authentication
     * @param username User's nickname
     * @param password User's password
     * @return Result of authentication process (success or failure)
     */
    public static boolean authenticate(String username, String password) {
        if (!isInited)
            init();
        if (isAuthenticate)
            logoff();
        if (!userExists(username))
            return false;
        JsonManager.init(new File(String.format("users%s%s", File.separator, username)).getPath());
        try {
            usr = JsonManager.toJavaObject();
            if (!usr.getPassword().equals(password))
                return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        isAuthenticate = true;
        return true;
    }

    /***
     * Method realises process of registration
     * @param username
     * @param password
     * @return If success return private key of user, else null
     */
    public static PrivateKey registrateNewUser(String username, String password) {
        if (!isInited)
            init();
        for (String reserve : reserved)
            if (reserve.equals(username))
                return null;
        if (userExists(username))
            return null;
        File file = new File(String.format("users%s%s", File.separator, username));
        User usr = new User(username, password);
        usr.setPublicKey(String.format("keys%s%s.key", File.separator, username));
        PrivateKey pk = RSA.generateKeys(username);
        JsonManager.init(file.getPath());
        file = new File(username);
        if (file.exists())
            file.delete();
        file.mkdir();
        try {
            JsonManager.toJSON(usr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pk;
    }

    /***
     * Method that returns public key of asked registered user
     * @param username User's nickname
     * @return Public key of asked user
     */
    public static byte[] getPublicKey(String username) {
        if (!isInited)
            init();
        File file = new File(username);
        if (file.exists() && file.isDirectory())
            return FileManager.readBytesFromFile(String.format("keys%s%s.key", File.separator, username));
        return null;
    }

    /***
     * Method that returns public key of Server
     * @return Public key of server
     */
    public static PublicKey getPublicKey() {
        if (!isInited)
            init();
        return (PublicKey) FileManager.readObjectFromFile(publicKey);
    }

    /***
     * Method for putting file on server with given recipient
     * @param file File
     * @param username Recipient
     * @return Result of putting file on server (success or failure)
     */
    public static boolean takeFile(byte[] file, String username) {
        if (userExists(username)) {
            FileManager.writeInFile(rsa.encrypt(file,
                    (PublicKey) FileManager.readObjectFromFile(String.format("keys%s%s.key",
                            File.separator, username))),
                    String.format("%s%s%s %s.txt", username, File.separator, usr.getUsername().toLowerCase(),
                            new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(Calendar.getInstance().getTime())));
            return true;
        }
        return false;
    }

    /***
     * Method for checking is given user already exist
     * @param username User's name to check
     * @return Result of checking (exist or not)
     */
    public static boolean userExists(String username) {
        File file = new File(String.format("users%s%s", File.separator, username));
        return file.exists() && file.isFile();
    }

    /***
     * Sets isAuthenticate property to false and usr property to null
     */
    public static void logoff() {
        if (isAuthenticate) {
            isAuthenticate = false;
            usr = null;
        }
    }

    /***
     * Method that returns instance of user's home directory
     * @return List of files
     */
    public static String[] ls() {
        List<String> lst = new ArrayList<String>();
        if (isAuthenticate) {
            File homeDir = new File(usr.getHome());
            File[] listOfFiles = homeDir.listFiles();
            for (int i = 0; i < listOfFiles.length; i++)
                if (listOfFiles[i].isFile())
                    lst.add(listOfFiles[i].getName());
        }
        return lst.toArray(new String[0]);
    }

    /***
     * Method for getting file from server
     * @param filename Name of file to get
     * @param pk Private Key of user (to decrypt)
     * @return Decrypted file
     */
    public static byte[] getFile(String filename, PrivateKey pk) {
        File file = new File(usr.getHome() + "/" + filename);
        if (file.exists()) {
            return rsa.decrypt(FileManager.readBytesFromFile(file.getPath()), pk);
        }
        return null;
    }
}
