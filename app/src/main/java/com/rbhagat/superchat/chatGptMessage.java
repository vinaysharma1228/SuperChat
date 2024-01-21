package com.rbhagat.superchat;

public class chatGptMessage {

    public  static String SEND_BY_ME="Me";
    public  static String SEND_BY_BOT="Bot";


    String message;
    String sendBy;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSendBy() {
        return sendBy;
    }

    public void setSendBy(String sendBy) {
        this.sendBy = sendBy;
    }

    public chatGptMessage(String message, String sendBy) {
        this.message = message;
        this.sendBy = sendBy;
    }

}
