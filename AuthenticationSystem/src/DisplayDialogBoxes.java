import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class DisplayDialogBoxes {
   private String message;
   private String buttonText;
   private String filePath;
   private AuthenticationSecurity validatePassword;
   
   public DisplayDialogBoxes() {
      message = "";
      buttonText = "";
      filePath = "";
      validatePassword = new AuthenticationSecurity();
   }
   
   public void setFilePath(String filePath) {
      this.filePath = filePath;
      return;
   }
   
   public String getFilePath() {
      return this.filePath;
   }
   
   public void setMessage(String message) {
      this.message = message;
      return;
   }
   
   public void setButtonText(String buttonText) {
      this.buttonText = buttonText;
      return;
   }
   
   public void exitProgram () {
      JPanel panel = new JPanel(new GridLayout(0,1));
      JLabel label = new JLabel(message.replace("\n", "<BR>"));
      panel.add(label);
      String[] options = new String[]{buttonText};
      JOptionPane.showOptionDialog(null, panel, "", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
      return;
   }

   public String loginPrompt() {
      boolean isValidPassword = false;
      String username = "";
      JPanel panel = new JPanel(new GridLayout(0,1));
      JLabel usernameLabel = new JLabel("Username: ");
      JTextField usernameTxtField = new JTextField(15);
      JLabel passwordLabel = new JLabel("Password: ");
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
      

      validatePassword = new AuthenticationSecurity();
      
      validatePassword.setUsername(username);
      validatePassword.setPassword(myPassword);
      validatePassword.setFilePath(filePath);
      validatePassword.setValidPassword();
      isValidPassword = validatePassword.getValidPassword();
      if (isValidPassword) {
         return username;
      }
      else {
         return "";
      }
   }

}
