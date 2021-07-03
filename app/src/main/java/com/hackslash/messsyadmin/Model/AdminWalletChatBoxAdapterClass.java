package com.hackslash.messsyadmin.Model;

public class AdminWalletChatBoxAdapterClass {

    private int mimageResourceId;
    private String sSendersUserName, sMessage;

    public AdminWalletChatBoxAdapterClass(int imageResourceId , String sendersUserName , String message){
        mimageResourceId =imageResourceId;
        sSendersUserName = sendersUserName;
        sMessage = message;
    }

    public int getImageResourceId() {
        return mimageResourceId;
    }

    public String getsSendersUserName() {
        return sSendersUserName;
    }

    public String getsMessage() {
        return sMessage;
    }

    }
