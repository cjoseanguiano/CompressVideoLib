package com.example.carlosanguiano.compressvideo.interfaces;

public interface TrimVideoListener {
    void onStartTrim();

    void onFinishTrim(String url);

    void onCancel();
}
