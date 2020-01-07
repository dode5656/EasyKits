package io.github.dode5656.easykits.utilities;

public enum Message {

    ERROR("messages.error"),
    PREFIX("prefix")
    ;
    private String key;
    Message(String s) {
        this.key = s;
    }

    String getMessage() {
        return key;
    }

}
