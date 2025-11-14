package org.delcom.app.entities;

// 1. IMPORT YANG HILANG SUDAH DITAMBAHKAN
import org.delcom.app.types.ESource;
import org.delcom.app.types.EType;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "cash_flows")
public class CashFlow {

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private EType type;

    @Enumerated(EnumType.STRING)
    private ESource source;

    private String label;
    private long amount;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;

    // Konstruktor kosong yang dibutuhkan oleh JPA
    public CashFlow() {
    }

    // Konstruktor untuk membuat objek baru
    public CashFlow(String id, EType type, ESource source, String label, String description, long amount) {
        this.id = id;
        this.type = type;
        this.source = source;
        this.label = label;
        this.description = description;
        this.amount = amount;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    // 2. SEMUA GETTER DAN SETTER YANG HILANG SUDAH DITAMBAHKAN DI SINI
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EType getType() {
        return type;
    }

    public void setType(EType type) {
        this.type = type;
    }

    public ESource getSource() {
        return source;
    }

    public void setSource(ESource source) {
        this.source = source;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}