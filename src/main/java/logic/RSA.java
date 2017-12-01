package logic;

import managers.FileManager;
import org.apache.commons.lang3.ArrayUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.security.*;
import java.util.ArrayList;
import java.util.List;

/***
 * Class that realises work of RSA crypting and decrypting
 */
public class RSA {

    private static final String ALGORITHM = "RSA";
    //Length of byte block for decrypting
    private static final short D_BLOCK_SIZE = 128;
    ///Length of byte block for encrypting
    private static final short E_BLOCK_SIZE = 117;

    private Cipher cipher;

    private String privateKey;
    private String publicKey;

    public RSA() {
        try {
            cipher = Cipher.getInstance(ALGORITHM);
            privateKey = "private.key";
            publicKey = "public.key";
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALGORITHM);
            kpg.initialize(1024);
            final KeyPair keys = kpg.generateKeyPair();
            FileManager.writeInFile(keys.getPrivate(), privateKey);
            FileManager.writeInFile(keys.getPublic(), publicKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    /***
     * Create RSA with given keys
     * @param publicKey Public Key
     * @param privateKey Private Key
     */
    public RSA(String publicKey, String privateKey){
        try{
            cipher = Cipher.getInstance(ALGORITHM);
            this.privateKey = privateKey;
            this.publicKey = publicKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    /***
     * Method that generates public and private keys for given user
     * and writes public key in public keys directory on the server
     * @param username User's name
     * @return Private Key
     */
    public static PrivateKey generateKeys(String username){
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALGORITHM);
            kpg.initialize(1024);
            final KeyPair keys = kpg.generateKeyPair();
            FileManager.writeInFile(keys.getPublic(), String.format("keys%s%s.key", File.separator, username));
            return keys.getPrivate();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * Method that splits byte array on parts of needed for RSA length
     * @param bytes Byte array
     * @param partLength Length of part
     * @return List of byte parts
     */
    private static List<byte[]> splitByteArray(byte[] bytes, int partLength) {
        List<byte[]> byteList = new ArrayList<byte[]>();
        int blockNum = bytes.length / partLength + (bytes.length % partLength == 0 ? 0 : 1);
        for (int i = 0; i < blockNum; i++) {
            int length = i != blockNum - 1 ? partLength : bytes.length % partLength;
            byte[] buf = new byte[length];
            System.arraycopy(bytes, i * partLength, buf, 0, length);
            byteList.add(buf.clone());
        }
        return byteList;
    }

    public PublicKey getPublicKey() {
        return (PublicKey) FileManager.readObjectFromFile(publicKey);
    }

    public PrivateKey getPrivateKey() {
        return (PrivateKey) FileManager.readObjectFromFile(privateKey);
    }

    /***
     * Method for encrypting and decrypting demands on given mode
     * @param bytes List of byte parts
     * @param key Public or Private key to use
     * @param mode true - encrypting, fakse - decrypting
     * @return
     */
    private byte[] make(byte[] bytes, Key key, boolean mode) {
        int partLength = mode ? E_BLOCK_SIZE : D_BLOCK_SIZE;
        try {
            cipher.init(mode ? 1 : 2, key);
            if (bytes.length <= partLength) {
                return cipher.doFinal(bytes);
            }
            List<Byte> lst = new ArrayList<Byte>();
            for (byte[] textB : splitByteArray(bytes, partLength)) {
                if(textB.length > 0) {
                    byte[] buf = cipher.doFinal(textB);
                    for (byte aBuf : buf) lst.add(aBuf);
                }
            }
            return ArrayUtils.toPrimitive(lst.toArray(new Byte[0]));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    /***
     * Encrypt given string text with given key
     * @param text Text
     * @param key Key
     * @return Encrypted array of bytes
     */
    public byte[] encrypt(String text, PublicKey key) {
        return encrypt(text.getBytes(), key);
    }

    /***
     * Encrypt given array of bytes with given key
     * @param bytes Byte array
     * @param key Key
     * @return Encrypted array of bytes
     */
    public byte[] encrypt(byte[] bytes, PublicKey key) {
        return make(bytes, key, true);
    }

    /***
     * Decrypt given array of bytes with Private Key of RSA object
     * @param bytes Array of bytes
     * @return Decrypted array of bytes
     */
    public byte[] decrypt(byte[] bytes) {
        return make(bytes, (PrivateKey) FileManager.readObjectFromFile(privateKey), false);
    }

    /***
     * Decrypt given array of bytes with given private key
     * @param bytes Array of bytes
     * @param pk Private key
     * @return Decrypted array of bytes
     */
    public byte[] decrypt(byte[] bytes, PrivateKey pk) {
        return make(bytes, pk, true);
    }
}
