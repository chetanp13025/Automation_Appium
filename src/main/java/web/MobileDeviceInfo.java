package web;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MobileDeviceInfo extends Properties {
public	static String platformVersion;
public static String deviceName;
public static void fetch() {
//	public static void main(String[] args) {
        try {
            // Get the path to the adb executable
            String adbPath = getAdbExecutablePath();

            if (adbPath != null) {
                // Execute adb command to list connected devices
                Process process = Runtime.getRuntime().exec(adbPath + " devices");

                // Read the output of the command
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                List<String> deviceList = new ArrayList<String>();

                // Parse the output to extract connected device IDs
                while ((line = reader.readLine()) != null) {
                    if (!line.startsWith("List of devices") && !line.trim().isEmpty()) {
                        String[] parts = line.split("\\s+");
                        if (parts.length > 1) {
                            deviceList.add(parts[0]);
                        }
                    }
                }

                // Close the BufferedReader
                reader.close();

                // Fetch device information for each connected device
                for (String device : deviceList) {
                    fetchDeviceInfo(adbPath, device);
                }
            } else {
                System.out.println("adb executable not found. Please make sure Android SDK is installed.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to fetch device information for a specific device
    private static void fetchDeviceInfo(String adbPath, String device) {
        try {
            // Execute adb command to get device information
            Process process = Runtime.getRuntime().exec(adbPath + " -s " + device + " shell getprop");

            // Read the output of the command
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            String platformName = "";
            String platformVersion = "";
            String deviceName = "";

            // Parse the output to extract required device information
            while ((line = reader.readLine()) != null) {
                if (line.contains("[ro.product.brand]:")) {
                    platformName = line.substring(line.indexOf(":") + 1).trim();
                } else if (line.contains("[ro.build.version.release]:")) {
                    platformVersion = line.substring(line.indexOf(":") + 1).trim();
                } else if (line.contains("[ro.product.model]:")) {
                    deviceName = line.substring(line.indexOf(":") + 1).trim();
                }
            }

            // Output device information
            System.out.println("Device ID: " + device);
            System.out.println("Platform Name: " + platformName);
            System.out.println("Platform Version: " + platformVersion);
            System.out.println("Device Name: " + deviceName);
            System.out.println();

            // Close the BufferedReader
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to get the path to the adb executable
    private static String getAdbExecutablePath() {
        // Check common locations where adb might be installed
        String[] possiblePaths = {
            System.getenv("ANDROID_HOME") + File.separator + "platform-tools" + File.separator + "adb",
            System.getProperty("user.home") + File.separator + "Android" + File.separator + "Sdk" + File.separator + "platform-tools" + File.separator + "adb",
            System.getProperty("user.home") + File.separator + "Library" + File.separator + "Android" + File.separator + "Sdk" + File.separator + "platform-tools" + File.separator + "adb",
            "/usr/local/bin/adb", // macOS default installation location
            "/usr/bin/adb" // Linux default installation location
        };

        // Check each possible path and return the first one that exists
        for (String path : possiblePaths) {
            File adbFile = new File(path);
            if (adbFile.exists() && adbFile.canExecute()) {
                return adbFile.getAbsolutePath();
            }
        }

        return null; // adb executable not found
    }
}
