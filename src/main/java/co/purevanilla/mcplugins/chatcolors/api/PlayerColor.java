package co.purevanilla.mcplugins.chatcolors.api;

import java.util.UUID;

public class PlayerColor {

    public Color color;
    public UUID player;

    public PlayerColor(Color color, UUID player){
        this.color=color;
        this.player=player;
    }

}
