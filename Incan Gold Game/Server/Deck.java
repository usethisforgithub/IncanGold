import java.util.ArrayList;
import java.util.Collections;
public class Deck{
    private ArrayList<Card> questDeck;
    public ArrayList<Card> flippedCards;

    public Deck(){
        questDeck = new ArrayList<Card>();
        flippedCards = new ArrayList<Card>();

        questDeck.add(Card.TREASURE1);
        questDeck.add(Card.TREASURE2);
        questDeck.add(Card.TREASURE3);
        questDeck.add(Card.TREASURE4);
        questDeck.add(Card.TREASURE5);
        questDeck.add(Card.TREASURE5);
        questDeck.add(Card.TREASURE7);
        questDeck.add(Card.TREASURE7);
        questDeck.add(Card.TREASURE9);
        questDeck.add(Card.TREASURE11);
        questDeck.add(Card.TREASURE11);
        questDeck.add(Card.TREASURE13);
        questDeck.add(Card.TREASURE14);
        questDeck.add(Card.TREASURE15);
        questDeck.add(Card.TREASURE17);
        questDeck.add(Card.HAZARD1);
        questDeck.add(Card.HAZARD1);
        questDeck.add(Card.HAZARD1);
        questDeck.add(Card.HAZARD2);
        questDeck.add(Card.HAZARD2);
        questDeck.add(Card.HAZARD2);
        questDeck.add(Card.HAZARD3);
        questDeck.add(Card.HAZARD3);
        questDeck.add(Card.HAZARD3);
        questDeck.add(Card.HAZARD4);
        questDeck.add(Card.HAZARD4);
        questDeck.add(Card.HAZARD4);
        questDeck.add(Card.HAZARD5);
        questDeck.add(Card.HAZARD5);
        questDeck.add(Card.HAZARD5);
    }

    public void shuffle(){
        Collections.shuffle(questDeck);
            while(!isValid()){
                Collections.shuffle(questDeck);
            }
    }

    public Card flip(){
        Card flippedCard = questDeck.get(0);
        if(flippedCard != Card.ARTIFACT){
            flippedCards.add(flippedCard);
        }
        questDeck.remove(0);
        return flippedCard;
    }

    public void insert(Card card){
        questDeck.add(card);
    }

    private boolean isValid(){
        ArrayList<Card> peekedCards = new ArrayList<Card>();
        int i = 0;
        while(!peekedCards.contains(questDeck.get(i))){
            if(!Card.isHazard(questDeck.get(i))){
                return true;
            }
            peekedCards.add(questDeck.get(i));
            i++;
        }

        return false;
    }

    public void display(){
        System.out.println("Cards in deck:");
        for(Card card : questDeck){
            System.out.print(card + ", ");
        }
        System.out.println();
        System.out.println("Cards played:");
        for(Card card : flippedCards){
            System.out.print(card + ", ");
        }
        System.out.println();
    }
}