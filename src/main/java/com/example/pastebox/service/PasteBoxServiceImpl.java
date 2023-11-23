package com.example.pastebox.service;

import com.example.pastebox.api.request.PasteBoxRequest;
import com.example.pastebox.api.request.PublicStatus;
import com.example.pastebox.api.response.PasteBoxResponse;
import com.example.pastebox.api.response.PasteBoxUrlResponse;
import com.example.pastebox.model.PasteBox;
import com.example.pastebox.repository.PasteBoxRepositoryMap;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PasteBoxServiceImpl implements PasteBoxService {
    @Value("${app.host}")
    private String host;
    @Value("${app.publicListSize}")
    private int publicListSize;

    private final PasteBoxRepositoryMap pasteBoxRepositoryMap;
    private AtomicInteger idGenerator = new AtomicInteger(0);

    @Override
    public PasteBoxResponse getByHash(String hash) {
        PasteBox pasteBox = pasteBoxRepositoryMap.getByHash(hash);

        return new PasteBoxResponse(pasteBox.getData(), pasteBox.isPublic());
    }

    @Override
    public List<PasteBoxResponse> getFirstPublicPasteBoxes() {
        List<PasteBox> pasteBoxList = pasteBoxRepositoryMap.getListOfPubicAndAlive(publicListSize);

        return pasteBoxList.stream().map(pasteBox ->
            new PasteBoxResponse(pasteBox.getData(), pasteBox.isPublic()))
                .collect(Collectors.toList());
    }

    @Override
    public PasteBoxUrlResponse create(PasteBoxRequest request) {
        int hash = generateId();

        PasteBox pasteBox = new PasteBox();
        pasteBox.setId(hash);
        pasteBox.setData(request.getData());
        pasteBox.setHash(Integer.toHexString(hash));
        pasteBox.setPublic(request.getPublicStatus() == PublicStatus.PUBLIC);
        pasteBox.setLifetime(LocalDateTime.now().plusSeconds(request.getExpirationTimeSeconds()));

        pasteBoxRepositoryMap.addToMap(pasteBox);

        return new PasteBoxUrlResponse(host + "/" + pasteBox.getHash());
    }

    private int generateId() {
        return idGenerator.getAndIncrement();
    }
}
