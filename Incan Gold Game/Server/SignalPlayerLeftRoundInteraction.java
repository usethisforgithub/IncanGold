import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class SignalPlayerLeftRoundInteraction implements ClientInteraction {
    private int playerIndex;

    public SignalPlayerLeftRoundInteraction(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    @Override
    public Boolean handleInteraction(PrintWriter pw, BufferedReader br) {
        // TODO Auto-generated method stub
        try {
            pw.println("PLAYER_LEFT_ROUND");
            pw.flush();
            br.readLine();

            pw.println(playerIndex);
            pw.flush();
            br.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }
    
}
