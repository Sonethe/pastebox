package com.example.pastebox.service;

import com.example.pastebox.api.request.PasteBoxRequest;
import com.example.pastebox.api.response.PasteBoxResponse;
import com.example.pastebox.api.response.PasteBoxUrlResponse;

import java.util.List;

public interface PasteBoxService {
    PasteBoxResponse getByHash(String hash);
    List<PasteBoxResponse> getFirstPublicPasteBoxes();
    PasteBoxUrlResponse create(PasteBoxRequest request);
}
