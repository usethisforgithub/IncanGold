public class Player {
    public int tent;
    public int lootBag;
    public IClientHandler _client;
    public boolean stillIn;

    public Player(IClientHandler client) {
        tent = 0;
        lootBag = 0;
        _client = client;
        stillIn = true;
    }
}
