import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import java.security.*;
import java.awt.GridLayout;
import java.io.IOException;

public class AuthenticationSystem {

   public static void main(String[] args) throws IOException {
      String username = "";
      String usernameFilePath = "";
      String systemInformation = "";
      int cnt = 0;
      ReadAFile readFile = new ReadAFile();
      
      usernameFilePath = "C:\\Users\\smcfa\\workspace\\AuthenticationSystem\\src\\";

      username = "";
      cnt = 0;
      while (username.equals("")) {
         if (cnt < 3) {
            username = loginPrompt("Username:", usernameFilePath + "credentials.txt", "Password:", readFile);
            cnt++;
         }
         else {
            exitProgram("Two many attempts, program will exit!", "Exit");
            return;
         }
      }
      
      systemInformation = readFile.pullRoleFromCredentials(usernameFilePath, username);
      
      exitProgram(systemInformation, "Log Out");
   }

   public static void exitProgram (String message, String buttonText) {
      JPanel panel = new JPanel(new GridLayout(0,1));
      JLabel label = new JLabel(message.replace("\n", "<BR>"));
      panel.add(label);
      String[] options = new String[]{buttonText};
      JOptionPane.showOptionDialog(null, panel, "", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
      return;
   }

   public static String loginPrompt(String usernameMessage, String usernameFilePath, String passwordMessage, ReadAFile readFile) {
      boolean validPassword = false;
      String username = "";
      JPanel panel = new JPanel(new GridLayout(0,1));
      JLabel usernameLabel = new JLabel(usernameMessage);
      JTextField usernameTxtField = new JTextField(15);
      JLabel passwordLabel = new JLabel(passwordMessage);
      JPasswordField pass = new JPasswordField(15);
      panel.add(usernameLabel);
      panel.add(usernameTxtField);
      panel.add(passwordLabel);
      panel.add(pass);
      String[] options = new String[]{"Login", "Cancel"};
      //int option = 
      JOptionPane.showOptionDialog(null, panel, "Login Screen", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
      
      username = usernameTxtField.getText().trim();
      String myPassword ="";
      char[] password = pass.getPassword();
      for (char chr : password) {
         myPassword += chr;
      }
      
      validPassword = validatePassword(myPassword, usernameFilePath, username, readFile);
      if (validPassword) {
         return username;
      }
      else {
         return "";
      }
   }
   
   public static boolean validatePassword(String myPassword, String usernameFilePath, String username, ReadAFile readFile) {
      String textFileMD5 = "";
      String md5Password = getMD5Password(myPassword);
      textFileMD5 = readFile.readUsersFromFile(usernameFilePath, username);
      
      if (md5Password.equals(textFileMD5)) {
         return true;
      }
      else {
         return false;
      }
   }
   
   public static String getMD5Password(String password) {
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
