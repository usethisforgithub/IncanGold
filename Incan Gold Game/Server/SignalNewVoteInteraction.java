import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class SignalNewVoteInteraction implements ClientInteraction {
    SocketClientHandler handler;

    public SignalNewVoteInteraction(SocketClientHandler handler) {
        this.handler = handler;
    }

    @Override
    public Boolean handleInteraction(PrintWriter pw, BufferedReader br) {
        try {
            pw.println("NEW_VOTE");
            pw.flush();
            Vote newVote = Vote.valueOf(br.readLine());
            //TODO: handle invalid vote
            handler.setvote(newVote);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }
    
}
