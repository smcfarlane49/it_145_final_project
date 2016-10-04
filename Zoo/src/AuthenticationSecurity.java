import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The AuthenticationSecurity class is used
 * to hash the password entered by the user
 * and to get and store the hashed password
 * by calling the readUsersFromFile from the
 * ReadAFile class.
 * 
 * @author Stephen McFarlane
 * @version v1.0.0
 * @since 2016-10-01
 */
public class AuthenticationSecurity {
   private String username;
   private String filePath;
   private String password;
   private ReadAFile readFile;
   private boolean validPassword;

   /**
    * AuthenticationSecurity default constructor
    * is used to set the following private variables
    * to there default values.
    * <ul>
    *    <li>username - empty string
    *    <li>filePath - empty string
    *    <li>password - empty string
    *    <li>readFile - initialize new instance of the ReadAFile class
    *    <li>validPassword - boolean default value of false
    * </ul>
    */
   public AuthenticationSecurity() {
      username = "";
      filePath = "";
      password = "";
      readFile = new ReadAFile();
      validPassword = false;
   }
   
   /**
    * setUsername is the setter used to update
    * the private variable username
    * @param username  This is the username 
    *        entered by the user when prompted.
    */
   public void setUsername (String username) {
      this.username = username;
      return;
   }
   
   /**
    * getUsername returns the username entered
    * by the user.
    * @return Returns the string username
    */
   public String getUsername () {
      return this.username;
   }
   
   /**
    * setPassword is the setter used to update
    * the private variable password
    * @param password  this is the password entered
    *        by the user when prompted.
    */
   public void setPassword (String password) {
      this.password = password;
      return;
   }
   
   /**
    * setFilePath is the setter used to update
    * the private variable filePath
    * 
    * @param filePath  This is the path to the
    *        location on disk where the file
    *        can be found
    */
   public void setFilePath (String filePath) {
      this.filePath = filePath;
      return;
   }

   /**
    * setValidPassword sets the private boolean
    * variable validPassword to true if the MD5 hash
    * values match for the user. It leaves the value
    * as false otherwise.
    * 
    * @throws IOException This is incase the file
    *         does not exist.
    */
   public void setValidPassword() throws IOException {
      validPassword = false;
      String textFileMD5 = "";
      readFile = new ReadAFile();
      String md5Password = getMD5Password(password);
      readFile.setFilePath(filePath);
      readFile.setUsername(username);
      textFileMD5 = readFile.readUsersFromFile();
      
      if (md5Password.equals(textFileMD5)) {
          validPassword = true;
      }
   }
   
   /**
    * getValidPassword returns the private boolean
    * that lets the application know if the username
    * and password are valid.
    * 
    * @return returns true or false based on the login
    *         to the application being valid.
    */
   public boolean getValidPassword () {
      return this.validPassword;
   }
   
   /**
    * getMD5Password is the method that takes
    * the password the user entered and hashes
    * it using the MD5 hash that was provided
    * as part of the final assignment.
    * 
    * @param password  This is the password the 
    *        user entered when prompted.
    * @return This returns a MD5 hash of the password
    *         provided.
    */
   public String getMD5Password(String password) {
      StringBuffer sb = new StringBuffer();
      try {
         MessageDigest md = MessageDigest.getInstance("MD5");
         md.update(password.getBytes());
         byte[] digest = md.digest();
         for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
         }
      }
      catch (NoSuchAlgorithmException e) {
         e.printStackTrace();
      }
      return sb.toString();
   }
}
