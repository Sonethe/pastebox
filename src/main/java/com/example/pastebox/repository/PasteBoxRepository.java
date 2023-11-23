package com.example.pastebox.repository;

import com.example.pastebox.model.PasteBox;

import java.util.List;

public interface PasteBoxRepository {
    PasteBox getByHash(String hash);
    List<PasteBox> getListOfPubicAndAlive(int amount);
    void addToMap(PasteBox pasteBox);
}
