package Models;

public class Player {
    int playerNr;
    int sessionid;
    String name;
    String password;

    public Player(int playerNr, String name, String password) {
        this.playerNr = playerNr;
        this.name = name;
        this.password = password;
    }

    public int getPlayerNr() {
        return playerNr;
    }

    public void setPlayerNr(int playerNr) {
        this.playerNr = playerNr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSessionid() {
        return sessionid;
    }

    public void setSessionid(int sessionid) {
        this.sessionid = sessionid;
    }
}
