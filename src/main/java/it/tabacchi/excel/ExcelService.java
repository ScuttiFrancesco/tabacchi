package it.tabacchi.excel;

import it.tabacchi.prodotti.prodottomagazzino.IProdottoMagazzinoService;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExcelService {

    private final IProdottoMagazzinoService pmservice;

    public ExcelService(IProdottoMagazzinoService pmservice) {
        this.pmservice = pmservice;
    }

    public byte[] generaExcelOrdine() throws IOException {
        // 1. Recupero i dati in base ai criteri interni (es. scorta < minima)
        List<OrdineProdottoDto> prodottiDaOrdinare = pmservice.cacolaProdottiDaOrdinare();

        try (SXSSFWorkbook workbook = new SXSSFWorkbook(100);
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            SXSSFSheet sheet = workbook.createSheet("Ordine Fornitore");

            // Stile per l'intestazione
            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Creazione Header
            Row headerRow = sheet.createRow(0);
            String[] colonne = {"Codice Barcode", "Nome Prodotto", "Quantità da Ordinare"};
            for (int i = 0; i < colonne.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(colonne[i]);
                cell.setCellStyle(headerStyle);
            }

            // Popolamento Dati
            int rowIdx = 1;
            for (OrdineProdottoDto ordine : prodottiDaOrdinare) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(ordine.aams());
                row.createCell(1).setCellValue(ordine.descrizione());
                row.createCell(2).setCellValue(ordine.quantita());
                // Formattazione per valuta (opzionale)
              //  row.createCell(3).setCellValue(ordine.prezzoAcquisto().doubleValue());
            }

            // Auto-size delle colonne (SXSSF richiede un piccolo trick per questo)
            sheet.trackAllColumnsForAutoSizing();
            for (int i = 0; i < colonne.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            workbook.dispose(); // Pulisce i file temporanei su disco
            return out.toByteArray();
        }
    }


    /**
     * Legge un file Excel proteggendo l'applicazione da file corrotti o malevoli.
     */
    public List<Map<String, String>> readExcel(InputStream is) throws IOException {
        // Protezione contro Zip Bomb: limitiamo l'espansione dei file compressi
        ZipSecureFile.setMinInflateRatio(0.01);

        List<Map<String, String>> result = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Salta header

                Map<String, String> rowData = new HashMap<>();
                // Esempio lettura dinamica con Java 21 Switch Pattern Matching
                for (Cell cell : row) {
                    String value = switch (cell.getCellType()) {
                        case STRING -> cell.getStringCellValue();
                        case NUMERIC -> formatter.formatCellValue(cell);
                        case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
                        case FORMULA -> cell.getCellFormula();
                        default -> "";
                    };
                    rowData.put("col_" + cell.getColumnIndex(), value);
                }
                result.add(rowData);
            }
        }
        return result;
    }
}
