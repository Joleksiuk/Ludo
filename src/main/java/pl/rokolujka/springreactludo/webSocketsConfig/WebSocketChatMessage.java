package pl.rokolujka.springreactludo.webSocketsConfig;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WebSocketChatMessage {

    private String content;
    private String sender;
    private String receiver;
    private String date;
    private Status status;
}