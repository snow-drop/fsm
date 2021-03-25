package com.hicx.fsm.extraction;

public enum FileTypes {

    TEXT("text/plain");

    private final String mimeType;

    FileTypes(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }
}
