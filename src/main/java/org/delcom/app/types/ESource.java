package org.delcom.app.types;

public enum ESource {
    // Nilai-nilai baru yang disesuaikan dengan app.rest
    TABUNGAN("Tabungan"),
    GAJI("Gaji"),
    CASH("Cash"),
    FREELANCE("Freelance Project"), // Ditambahkan untuk request PUT
    
    // Nilai lama yang mungkin masih relevan
    INVESTMENT("Investment"),
    LOANS("Loans"),
    SAVINGS("Savings"), // "Savings" tetap dipertahankan untuk fleksibilitas
    OTHERS("Others");

    private final String value;

    ESource(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * Metode ini akan mencari enum berdasarkan nama (misal: "CASH") 
     * atau berdasarkan nilai (misal: "Tabungan"), tidak peduli besar kecil hurufnya.
     */
    public static ESource fromString(String v) {
        if (v == null) {
            return null;
        }
        for (ESource s : ESource.values()) {
            // Membandingkan nama enum (e.g., SAVINGS) atau nilai stringnya (e.g., "Tabungan")
            if (s.name().equalsIgnoreCase(v) || s.value.equalsIgnoreCase(v)) {
                return s;
            }
        }
        return null; // Mengembalikan null jika tidak ada yang cocok
    }
}