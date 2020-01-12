package io.github.dode5656.easykits.utilities;

public enum Message {

    PREFIX("prefix"),
    NOENOUGHSPACE("messages.no-enough-space"),
    PLAYERONLY("messages.player-only"),
    KITOTHERSNOPERM("messages.kit-others-no-permission"),
    KITINVALID("messages.kit-invalid"),
    PLAYEROFFLINE("messages.player-offline"),
    NOPERMCMD("messages.no-perm-cmd"),
    NOPERMKIT("messages.no-perm-kit"),
    KITSAVEDSUCCESSFULLY("messages.kit-successfully-saved"),
    ;
    private String key;
    Message(String s) {
        this.key = s;
    }

    String getMessage() {
        return key;
    }

}
