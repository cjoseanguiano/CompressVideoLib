package com.example.carlosanguiano.compressvideo.trimvideo.interfaces;

public interface TrimVideoListener {
    void onStartTrim();

    void onFinishTrim(String url);

    void onCancel();
}
