import java.io.IOException;

public class Zoo {

   /**
    * This is the main entry point to the Zoo application. This 
    * application allows a user to log into a zoo system. It will
    * validate there username and password. It will use a MD5 hash
    * to validate the password against the stored password. If that
    * is successful then the user will get system information prompted
    * to them based on the users role at the zoo. If the attempt to login
    * 3 times unsuccessfully the program will exit with an error.
    * 
    * @param args  these are not used but could be parameters passed to 
    *              the application when it starts.
    * @throws IOException  This is incase there is an error when trying
    *                      to read the files used in this application.
    */
   public static void main(String[] args) throws IOException {
      String username = "";
      String filePath = "";
      String systemInformation = "";
      int cnt = 0;
      ReadAFile readFile = new ReadAFile();
      DisplayDialogBoxes loginPrompt = new DisplayDialogBoxes();
      
      filePath = "C:\\Users\\smcfa\\workspace\\AuthenticationSystem\\src\\";
      loginPrompt.setFilePath(filePath);

      username = "";
      cnt = 0;
      while (username.equals("")) {
         if (cnt < 3) {
            username = loginPrompt.loginPrompt();
            if (username == "exit") {
               return;
            }
            cnt++;
         }
         else {
            loginPrompt.setMessage("Two many attempts, program will exit!");
            loginPrompt.setButtonText("Exit");
            loginPrompt.exitProgram();
            return;
         }
      }
      
      readFile.setFilePath(filePath);
      readFile.setUsername(username);
      systemInformation = readFile.pullRoleFromCredentials();
      
      loginPrompt.setMessage(systemInformation);
      loginPrompt.setButtonText("Log Out");
      loginPrompt.exitProgram();
   }

}
