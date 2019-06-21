/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package authentication.system;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * Program authenticates user via username and password using a preexisting 
 * credentials.txt file. Then, displays the users corresponding role file.
 * 
 * @author Andres
 * @version 1.0
 */
public class AuthenticationSystem {

    /**
     * @param args the command line arguments
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws 
            NoSuchAlgorithmException, FileNotFoundException, IOException {
        
        UserInfo user = new UserInfo();
        Scanner scnr = new Scanner(System.in);
        int authenticationAttempts = 0;
        int authenticationTries = 3;
        
        // Zoo system runs until failed attmepts are over 3, or user types exit.
        while ((authenticationAttempts < authenticationTries) 
                && (!user.getUsername().equals("exit"))) {
            
            //Display menu
            System.out.println("Welcome to the zoo system");
            System.out.println("-------------------------");

            // Prompt for username and password.
            System.out.println("Enter username: ");
            user.setUsername(scnr.nextLine());
            
            // If user enters "exit" exit the program.
            if (user.getUsername().equals("exit")) {
                break;
            }
        
            System.out.println("Enter password: ");
            user.setPassword(scnr.nextLine());
        
            // Authenticate credentials. If successful, show corrisponting role
            // file. If unsuccessful, increment authenticationAttempts and
            // show attempts left.
            if (authenticateCredentials(
                    user.getUsername(), user.getSecurePassword()) == true) {
                
                System.out.println("\nLogged in");
                System.out.println("User role: " + user.getUserRole());
                readRoleFile(user.getUserRole());
                
                System.out.println("\nWould you like to logout? y/n");
                String logout = scnr.next();
                while (logout.equals("n")) {
                    readRoleFile(user.getUserRole());
                    System.out.println("Would you like to logout? y/n");
                    logout = scnr.next();
                }
                // End program when user enters "y" to logout.
                break;
            }
            else {
                System.out.println("Incorrect credentials");
                authenticationAttempts++;
                System.out.print("Attemps left: ");
                System.out.println(authenticationTries - authenticationAttempts);
                System.out.println("");
            }
        }
        // "Goodbye" will be displayed when program ends.
        System.out.println("Goodbye");
    }
    
    /**
     * Returns true if the entered username and password are valid credentials.
     * Returns false if the entered credentials are not valid.
     * This method is dependent on knowing the directory of the
     * credentials.txt file.
     * 
     * @param username
     * @param securePassword
     * @return
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static boolean 
        authenticateCredentials(String username, String securePassword) 
            throws FileNotFoundException, IOException {
            
        FileInputStream fileByteStream = null;
        Scanner fileScanner = null;
        String storedUsername = "";
        
        // Open credentials file.
        fileByteStream = new FileInputStream(
                "F:\\SNHU\\Java Class\\Week 7 Final\\credentials.txt");
        fileScanner = new Scanner(fileByteStream);
        
        // Reading credentials file for username match.
        while (!username.equals(storedUsername) && fileScanner.hasNext()) {
            storedUsername = fileScanner.next();
        }
        
        if (username.equals(storedUsername)) {
            // Read credentials file for password match.
            if (securePassword.equals(fileScanner.next())) {
                // Close credentials.txt file
                fileByteStream.close();

                return true;
            }
            else {
                // Close credentials.txt file
                fileByteStream.close();

                return false;
            }
        }
        else {
            // Close credentials.txt file
            fileByteStream.close();
            return false;
        }
    }
        /**
         * Takes the string userRole and uses it to return the the correct 
         * role file String contents associated with the users credentials. 
         * This method is dependant on knowing the directory of the txt files.
         * 
         * @param userRole
         * @throws FileNotFoundException
         * @throws IOException 
         */
    public static void readRoleFile(String userRole) throws FileNotFoundException, IOException {
        FileInputStream fileByteStream = null;
        Scanner fileScanner = null;
        String adminFileDir = "F:\\SNHU\\Java Class\\Week 7 Final\\admin.txt";
        String vetFileDir = "F:\\SNHU\\Java Class\\Week 7 Final\\veterinarian.txt";
        String zookeeperFileDir = "F:\\SNHU\\Java Class\\Week 7 Final\\zookeeper.txt";
        
        switch(userRole) {
            case "admin":
                // Open admin file.
                fileByteStream = new FileInputStream(adminFileDir);
                fileScanner = new Scanner(fileByteStream);
                while (fileScanner.hasNextLine()) {
                    System.out.println(fileScanner.nextLine());
                }
                fileByteStream.close();
                break;
            case "veterinarian" :
                // Open veterinarian file.
                fileByteStream = new FileInputStream(vetFileDir);
                fileScanner = new Scanner(fileByteStream);
                while (fileScanner.hasNextLine()) {
                    System.out.println(fileScanner.nextLine());
                }
                fileByteStream.close();
                break;
            case "zookeeper":
                // Open zookeeper file.
                fileByteStream = new FileInputStream(zookeeperFileDir);
                fileScanner = new Scanner(fileByteStream);
                while (fileScanner.hasNextLine()) {
                    System.out.println(fileScanner.nextLine());
                }
                fileByteStream.close();
                break;
            default:
                System.out.println("Access denied");
        }
    }
}
