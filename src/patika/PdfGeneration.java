package patika;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import javafx.collections.ObservableList;

public class PdfGeneration {

    public void pdfGeneration(String fileName, ObservableList<Gyogyszer> data) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(fileName + ".pdf"));
            document.open();
            //Táblázat
            float[] columnWidths = {2, 4, 2, 10, 10, 3};
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell(new Phrase("Patika"));
            cell.setBackgroundColor(GrayColor.GRAYWHITE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(6);
            table.addCell(cell);
            
            table.getDefaultCell().setBackgroundColor(new GrayColor(0.75f));
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("Sorszám");
            table.addCell("Gyógyszer");
            table.addCell("Mennyiség");
            table.addCell("Használat");
            table.addCell("Ellenjavallat");
            table.addCell("Szavatosság");
            
            table.setHeaderRows(1);
            
            table.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            
            for (int i = 1; i <= data.size(); i++) {
                Gyogyszer actualGyogyszer = data.get(i - 1);
                
                table.addCell(""+i);
                table.addCell(actualGyogyszer.getGyogyszer());
                table.addCell(actualGyogyszer.getMennyiseg());
                table.addCell(actualGyogyszer.getHasznalat());
                table.addCell(actualGyogyszer.getEllenJav());
                table.addCell(actualGyogyszer.getSzavatossag());
            }
            
            document.add(table);
           
 
            //Aláírás
            Chunk signature = new Chunk("\n\n Generálva a Patika alkalmazás segítségével.");
            Paragraph base = new Paragraph(signature);
            document.add(base);

        } catch (Exception e) {
            e.printStackTrace();
        }
        document.close();
    }


}