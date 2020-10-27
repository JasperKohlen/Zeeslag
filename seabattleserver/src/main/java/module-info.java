module seabattleserver {
    requires javax.websocket.client.api;
    requires slf4j.api;
    requires javax.websocket.api;
    requires org.eclipse.jetty.servlet;
    requires org.eclipse.jetty.server;
    requires org.eclipse.jetty.websocket.javax.websocket;
    requires gson; // logging with Logback
}