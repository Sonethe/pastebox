package com.example.pastebox.service;

import com.example.pastebox.api.request.PasteBoxRequest;
import com.example.pastebox.api.response.PasteBoxResponse;
import com.example.pastebox.api.response.PasteBoxUrlResponse;
import com.example.pastebox.model.PasteBox;
import com.example.pastebox.repository.PasteBoxRepositoryMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class PasteBoxServiceImpl implements PasteBoxService {
    private final PasteBoxRepositoryMap pasteBoxRepositoryMap;
    private AtomicInteger idGenerator = new AtomicInteger(0);

    @Override
    public PasteBoxResponse getByHash(String hash) {
        PasteBox pasteBox = pasteBoxRepositoryMap.getByHash(hash);

        return new PasteBoxResponse(pasteBox.getData(), pasteBox.isPublic());
    }

    @Override
    public List<PasteBoxResponse> getFirstPublicPasteBoxes(int amount) {
        return null;
    }

    @Override
    public PasteBoxUrlResponse create(PasteBoxRequest request) {
        int hash = generateId();

        PasteBox pasteBox = new PasteBox();
        pasteBox.setId(hash);
        pasteBox.setData(request.getData());

        return null;
    }

    private int generateId() {
        return idGenerator.getAndIncrement();
    }
}
