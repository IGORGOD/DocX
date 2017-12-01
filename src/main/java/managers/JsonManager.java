package managers;

import com.fasterxml.jackson.databind.ObjectMapper;
import logic.User;

import java.io.File;
import java.io.IOException;

/***
 * Class that helps to work with JSON files
 */
public class JsonManager {

    private static String baseFile;
    private static boolean isInited = false;

    /***
     * Init manager with given filepath
     * @param path Filepath
     */
    public static void init(String path) {
        isInited = true;
        baseFile = path;
    }

    /***
     * Write User object to JSON file
     * @param user User object
     * @throws IOException
     */
    public static void toJSON(User user) throws IOException {
        if (isInited) {
            File file = new File(baseFile);
            if (file.exists())
                file.delete();
            file.createNewFile();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(baseFile), user);
        }
    }

    /***
     * Read User object from JSON file
     * @return User object
     * @throws IOException
     */
    public static User toJavaObject() throws IOException {
        if (isInited) {
            File file = new File(baseFile);
            if(!file.exists() || !file.isFile())
                return null;
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(file, User.class);
        }
        return null;
    }
}
