package hr.fer.oprpp1.hw05.crypto;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

/**
 * Class representing a CLI for some cryptography methods.
 */
public class Crypto {

    /**
     * Constant representing a byte buffer size.
     */
    private static final int BUFFER_SIZE = 1024;

    /**
     * A CLI for some cryptography methods.
     * @param args Desired method (encrypt/decrypt/checksha) and its argument(s)
     */
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        if (args.length < 2) {
            throw new IllegalArgumentException("Invalid number of arguments!");
        }

        if (args[0].equals("checksha")) {
            if (args.length > 2) {
                throw new IllegalArgumentException("Usage: checksha %filename%");
            }

            Crypto.checksha(args[1]);
        }

        if (args[0].equals("encrypt")) {
            if (args.length > 3) {
                throw new IllegalArgumentException("Usage: encrypt %file_to_encrypt% %encrypted_file%");
            }

            Crypto.crypt(args[1], args[2], true);
        }

        if (args[0].equals("decrypt")) {
            if (args.length > 3) {
                throw new IllegalArgumentException("Usage: decrypt %file_to_decrypt% %decrypted_file%");
            }

            Crypto.crypt(args[1], args[2], false);
        }
    }

    /**
     * Method for checking an SHA value for the provided file.
     * @param path_to_file Path to the file
     */
    private static void checksha(String path_to_file) throws IOException, NoSuchAlgorithmException {
        Scanner in = new Scanner(System.in);
        System.out.print("Please provide expected sha-256 digest for " + path_to_file + ":\n> ");

        String digest = in.nextLine().trim();
        String calc_digest = calculateDigest(path_to_file);

        System.out.print("Digesting completed. ");

        if (!digest.equals(calc_digest)) {
            System.out.println("Digest of " + path_to_file + " does not match the expected digest. Digest was: " +
                    calc_digest);
            return;
        }

        System.out.println("Digest of " + path_to_file + " matches expected digest.");
    }

    /**
     * Method for (en/de)crypting a file.
     * @param path_to_original File to be encrypted
     * @param path_to_new Path to the encrypted file
     * @param encrypt Boolean representing if the method is used for encrypting
     */
    private static void crypt(String path_to_original, String path_to_new, boolean encrypt) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IOException {
        Scanner in = new Scanner(System.in);

        System.out.print("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):\n> ");
        String password = in.nextLine().trim();
        if (password.length() != 32) throw new IllegalArgumentException("Password length must be 32!");
        if (!(password.matches("^[0-9a-fA-F]+$")))
            throw new IllegalArgumentException("Password must have only valid hex digits!");

        System.out.print("Please provide initialization vector as hex-encoded text (32 hex-digits):\n> ");
        String vector = in.nextLine().trim();
        if (vector.length() != 32) throw new IllegalArgumentException("Vector length must be 32!");
        if (!(vector.matches("^[0-9a-fA-F]+$")))
            throw new IllegalArgumentException("Vector must have only valid hex digits!");

        SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(password), "AES");
        AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(vector));
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

        FileInputStream inputStream = new FileInputStream(path_to_original);
        FileOutputStream outputStream = new FileOutputStream(path_to_new);

        CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
        byte[] bytes = new byte[BUFFER_SIZE];
        int count;

        while ((count = cipherInputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, count);
        }

        inputStream.close();
        outputStream.close();
        cipherInputStream.close();

        if (encrypt) {
            System.out.print("Encryption completed. ");
        } else {
            System.out.print("Decryption completed. ");
        }

        System.out.println("Generated file " + path_to_new + " based on file " +
                path_to_original + ".");
    }

    /**
     * Method for calculating file digest.
     * @param path_to_file Path to the file
     * @return Calculated digest
     */
    private static String calculateDigest(String path_to_file) throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        InputStream inputStream = new BufferedInputStream(Files.newInputStream(Path.of(path_to_file)));
        byte[] bytes = new byte[BUFFER_SIZE];
        int count;

        while ((count = inputStream.read(bytes)) != -1) {
            digest.update(bytes, 0, count);
        }

        inputStream.close();

        return Util.bytetohex(digest.digest());
    }

}
