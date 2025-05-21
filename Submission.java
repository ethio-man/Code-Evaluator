import java.io.*;
import java.nio.file.*;
public class Submission {
    public static String result;
    private static String code;
private String username;
    public Submission(String username,String code) {
        this.code = code;
this.username=username;
    }
    public static String evaluateUserCode(String code, String input, String expectedOutput) {
        String fileName = "UserProgram.java";
        try {
            Files.write(Paths.get(fileName), code.getBytes());
            Process compile = new ProcessBuilder("javac", fileName).start();
            compile.waitFor();
            if (compile.exitValue() != 0) {
                result="Compilation Error:"+compile.getErrorStream();
                return result;
            }
            Process run = new ProcessBuilder("java", "UserProgram").start();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(run.getOutputStream()));
            writer.write(input);
            writer.newLine();
            writer.flush();
            writer.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(run.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line.trim());
            }
            run.waitFor();
            if (output.toString().equals(expectedOutput)) {
                result="Correct!";
            } else {
                result="Wrong!\n" +"Expected: " + expectedOutput+"\n"+"Got:      " + output;
            }
        } catch (Exception e) {
            result="An error occurred while evaluating code:";
        } finally {
            try {
                Files.deleteIfExists(Paths.get("UserProgram.java"));
                Files.deleteIfExists(Paths.get("UserProgram.class"));
            } catch (IOException ignored) {}
            return result;
        }
    }
    private static void printStream(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
}
