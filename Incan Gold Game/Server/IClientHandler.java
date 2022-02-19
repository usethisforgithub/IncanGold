public interface IClientHandler {
    void signalGameStarted(int playerId, int totalNumPlayers);//comunicate player list in this method
    Vote getVote();
    void signalNewVote();
    void signalCardFlipped(Card card);
    void signalPlayerLeftRound(int playerIndex);
    void signalGameEnded();
}
