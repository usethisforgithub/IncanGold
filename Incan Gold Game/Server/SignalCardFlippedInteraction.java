import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class SignalCardFlippedInteraction implements ClientInteraction {
    private Card card;
    
    public SignalCardFlippedInteraction(Card card) {
        this.card = card;
    }

    @Override
    public Boolean handleInteraction(PrintWriter pw, BufferedReader br) {
        // TODO Auto-generated method stub
        try {
            pw.println("CARD_FLIPPED");
            pw.flush();
            br.readLine();

            pw.println(card);
            pw.flush();
            br.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return true;
    }
    
}
