import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class ReadAFile {

   private String fileMD5Password;
   private String role;
   private String systemInfo = "";
   
   public ReadAFile() {
      fileMD5Password = "";
      role = "";
      systemInfo = "";
   }
   
   public String readUsersFromFile(String filepath, String username) {
      File file = new File(filepath);
      FileInputStream fis = null;
      BufferedInputStream bis = null;
      DataInputStream dis = null;
     
   
      try {
        String curline = "";
        
        fis = new FileInputStream(file);
   
        // Here BufferedInputStream is added for fast reading.
        bis = new BufferedInputStream(fis);
        dis = new DataInputStream(bis);
   
        while (dis.available() != 0) {
        // dis.available() returns 0 if the file does not have more lines.
          curline = dis.readLine();
          String[] line = curline.split("\t");
          if (line[0].toString().equals(username)) {
             fileMD5Password = line[1];
          }
          
        }
   
        // dispose all the resources after using them.
        fis.close();
        bis.close();
        dis.close();
   
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
      return fileMD5Password;
   }
   
   public String pullRoleFromCredentials (String usernameFilePath, String username) throws IOException {
      FileInputStream fileByteStream = null;
      Scanner inFS = null;
      String line1 = "";
      int i = 0;
      
      fileByteStream = new FileInputStream(usernameFilePath + "credentials.txt");
      inFS = new Scanner(fileByteStream);
      
      line1 = inFS.nextLine();
      i = 1;
      int lineLength  = line1.split("\t").length;
      
      while (inFS.hasNextLine() || role.equals("")) {
         String [] user = new String[lineLength];

         user = line1.split("\t");

         if (user[0].equals(username)) {
            role = user[3];
            systemInfo = pullRoleSystemInformation(usernameFilePath + role + ".txt");
            return systemInfo;
         }
         line1 = inFS.nextLine();
         ++i;
      }
      return "";
   }
   
   public String pullRoleSystemInformation (String roleFilePath) throws IOException {
      FileInputStream fileByteStream = null;
      Scanner inFS = null;
      String line1 = "";
      int i = 0;
      
      fileByteStream = new FileInputStream(roleFilePath);
      inFS = new Scanner(fileByteStream);
      
      line1 = inFS.nextLine() + "\n";
      
      while (inFS.hasNextLine()) {
         line1 += inFS.nextLine() + "\n";
      }
      
      systemInfo = "<html><p>" + line1 + "</p></html>";
      return systemInfo;
   }

}
