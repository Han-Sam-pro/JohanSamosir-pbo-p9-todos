package org.delcom.app.services;

import org.delcom.app.entities.CashFlow;
import org.delcom.app.repositories.ICashFlowRepository;
import org.delcom.app.types.ESource;
import org.delcom.app.types.EType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CashFlowService implements ICashFlowService {

    private final ICashFlowRepository cashFlowRepository;

    public CashFlowService(ICashFlowRepository cashFlowRepository) {
        this.cashFlowRepository = cashFlowRepository;
    }

    /**
     * Mengambil semua data. Jika ada parameter pencarian, 
     * data akan difilter di level database.
     */
    @Override
    public ArrayList<CashFlow> getAll(String search) {
        if (search == null || search.trim().isEmpty()) {
            return new ArrayList<>(cashFlowRepository.findAll());
        }
        // Menggunakan metode custom dari repository untuk pencarian yang efisien
        return new ArrayList<>(cashFlowRepository.findByLabelContainingIgnoreCaseOrDescriptionContainingIgnoreCase(search, search));
    }

    /**
     * Mengambil satu data berdasarkan ID.
     */
    @Override
    public Optional<CashFlow> getById(String id) {
        return cashFlowRepository.findById(id);
    }

    /**
     * Menambahkan data baru. ID akan digenerate di sini.
     */
    @Override
    public CashFlow addCashFlow(String type, String source, String label, String description, long amount) {
        EType t = EType.fromString(type);
        ESource s = ESource.fromString(source);
        if (t == null || s == null) {
            return null; // Validasi gagal
        }
        
        // Buat ID baru di sini sebelum membuat entity
        String newId = UUID.randomUUID().toString();
        
        CashFlow cf = new CashFlow(newId, t, s, label, description, amount);
        
        // Menggunakan metode .save() dari JpaRepository untuk menyimpan ke database
        return cashFlowRepository.save(cf);
    }

    /**
     * Memperbarui data yang sudah ada berdasarkan ID.
     */
    @Override
    public boolean updateCashFlow(String id, String type, String source, String label, String description, long amount) {
        // 1. Validasi input type dan source
        EType t = EType.fromString(type);
        ESource s = ESource.fromString(source);
        if (t == null || s == null) {
            return false;
        }

        // 2. Cari data yang ada di database
        Optional<CashFlow> optionalCashFlow = cashFlowRepository.findById(id);

        // 3. Jika data tidak ditemukan, kembalikan false
        if (optionalCashFlow.isEmpty()) {
            return false;
        }

        // 4. Jika data ditemukan, perbarui field-nya
        CashFlow existingCashFlow = optionalCashFlow.get();
        existingCashFlow.setType(t);
        existingCashFlow.setSource(s);
        existingCashFlow.setLabel(label);
        existingCashFlow.setDescription(description);
        existingCashFlow.setAmount(amount);
        existingCashFlow.setUpdatedAt(Instant.now()); // Jangan lupa perbarui timestamp

        // 5. Simpan kembali perubahan ke database.
        // JpaRepository cukup pintar untuk melakukan UPDATE jika ID sudah ada.
        cashFlowRepository.save(existingCashFlow);
        
        return true;
    }
    
    /**
     * Menghapus data berdasarkan ID.
     */
    @Override
    public boolean removeCashFlow(String id) {
        // Cek dulu apakah datanya ada untuk menghindari error
        if (cashFlowRepository.existsById(id)) {
            cashFlowRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Mengambil semua label unik dari database.
     */
    @Override
    public Set<String> getAllLabels() {
        // Logika ini tetap sama, hanya sumber datanya yang berbeda (langsung dari DB)
        return cashFlowRepository.findAll().stream()
                .map(CashFlow::getLabel)
                .collect(Collectors.toSet());
    }
}