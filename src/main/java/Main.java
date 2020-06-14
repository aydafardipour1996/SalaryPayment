import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {

write();
    }
private static void write() throws IOException {
    Path path = Paths.get("src/main/resources/question.txt");

    ArrayList<String> question =new ArrayList<String>(10);
    for (int i=0; i<=10;i++){

    question.add(i, String.valueOf(i * 3));

    }


    Iterable<String> iterable = question;
    Files.write(path,iterable );
}
}
