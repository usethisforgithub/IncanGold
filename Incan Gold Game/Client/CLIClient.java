import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class CLIClient {
    public static void main(String[] args) throws IOException{
        int playerId;
        Socket socket = new Socket("127.0.0.1", 43210);
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));


        ArrayList<Player> players = new ArrayList<Player>(); 
        ArrayList<Card> cardsFlipped = new ArrayList<Card>();
        

        System.out.println("Hello from client!");

        boolean gameGoing = true;
        while(gameGoing){
           String op = br.readLine();
           switch(op){
               case "NEW_VOTE":
               System.out.println("Enter your vote, p/l:");
               String voteString = keyboard.readLine();
               Vote vote = Vote.NOT_VOTED;
               boolean voted = false;
               while(!voted){
                if(voteString.equals("p")){
                    vote = Vote.PRESS;
                    voted = true;
                    pw.println(vote);
                    pw.flush();
                }else if(voteString.equals("l")){
                    vote = Vote.LEAVE;
                    voted = true;
                    pw.println(vote);
                    pw.flush();
                }else{
                    System.out.println("Invalid entry. Enter your vote, p/l:");
                }
               }
               break;
            
               case "PLAYER_LEFT_ROUND":
               pw.println("OK");
               pw.flush();
               playerId = Integer.parseInt(br.readLine());
               pw.println("OK");
               pw.flush();

               break;

               case "CARD_FLIPPED":
               pw.println("OK");
               pw.flush();

               Card flippedCard = Card.valueOf(br.readLine());
               System.out.println("Card was flipped: " + flippedCard);
               cardsFlipped.add(flippedCard);
               pw.println("OK");
               pw.flush();
               break;

               case "GAME_STARTED":
               pw.println("OK");
               pw.flush();
               System.out.println("Game has started");
               playerId = Integer.parseInt(br.readLine());
               pw.println("OK");
               pw.flush();

               int playerCount = Integer.parseInt(br.readLine());
               pw.println("OK");
               pw.flush();
               for(int i = 0; i < playerCount; i++){
                   players.add(new Player());
               }
               System.out.println("You are player " + playerId + " of " + players.size());
               System.out.println();
               break;
           }
        }

        socket.close();
    }
}
