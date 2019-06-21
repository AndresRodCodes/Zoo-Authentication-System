/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package authentication.system;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * This class holds the username and password for the user logging in.
 * There is a setter for the username and password and only a getter for the 
 * username. A getter is not available for a password for user privacy
 * and system security.
 * 
 * @author Andres
 */
public class UserInfo {
    private String username;
    private String password;
    
    public UserInfo() {
        this.username = "";
        this.password = "";
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    String getUsername() {
        return this.username;
    }
    
    /**
     * Returns MD5 hash password String using the user provided password for 
     * comparing to existing MD5 hash passwords in the credentials.txt file.
     * 
     * @return
     * @throws java.security.NoSuchAlgorithmException
     */
    public String getSecurePassword() throws NoSuchAlgorithmException {
        String original = this.password;
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(original.getBytes());
        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }
    
    /**
     * Returns the user role as a String. It accesses the credentials.txt file
     * and uses a file scanner read the role associated with the entered
     * credentials.
     * It is dependent on knowing the directory of the credentials.txt file 
     * and not removing the plain English passwords in the third column.
     * 
     * @return
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public String getUserRole() throws FileNotFoundException, IOException {
        FileInputStream fileByteStream = null;
        Scanner fileScanner = null;
        String storedUsername = "";
        String userRole = "";
        
        // Open credentials file.
        fileByteStream = new FileInputStream(
                "F:\\SNHU\\Java Class\\Week 7 Final\\credentials.txt");
        fileScanner = new Scanner(fileByteStream);
        
        // Reading credentials file for username match.
        while (!this.username.equals(storedUsername) && fileScanner.hasNext()) {
            storedUsername = fileScanner.next();
        }
        // Navigates from username to user role String.
        for (int i = 0; i < 3; i++) {
            userRole = fileScanner.next();
            
        }
        // Skip over passwords with one space.
        if (userRole.equals("veterinarian") || userRole.equals("admin") || userRole.equals("zookeeper")) {
            fileByteStream.close();
            
            return userRole;
        }
        else {
            userRole = fileScanner.next();
            // Close credentials.txt file.
            fileByteStream.close();
            
            return userRole;
        }
    }
}
