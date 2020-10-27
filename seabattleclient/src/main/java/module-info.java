module seabattleclient {
    requires slf4j.api;
    requires javafx.graphics;
    requires javafx.controls;
    requires javax.websocket.client.api;
    requires gson;
    requires SeaBattleShared;
    exports seabattlegui;
}