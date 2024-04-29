
import java.io.*;
import java.time.LocalDate;

public class File {
    public static void writefile(String first,String last,LocalDate birthDate, String gender, String username, String password) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\Java\\Advanced CP\\src\\user_info.txt",true));
        writer.write(first + "," + last + "," + birthDate + "," + gender + "," + username + "," + password);
        writer.newLine();
        writer.flush();
    }
    public static void readFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("D:\\Java\\Advanced CP\\src\\user_info.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 6) {
                String first = parts[0];
                String last = parts[1];
                LocalDate birthDate = LocalDate.parse(parts[2]);
                String gender = parts[3];
                String username = parts[4];
                String password = parts[5];
                Login_SignUp.userInfo.put(username, password);
            }
        }
    }
}
