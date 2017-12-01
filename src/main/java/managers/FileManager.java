package managers;

import org.apache.commons.io.FileUtils;

import java.io.*;

/***
 * Class for work make read/write operations with files
 */
public class FileManager {

    /***
     * Write Object in file
     * @param obj Object
     * @param fileName Path to file
     */
    public static void writeInFile(Object obj, String fileName) {
        File file = new File(fileName);
        try {
            if (!file.exists())
                file.createNewFile();
            ObjectOutputStream objOS = new ObjectOutputStream(new FileOutputStream(file));
            objOS.writeObject(obj);
            objOS.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     * Write byte array in file
     * @param bytes Array of bytes
     * @param fileName Filepath
     */
    public static void writeInFile(byte[] bytes, String fileName) {
        File file = new File(fileName);
        try {
            if (!file.exists())
                file.createNewFile();
            FileUtils.writeByteArrayToFile(file, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     *Read bytes form file
     * @param fileName Filepath
     * @return Array of bytes
     */
    public static byte[] readBytesFromFile(String fileName) {
        File file = new File(fileName);
        try {
            if (!file.exists())
                file.createNewFile();
            return FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    /***
     * Read Object from file
     * @param fileName Filepath
     * @return Object from file
     */
    public static Object readObjectFromFile(String fileName) {
        try {
            return new ObjectInputStream(new FileInputStream(fileName)).readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
