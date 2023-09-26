package api;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Response {
    private int code;
    private List<MessageIds> messageIds;

    public Response(int code, List<MessageIds> messageIds) {
        this.code = code;
        this.messageIds = messageIds;
    }

    public Response(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<MessageIds> getMessageIds() {
        return messageIds;
    }
}
