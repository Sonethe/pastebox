package com.example.pastebox.repository;

import com.example.pastebox.exception.NotFoundEntityException;
import com.example.pastebox.model.PasteBox;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class PasteBoxRepositoryMap implements PasteBoxRepository {
    private final Map<String, PasteBox> vault = new ConcurrentHashMap<>();

    @Override
    public PasteBox getByHash(String hash) {
        PasteBox pasteBox = vault.get(hash);

        if(pasteBox == null) {
            throw new NotFoundEntityException("Pastebox not found with hash = " + hash);
        }

        return pasteBox;
    }

    @Override
    public List<PasteBox> getListOfPubicAndAlive(int amount) {
        LocalDateTime nowTimestamp = LocalDateTime.now();

        return vault.values().stream()
                .filter(PasteBox::isPublic)
                .filter(pasteBox -> pasteBox.getLifetime().isAfter(nowTimestamp))
                .sorted(Comparator.comparing(PasteBox::getId).reversed())
                .limit(amount)
                .collect(Collectors.toList());
    }

    @Override
    public void add(PasteBox pasteBox) {
        vault.put(pasteBox.getHash(), pasteBox);
    }
}
