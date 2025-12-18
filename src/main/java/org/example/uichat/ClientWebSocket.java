package org.example.uichat;

import java.net.URI;
import java.nio.ByteBuffer;


import javafx.scene.control.TextArea;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

public class ClientWebSocket extends WebSocketClient {

    private static WebSocketClient client;

    private static TextArea textArea;

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
        textArea.setText("closed with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onMessage(String message) {
        System.out.println(Message.unserialize(message));
        try {
            textArea.setText(textArea.getText() + "\n" + Message.unserialize(message).toString());
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

    public static void setTextArea(TextArea textArea2){textArea = textArea2;}

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
}