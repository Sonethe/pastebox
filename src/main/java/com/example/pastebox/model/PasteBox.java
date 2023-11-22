package com.example.pastebox.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PasteBox {
    private int id;
    private String data;
    private String hash;
    private LocalDateTime lifetime;
    private boolean isPublic;
}
