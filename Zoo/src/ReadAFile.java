import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * The ReadAFile class takes a file path
 * and will read the file and return the
 * requested information.
 * 
 * @author Stephen McFarlane
 * @version v1.0.0
 * @since 2016-10-01
 */

public class ReadAFile {

   private String fileMD5Password;
   private String role;
   private String systemInfo;
   private String filePath;
   private String username;
   
   /**
    * ReadAFile default constructor sets the 
    * private variables to the default state.
    * 
    * <p>Sets the private variables to empty strings.
    * <ul>
    *    <li>fileMD5Password - This is the variable
    *        that will hold the value from the file.
    *    <li>role - This the user role from the
    *        credentials text file.
    *    <li>systemInfo - This is the information to return for the user
    *    <li>filePath - This is the path to the file that will be read.
    *    <li>username - This is the user name to validate the password for.
    * </ul>
    */
   public ReadAFile() {
      fileMD5Password = "";
      role = "";
      systemInfo = "";
      filePath = "";
      username = "";
   }
   
   /**
    * setFilePath sets the private variable for use in the class.
    * 
    * @param filePath  This is the path to the file that will be read.
    */
   public void setFilePath(String filePath) {
      this.filePath = filePath;
      return;
   }
   
   /**
    * setUsername sets the private variable for use in the class.
    * 
    * @param username  This is the user name to validate the password for.
    */
   public void setUsername(String username) {
      this.username = username;
      return;
   }
   
   /**
    * readUserFromFile uses the user name and filePath to read
    * the MD5Password out of the text file to check against
    * the password the user entered.
    * 
    * @return  The MD5 password hash from the file.
    * @throws IOException - Gives an exception if the file does not exists.
    */
   public String readUsersFromFile() throws IOException {
      FileInputStream fileByteStream = null;
      Scanner inFS = null;
      String line1 = "";
      
      fileByteStream = new FileInputStream(filePath + "credentials.txt");
      inFS = new Scanner(fileByteStream);
      
      line1 = inFS.nextLine();
      String[] line = line1.split("\t");
      if (line[0].toString().equals(username)) {
         fileMD5Password = line[1];
      }
      else {
         while (inFS.hasNextLine()) {
            line = inFS.nextLine().split("\t");
            if (line[0].toString().equals(username)) {
               fileMD5Password = line[1];
            }
         }
      }
      inFS.close();
      return fileMD5Password;
   }
   
   /**
    * pullRoleFromCredentials is used to return the system
    * information from the role file. This is only called
    * once a user and password have been validated. Based
    * on the role the user is assigned it will pull the
    * correct file for the system information.
    * 
    * <p> uses the user name to find the role and then
    * the role name to pull the correct file. This then calls
    * pullRoleSystemInformation passing it the path to 
    * the role file.</p>
    * 
    * @return  returns either the system information or
    *          and empty string if the user name or role
    *          do not match to a file.
    * @throws IOException  in case the file does not exist
    */
   public String pullRoleFromCredentials () throws IOException {
      FileInputStream fileByteStream = null;
      Scanner inFS = null;
      String line1 = "";
      
      fileByteStream = new FileInputStream(filePath + "credentials.txt");
      inFS = new Scanner(fileByteStream);
      
      line1 = inFS.nextLine();
      int lineLength  = line1.split("\t").length;
      
      while (inFS.hasNextLine() || role.equals("")) {
         String [] user = new String[lineLength];

         user = line1.split("\t");

         if (user[0].equals(username)) {
            role = user[3];
            systemInfo = pullRoleSystemInformation(filePath + role + ".txt");
            inFS.close();
            return systemInfo;
         }
         line1 = inFS.nextLine();
      }
      
      inFS.close();
      return "";
   }
   
   /**
    * pullRoleSystemInformation actually reads the file and
    * returns the system information for the role to the 
    * pullRoleFromCredentials method which calls this one.
    * 
    * @param roleFilePath  The path to the role file based
    *        on the the role name.
    * @return  Returns the context of the file in a string.
    * @throws IOException  in case the file does not exist
    */
   private String pullRoleSystemInformation (String roleFilePath) throws IOException {
      FileInputStream fileByteStream = null;
      Scanner inFS = null;
      String line1 = "";
      
      fileByteStream = new FileInputStream(roleFilePath);
      inFS = new Scanner(fileByteStream);
      
      line1 = inFS.nextLine() + "\n";
      
      while (inFS.hasNextLine()) {
         line1 += inFS.nextLine() + "\n";
      }
      inFS.close();
      systemInfo = "<html><p>" + line1 + "</p></html>";
      return systemInfo;
   }
}
