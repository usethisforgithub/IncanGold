import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class SocketClientHandler implements IClientHandler, Runnable {
    private Socket socket;
    private Vote vote;

    private Queue<ClientInteraction> interactionQueue;

    public SocketClientHandler(Server _gameServer, Socket _socket) {
        vote = Vote.NOT_VOTED;
        interactionQueue = new LinkedList<ClientInteraction>();
        socket = _socket;
        Thread t = new Thread(this);
        t.start();
    }

    

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            OutputStream os;
            os = socket.getOutputStream();
            InputStream is = socket.getInputStream();
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            Boolean sentinel = true;
            while(sentinel){
                if(interactionQueue.size() == 0){
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }else{
                    ClientInteraction interaction = interactionQueue.remove();
                    sentinel = interaction.handleInteraction(pw, br);
                }
            }
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
    }

    @Override
    public Vote getVote() {
        return vote;
    }

    @Override
    public void signalGameStarted(int playerId, int totalNumPlayers) {
        // TODO Auto-generated method stub
        interactionQueue.add(new SignalGameStartedInteraction(playerId, totalNumPlayers));
    }

    @Override
    public void signalNewVote() {
        // TODO Auto-generated method stub
        vote = Vote.NOT_VOTED;
        interactionQueue.add(new SignalNewVoteInteraction(this));
    }

    @Override
    public void signalCardFlipped(Card card) {
        // TODO Auto-generated method stub
        interactionQueue.add(new SignalCardFlippedInteraction(card));
    }

    @Override
    public void signalPlayerLeftRound(int playerIndex) {
        // TODO Auto-generated method stub
        interactionQueue.add(new SignalPlayerLeftRoundInteraction(playerIndex));
    }


    @Override
    public void signalGameEnded() {
        // TODO Auto-generated method stub
        interactionQueue.add(new SignalGameEndedInteraction());
    }

    public void setvote(Vote vote){
        this.vote = vote;
    }
}
