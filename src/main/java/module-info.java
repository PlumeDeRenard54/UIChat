module org.example.uichat {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.java_websocket;
    requires com.fasterxml.jackson.databind;

    // Open your package to Jackson so it can access private fields/methods for JSON serialization
    opens org.example.uichat to javafx.fxml, com.fasterxml.jackson.databind;
    exports org.example.uichat;
}