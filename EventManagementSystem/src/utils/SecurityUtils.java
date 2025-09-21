package utils;

/**
 * Utility class for security-related operations
 */
public class SecurityUtils {
    
    /**
     * Verifies a password against a stored password using simple string comparison.
     * This should only be used when passwords are stored in plain text (not recommended).
     *
     * @param password the password to verify
     * @param storedPassword the stored plain text password
     * @return VerificationResult containing success status
     */
    public static VerificationResult verifyPassword(String password, String storedPassword) {
        try {
            System.out.println("Verifying plain text password");
            
            // Simple string comparison for plain text passwords
            if (password.equals(storedPassword)) {
                System.out.println("Password verification successful");
                return new VerificationResult(true, null);
            }
            
            System.out.println("Password verification failed");
            return new VerificationResult(false, null);
        } catch (Exception e) {
            System.out.println("Error during password verification: " + e.getMessage());
            e.printStackTrace();
            return new VerificationResult(false, null);
        }
    }

    /**
     * Result class for password verification
     */
    public static class VerificationResult {
        private final boolean success;
        private final String newHash;

        public VerificationResult(boolean success, String newHash) {
            this.success = success;
            this.newHash = newHash;
        }

        public boolean isSuccess() {
            return success;
        }

        public boolean needsMigration() {
            return newHash != null;
        }

        public String getNewHash() {
            return newHash;
        }
    }
}