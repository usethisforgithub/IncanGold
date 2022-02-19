import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class SignalGameStartedInteraction implements ClientInteraction {
    private int playerId;
    private int totalNumPlayers;

    public SignalGameStartedInteraction(int playerId, int totalNumPlayers) {
        this.playerId = playerId;
        this.totalNumPlayers = totalNumPlayers;
    }

    @Override
    public Boolean handleInteraction(PrintWriter pw, BufferedReader br) {
        // TODO Auto-generated method stub
        
        try {
            pw.println("GAME_STARTED");
            pw.flush();
            br.readLine();

            pw.println(playerId);
            pw.flush();
            br.readLine();

            pw.println(totalNumPlayers);
            pw.flush();
            br.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return true;
    }
}
