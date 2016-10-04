import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * The DisplayDialogBoxes class displays
 * the different dialog boxes used in this
 * application.
 * 
 * @author Stephen McFarlane
 * @version v1.0.0
 * @since 2016-10-01
 */
public class DisplayDialogBoxes {
   private String message;
   private String buttonText;
   private String filePath;
   private AuthenticationSecurity validatePassword;
   
   /**
    * Default constructor for the class that
    * initializes all the private variables
    * to some default value.
    * 
    * <p>These are the private variables and
    * there data types.
    * <ul>
    *    <li>message - empty string
    *    <li>buttonText - empty string
    *    <li>filePath - empty string
    *    <li>validatePassword - new AuthenticationSecurity instance.
    * </ul>
    */
   public DisplayDialogBoxes() {
      message = "";
      buttonText = "";
      filePath = "";
      validatePassword = new AuthenticationSecurity();
   }
   
   /**
    * setFilePath is a setter method that updates
    * the private filed filePath.
    * 
    * @param filePath  This is the path to the file
    *        location on disk.
    */
   public void setFilePath(String filePath) {
      this.filePath = filePath;
      return;
   }
   
   /**
    * getFilePath is the getter that can be called
    * to return the private variable filePath.
    * 
    * @return This returns a string with the filePath.
    */
   public String getFilePath() {
      return this.filePath;
   }
   
   /**
    * setMessage is the setter to set the private
    * message variable that is used to display a
    * message in the pop up box.
    * 
    * @param message  This is the text to show up
    *        in the dialog box pop up.
    */
   public void setMessage(String message) {
      this.message = message;
      return;
   }
   
   /**
    * setButtonText is the setter to set the text
    * of the buttons in the pop up dialog box.
    * 
    * @param buttonText  This is the actual text
    *        for the buttons on the pop up dialog.
    */
   public void setButtonText(String buttonText) {
      this.buttonText = buttonText;
      return;
   }
   
   /**
    * exitProgram is the dialog box that is used to
    * exit the application. This can be called because
    * they logged in to many times or the successfully
    * logged in and got the system information.
    * 
    * <p>This uses the private variables:
    * <ul>
    *    <li>message
    *    <li>buttonText
    * </ul>
    */
   public void exitProgram () {
      JPanel panel = new JPanel(new GridLayout(0,1));
      JLabel label = new JLabel(message.replace("\n", "<BR>"));
      panel.add(label);
      String[] options = new String[]{buttonText};
      JOptionPane.showOptionDialog(null, panel, "", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
      return;
   }

   /**
    * loginPrompt is probably the main method that the program
    * uses. It is the first thing the main method calls when it starts.
    * 
    * <p>This method calls these following methods:
    * <ul>
    *    <li>RequestFocusListener
    *    <li>setValidPassword
    *    <li>getValidPassword
    *    <li>invalidUsernamePassword
    *  </ul>
    *  The private variables used in this method are:
    *  <ul>
    *    <li>fielPath
    *  </ul>
    *  
    * @return Returns three options:
    *       <ul>
    *          <li>username - if it validates the password successfully.
    *          <li>"exit" - if the user cancels the login request.
    *          <li>"" - empty string if it does not fine the username or password.
    *       </ul>
    * @throws IOException - Gives an exception if the file does not exists.
    */
   public String loginPrompt() throws IOException {
      boolean isValidPassword = false;
      String username = "";
      JPanel panel = new JPanel(new GridLayout(0,1));
      JLabel usernameLabel = new JLabel("Username: ");
      JTextField usernameTxtField = new JTextField(15);
      JLabel passwordLabel = new JLabel("Password: ");
      JPasswordField pass = new JPasswordField(15);
      // https://tips4java.wordpress.com/2010/03/14/dialog-focus/
      usernameTxtField.addAncestorListener( new RequestFocusListener() );
      panel.add(usernameLabel);
      panel.add(usernameTxtField);
      panel.add(passwordLabel);
      panel.add(pass);
      String[] options = new String[]{"Login", "Cancel"};
      int option = JOptionPane.showOptionDialog(null, panel, "Login Screen", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
      
      if (option == 1) {
         return "exit";
      }
      
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
         if (invalidUsernamePassword() == 1) {
            return "exit";
         }
         return "";
      }
   }
   
   /**
    * invalidUsernamePassword is shown when the user
    * enters a username and password combination that
    * does not match to a set in the file.
    * 
    * @return This returns the option selected
    *          <ul>
    *             <li>Try Again - equals 0 and is the default focus.
    *             <li>Exit - equals 1 and will allow the user to exit the program.
    *          </ul>
    */
   private int invalidUsernamePassword () {
      JPanel panel = new JPanel(new GridLayout(0,1));
      JLabel label = new JLabel("You entered an invalid username or password");
      panel.add(label);
      String[] options = new String[]{"Try Again", "Exit"};
      int option = JOptionPane.showOptionDialog(null, panel, "", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
      return option;
   }
}
