import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
 * validatePassword.setUsername(username);
      validatePassword.setPassword(myPassword);
      validatePassword.setFilePath(filePath);
      validatePassword.setValidPassword();
      isValidPassword = validatePassword.getValidPassword();
 */
public class AuthenticationSecurity {
   private String username;
   private String filePath;
   private String password;
   private ReadAFile readFile;
   private boolean validPassword;

   public AuthenticationSecurity() {
      username = "";
      filePath = "";
      password = "";
      readFile = new ReadAFile();
      validPassword = false;
   }
   
   public void setUsername (String username) {
      this.username = username;
      return;
   }
   
   public String getUsername () {
      return this.username;
   }
   
   public void setPassword (String password) {
      this.password = password;
      return;
   }
   
   public void setFilePath (String filePath) {
      this.filePath = filePath;
      return;
   }

   public void setValidPassword() {
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
   
   public boolean getValidPassword () {
      return this.validPassword;
   }
   
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
