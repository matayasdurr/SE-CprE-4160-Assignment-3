import java.io.*;
import java.util.*;
import org.apache.poi.openxml4j.opc.PackageRelationship;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ooxml.POIXMLException;
import org.apache.poi.util.POILogger;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTWorksheet;

public class XSSFWorkbook {
    private static final POILogger logger = null;

    // placeholder list of sheets
    private List<XSSFSheet> sheets = new ArrayList<>();

    public XSSFSheet cloneSheet(int sheetNum, String newName) {
        validateSheetIndex(sheetNum);
        XSSFSheet srcSheet = sheets.get(sheetNum);
        if (newName == null) {
            newName = getUniqueSheetName(srcSheet.getSheetName());
        } else {
            validateSheetName(newName);
        }

        XSSFSheet clonedSheet = createSheet(newName);
        copyRelationships(srcSheet, clonedSheet);
        copyExternalRelationships(srcSheet, clonedSheet);
        copySheetContent(srcSheet, clonedSheet);
        finalizeCTWorksheet(clonedSheet);

        return clonedSheet;
    }

    private void copyRelationships(XSSFSheet srcSheet, XSSFSheet clonedSheet) {
        for (RelationPart rp : srcSheet.getRelationParts()) {
            POIXMLDocumentPart r = rp.getDocumentPart();
            if (r instanceof XSSFDrawing) continue;
            addRelation(rp, clonedSheet);
        }
    }

    private void copyExternalRelationships(XSSFSheet srcSheet, XSSFSheet clonedSheet) {
        try {
            for (PackageRelationship pr : srcSheet.getPackagePart().getRelationships()) {
                if (pr.getTargetMode() == TargetMode.EXTERNAL) {
                    clonedSheet.getPackagePart().addExternalRelationship(
                        pr.getTargetURI().toASCIIString(), pr.getRelationshipType(), pr.getId()
                    );
                }
            }
        } catch (InvalidFormatException e) {
            throw new POIXMLException("Failed to clone external relationships", e);
        }
    }

    private void copySheetContent(XSSFSheet srcSheet, XSSFSheet clonedSheet) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            srcSheet.write(out);
            try (ByteArrayInputStream bis = new ByteArrayInputStream(out.toByteArray())) {
                clonedSheet.read(bis);
            }
        } catch (IOException e) {
            throw new POIXMLException("Failed to clone sheet content", e);
        }

        // Handle drawing cloning
        for (RelationPart rp : srcSheet.getRelationParts()) {
            if (rp.getDocumentPart() instanceof XSSFDrawing) {
                XSSFDrawing srcDrawing = (XSSFDrawing) rp.getDocumentPart();
                if (clonedSheet.getCTWorksheet().isSetDrawing()) {
                    clonedSheet.getCTWorksheet().unsetDrawing();
                }
                XSSFDrawing clonedDg = clonedSheet.createDrawingPatriarch();
                clonedDg.getCTDrawing().set(srcDrawing.getCTDrawing());
                for (RelationPart drawRel : srcDrawing.getRelationParts()) {
                    addRelation(drawRel, clonedDg);
                }
                break;
            }
        }
    }

    private void finalizeCTWorksheet(XSSFSheet clonedSheet) {
        CTWorksheet ct = clonedSheet.getCTWorksheet();
        if (ct.isSetLegacyDrawing()) {
            logger.log(POILogger.WARN, "Cloning sheets with comments is not yet supported.");
            ct.unsetLegacyDrawing();
        }
        if (ct.isSetPageSetup()) {
            logger.log(POILogger.WARN, "Cloning sheets with page setup is not yet supported.");
            ct.unsetPageSetup();
        }
        clonedSheet.setSelected(false);
    }

    // --- Placeholder methods below to avoid compile errors ---

    private void validateSheetIndex(int sheetNum) {}
    private String getUniqueSheetName(String base) { return base + "_Copy"; }
    private void validateSheetName(String name) {}
    private XSSFSheet createSheet(String name) { return new XSSFSheet(name); }
    private void addRelation(RelationPart rp, POIXMLDocumentPart target) {}
}
