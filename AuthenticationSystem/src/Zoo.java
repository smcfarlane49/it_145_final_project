import java.io.IOException;

public class Zoo {

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
