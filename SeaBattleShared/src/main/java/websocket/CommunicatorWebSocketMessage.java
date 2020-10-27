package websocket;

public class CommunicatorWebSocketMessage {
    private CommunicatorWebSocketMessageOperation operation;
    private CommunicatorWebSocketDTO dto;

    public CommunicatorWebSocketMessage(CommunicatorWebSocketMessageOperation operation) {
        this.operation = operation;
    }

    public CommunicatorWebSocketMessageOperation getOperation() {
        return operation;
    }

    public void setOperation(CommunicatorWebSocketMessageOperation operation) {
        this.operation = operation;
    }

    public CommunicatorWebSocketMessage() {

    }

    public CommunicatorWebSocketMessage(CommunicatorWebSocketMessageOperation operation, CommunicatorWebSocketDTO dto){
        this.operation = operation;
        this.dto = dto;
    }

    public CommunicatorWebSocketDTO getDto() {
        return dto;
    }
}
