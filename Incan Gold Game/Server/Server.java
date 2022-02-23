import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server{

    public Server() {
        artifactsSeen = 0;
        players = new ArrayList<Player>();
        pot = 0;
        deck = new Deck();
        artifactStash = 0;
    }

    public final int roundCount = 5;
    public boolean GAME_STARTED;
    public int artifactStash;
    public int artifactsSeen;
    public ArrayList<Player> players;
    public int pot;
    public int playersRemaining;
    public int round;
    public boolean roundGoing;
    public Deck deck;

    public void playGame() throws InterruptedException, IOException{
        //TODO: implement connection listener
        //TODO: implement game start feature
        ServerSocket serverSocket = new ServerSocket(43210);
        Socket socket = serverSocket.accept();
        //Socket socket2 = serverSocket.accept();
        

        //connect all clients
        players.add(new Player(new SocketClientHandler(this, socket)));
        //players.add(new Player(new SocketClientHandler(this, socket2)));
        serverSocket.close();


        //wait for game to start

        //signal to players that game has started
        for(int i = 0; i < players.size(); i++){
            players.get(i)._client.signalGameStarted(i, players.size());
        }

        //round logic
        for(round = 0; round < roundCount; round++){
            //set starting lootbags to zero
            for(Player player : players){
                player.lootBag = 0;
                player.stillIn = true;
            }
            //set all players to still remaining
            playersRemaining = players.size();

            //put new artifact in the deck for the round
            deck.insert(Card.ARTIFACT);
            deck.shuffle();

            roundGoing = true;
            
            //flip card until non-hazard is flipped
            Card card = deck.flip();
            broadcastCardFlipped(card);
            while(Card.isHazard(card)){
                card = deck.flip();
                broadcastCardFlipped(card);
            }

            //handles treasure distribution
            //does not handle hazard ending here because won't happen on first flip
            resolveCard(card);
            
            while(roundGoing){
                printScores();

                //signals new vote to each olayer still in the game
                callVote();
                
                resolveVote();

                //if everyone has left, end the round, set everyone to stillIn,
                // and reset the players remaining
                //TODO: check that I change player remaining
                if(playersRemaining == 0){
                    endRound();
                }else{
                    card = deck.flip();
                    broadcastCardFlipped(card);

                    //if the flipped card is a duplicate hazard, end the round
                    if(Card.isHazard(card) && (deck.flippedCards.indexOf(card) != deck.flippedCards.lastIndexOf(card))){
                        endRound(card);
                    }else{
                        resolveCard(card);
                    }
                }
            }
        }

        //TODO: handle end of game
        System.out.println("Game has ended!");
        printScores();
    }

    //signals new vote to each handler
    private void callVote(){
        System.out.println("Calling the vote");
        for(Player player : players){
            if(player.stillIn){
                player._client.signalNewVote();
            }
        }
    }

    private void endRound(){
        System.out.println("Ending the round by leave");
        for(Card card : deck.flippedCards){
            deck.insert(card);
        }
        deck.flippedCards.clear();
        for(int i = 0; i < artifactStash; i++){
            deck.insert(Card.ARTIFACT);
        }
        roundGoing = false;
    }

    private void endRound(Card card){
        System.out.println("Ending the round by hazard");
        for(Card flippedCard : deck.flippedCards){
            if(flippedCard != card){
                deck.insert(flippedCard);
            }
        }
        deck.flippedCards.clear();
        for(int i = 0; i < artifactStash; i++){
            deck.insert(Card.ARTIFACT);
        }
        roundGoing = false;
    }

    //waits for vote
    // distributes the treasure and artifacts according to who voted to leave
    private void resolveVote() throws InterruptedException{
        int reward = 0;
        int playersThatLeft = 0;

        //wait for vote
        while(!readyForVote()){
            Thread.sleep(1000);
        }

        //count the players that left
        for(int i = 0; i < players.size(); i++){
            Player player = players.get(i);
            if(player.stillIn && player._client.getVote() == Vote.LEAVE){
                playersThatLeft++;
                broadcastPlayerLeftRound(i);
            }
        }

        if(playersThatLeft > 0){
            if(playersThatLeft == 1){
                reward += calculateArtifactReward();
            }

            reward += pot/playersThatLeft;
            pot = pot % playersThatLeft;
            
            for(Player player : players){
                if(player.stillIn && player._client.getVote() == Vote.LEAVE){
                    player.stillIn = false;
                    playersRemaining--;
                    player.tent += reward;
                    player.tent += player.lootBag;
                    player.lootBag = 0;
                }
            }
        }
        
    }

    //removes artifacts from the stash and returns the reward
    private int calculateArtifactReward(){
        int reward = 0;
        artifactsSeen++;
        for(int i = 0; i < artifactStash; i++){
            if(artifactsSeen <= 3){
                reward += 5;
            }else{
                reward += 10;
            }
        }
        artifactStash = 0;
        return reward;
    }

    private void printScores() throws IOException{
        //Runtime.getRuntime().exec("clear");
        System.out.println();
        System.out.println("Pot: " + pot);
        System.out.println("Scores:");
        for(int i = 0; i < players.size(); i++){
            Player player = players.get(i);
            System.out.println("Player " + (i+1) + ": ");
            System.out.println("lootBag: " + player.lootBag);
            System.out.println("tent: " + player.tent);
            System.out.println();
        }
        System.out.println();
        System.out.println();
        deck.display();
    }

    //figures out the total treasure to distribute, distributes it, or adds artifact to stash
    private void resolveCard(Card card){
        int treasure = 0;
        switch(card){
            case TREASURE1: 
            treasure = 1;
            break;
            case TREASURE2: 
            treasure = 2;
            break;
            case TREASURE3: 
            treasure = 3;
            break;
            case TREASURE4: 
            treasure = 4;
            break;
            case TREASURE5: 
            treasure = 5;
            break;
            case TREASURE7: 
            treasure = 7;
            break;
            case TREASURE9: 
            treasure = 9;
            break;
            case TREASURE11: 
            treasure = 11;
            break;
            case TREASURE13: 
            treasure = 13;
            break;
            case TREASURE14: 
            treasure = 14;
            break;
            case TREASURE15: 
            treasure = 15;
            break;
            case TREASURE17: 
            treasure = 17;
            break;
            case ARTIFACT: 
            artifactStash++;
            break;
            default: 
            break;
        }
        distributeTreasure(treasure);
    }

    //distributes reward amongst the players and the pot
    private void distributeTreasure(int treasure){
        if(treasure > 0){
            int reward = treasure / playersRemaining;
            int potReward = treasure % playersRemaining;

            for(Player player : players){
                if(player.stillIn){
                    player.lootBag += reward;
                }
            }
            pot += potReward;
        }
        System.out.println("Distributing " + treasure + " treasure");
    }

    //returns true if all players still in have voted
    private boolean readyForVote(){
        for(Player player : players){
            if(player.stillIn && player._client.getVote() == Vote.NOT_VOTED){
                return false;
            }
        }
        return true;
    }

    private void broadcastCardFlipped(Card card){
        for(Player player : players){
            player._client.signalCardFlipped(card);
        }
    }

    private void broadcastPlayerLeftRound(int playerIndex){
        for(Player player : players){
            player._client.signalPlayerLeftRound(playerIndex);
        }
    }
}



