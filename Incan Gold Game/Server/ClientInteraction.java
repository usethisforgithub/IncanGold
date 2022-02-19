import java.io.BufferedReader;
import java.io.PrintWriter;

public interface ClientInteraction {
    Boolean handleInteraction(PrintWriter pw, BufferedReader br);
}