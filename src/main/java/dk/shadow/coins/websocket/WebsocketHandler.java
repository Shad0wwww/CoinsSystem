package dk.shadow.coins.websocket;

import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.URI;
import java.nio.ByteBuffer;

public class WebsocketHandler extends WebSocketClient {


    public WebsocketHandler(URI serverURI) {
        super(serverURI);

    }


    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("Connected to ore");
        JsonObject obj = new JsonObject();
        obj.addProperty("type", "playerdata");
        obj.addProperty("MaxPlayers", Bukkit.getMaxPlayers());
        send(obj.toString());

    }



    @Override
    public void onClose(int code, String reason, boolean b) {
        System.out.println("closed with exit code " + code + " additional info: " + reason);
        final WebsocketHandler localcache = this;
        new Thread(localcache::reconnect).start();
    }

    @Override
    public void onMessage(String message) {

        try {
            JSONObject response;
            String status;
            JSONObject ping_response, data = createJSONObject(message);

            OfflinePlayer player = Bukkit.getOfflinePlayer((String) data.get("player"));
            String uuid = player.getUniqueId().toString();
            float amount = Float.parseFloat(String.valueOf(data.get("amount")));

            Bukkit.broadcastMessage("§8§l[ §a§lPAYSYSTEM §8§l] §fSpilleren " + player.getName() + " §fhar købt " + data.get("amount"));
            //ADD MONEY




        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onMessage(ByteBuffer message) {
        System.out.println("received ByteBuffer");
    }



    @Override
    public void onError(Exception ex) {
        System.err.println("an error occurred:" + ex);
    }


    private static JSONObject createJSONObject(String jsonString) {
        JSONObject jsonObject = new JSONObject();
        JSONParser jsonParser = new JSONParser();
        if (jsonString != null && !jsonString.isEmpty())
            try {
                jsonObject = (JSONObject)jsonParser.parse(jsonString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        return jsonObject;
    }
}
