package com.hackslash.messsyadmin.Model;


public class MessageClass {

    private String messageId , message , senderId , name , profileImage , imageUrl , messageTime , messageDate; // imageUrl is the image send by the user during chats

    public MessageClass(){}

    public MessageClass(String message, String senderId, String messageTime , String messageDate) {
        this.message = message;
        this.senderId = senderId;
        this.messageTime = messageTime;
        this.messageDate = messageDate;
    }

    public MessageClass(String message, String senderId, String messageTime , String messageDate , String imageUrl) {
        this.message = message;
        this.senderId = senderId;
        this.messageTime = messageTime;
        this.messageDate = messageDate;
        this.imageUrl = imageUrl;
    }

    public MessageClass( String message, String senderId, String name, String profileImage, String messageTime , String messageDate) {
        this.message = message;
        this.senderId = senderId;
        this.name = name;
        this.messageTime = messageTime;
        this.messageDate = messageDate;
        this.profileImage = profileImage;
    }

    public MessageClass( String message, String senderId, String name, String profileImage,String messageTime , String messageDate , String imageUrl) {
        this.message = message;
        this.senderId = senderId;
        this.name = name;
        this.profileImage = profileImage;
        this.messageTime = messageTime;
        this.messageDate = messageDate;
        this.imageUrl = imageUrl;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }
}
