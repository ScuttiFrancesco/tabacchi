package it.tabacchi.excel;

public record ExcelRow(String barcode, Integer quantita) {
    public ExcelRow {
        if (barcode == null || barcode.isBlank()) throw new IllegalArgumentException("Barcode mancante");
        if (quantita < 0) throw new IllegalArgumentException("Quantità negativa");
    }
}
