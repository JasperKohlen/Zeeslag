module seabattleclient {
    requires slf4j.api;
    requires javafx.graphics;
    requires javafx.controls;
    requires javax.websocket.client.api;
    requires java.logging;
    requires java.sql;
    requires gson;
    requires SeaBattleShared;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpclient;

    exports websocketclient;
    exports seabattlegui;
}