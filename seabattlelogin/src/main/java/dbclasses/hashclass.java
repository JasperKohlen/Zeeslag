package dbclasses;

import org.mindrot.jbcrypt.BCrypt;

public class hashclass {
    private static int workload = 12;

    public static String hashPassword(String passwordPlaintext) {
        String salt = BCrypt.gensalt(workload);
        return BCrypt.hashpw(passwordPlaintext, salt);
    }

    public static boolean checkPassword(String password_plaintext, String storedHash) {
        boolean passwordVerified = false;

        if(null == storedHash || !storedHash.startsWith("$2a$"))
            throw new IllegalArgumentException("Invalid hash provided for comparison");

        passwordVerified = BCrypt.checkpw(password_plaintext, storedHash);

        return(passwordVerified);
    }
}
