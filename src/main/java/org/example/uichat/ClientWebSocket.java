package org.example.uichat;

import java.net.URI;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;


import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

public class ClientWebSocket extends WebSocketClient {

    private static WebSocketClient client;

    private static VBox messages;
    public static List<String> publicRooms = new ArrayList<>();

    public ClientWebSocket(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    public ClientWebSocket(URI serverURI) {
        super(serverURI);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connexion établie !");
        System.out.println("Headers du handshake: " + handshakedata.iterateHttpFields());
    }


    @Override
    public void onClose(int code, String reason, boolean remote) {
        messages.getChildren().clear();
        messages.getChildren().add(createMessageView("closed with exit code " + code + " additional info: " + reason));
    }

    public Label createMessageView(Message m){
        Label label = new Label(m.toString());
        label.getStyleClass().add("messages");

        return label;
    }

    public Label createMessageView(String m){
        return createMessageView(new Message("System",m,"All"));
    }

    @Override
    public synchronized void onMessage(String message) {
        System.out.println(Message.unserialize(message));
        try {
            Message data =  Message.unserialize(message);

            //Reception des données des rooms
            if (data.user.equals("RoomInfo")){
                System.out.println("Room info received");
                publicRooms.add(data.message);
                MainApp.helloController.updateRooms();
                return;
            }

            //Reception des Messages
            Platform.runLater(()->messages.getChildren().add(createMessageView(data)));

        } catch (Exception e) {
            System.out.println("Error while computing data = " + e.getMessage());
            System.out.println(message);
        }
    }

    @Override
    public void onMessage(ByteBuffer message) {
        System.out.println("received ByteBuffer");
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    public static void setMessages(VBox vBox){messages = vBox;}

    public static WebSocketClient getLink(){
        if (client == null) {
            try {
                WebSocketClient clientLoc = new ClientWebSocket(new URI(/*"wss://khaos-experiences.fr:8887"*/ "ws://localhost:8080"), new Draft_6455());

                /*SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    }

                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }}, new SecureRandom());
                clientLoc.setSocketFactory(sslContext.getSocketFactory());*/


                clientLoc.connectBlocking();
                client = clientLoc;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e.getCause());
            }
        }
        return client;
    }

    public static void send(Message message){
        try {
            WebSocketClient client = ClientWebSocket.getLink();
            String toSend = message.serialize();
            System.out.println(toSend);
            client.send(toSend);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void logInRoom(String room,String userName){
        send(new Message(userName,"Joins",room));
        //Reset de l'affichage
        messages.getChildren().clear();
    }
}