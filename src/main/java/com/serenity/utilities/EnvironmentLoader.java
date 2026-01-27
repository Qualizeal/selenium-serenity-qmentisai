package com.serenity.utilities;

/**
 * Simple Environment Loader
 * Returns system environment variables for CI/CD pipeline integration
 */
public class EnvironmentLoader {

    /**
     * Get environment variable value from system
     * 
     * @param key - environment variable key
     * @return value or null if not found
     */
    public static String get(String key) {
        return System.getenv(key);
    }

    /**
     * Get environment variable with default value
     * @param key - environment variable key
     * @param defaultValue - default value if not found
     * @return value or default
     */
    public static String get(String key, String defaultValue) {
        String value = System.getenv(key);
        return value != null ? value : defaultValue;
    }

    /**
     * Check if environment variable exists
     * @param key - environment variable key
     * @return true if exists
     */
    public static boolean has(String key) {
        return System.getenv(key) != null;
    }

    /**
     * Check if running in CI/CD pipeline
     * Detects common CI environment variables
     * @return true if running in pipeline
     */
    public static boolean isRunningInPipeline() {
        return System.getenv("CI") != null || 
               System.getenv("JENKINS_HOME") != null ||
               System.getenv("GITLAB_CI") != null ||
               System.getenv("GITHUB_ACTIONS") != null ||
               System.getenv("AZURE_PIPELINES") != null;
    }
}
