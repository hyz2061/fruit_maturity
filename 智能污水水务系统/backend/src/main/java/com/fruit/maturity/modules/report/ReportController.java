package com.fruit.maturity.modules.report;

import com.fruit.maturity.common.ApiResult;
import com.fruit.maturity.modules.env.repo.EnvMetricRepository;
import com.fruit.maturity.modules.event.repo.EventRepository;
import com.fruit.maturity.modules.report.repo.ReportRepository;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {
  private final ReportRepository reportRepository;
  private final EventRepository eventRepository;
  private final EnvMetricRepository envRepository;

  public ReportController(ReportRepository reportRepository,
      EventRepository eventRepository,
      EnvMetricRepository envRepository) {
    this.reportRepository = reportRepository;
    this.eventRepository = eventRepository;
    this.envRepository = envRepository;
  }

  @GetMapping("/reports")
  public ApiResult<?> list() {
    return ApiResult.ok(reportRepository.findAll());
  }

  @PostMapping("/reports/export")
  public ApiResult<String> export() {
    return ApiResult.ok("export started");
  }

  @GetMapping("/reports/export/excel")
  public ResponseEntity<byte[]> exportExcel() throws Exception {
    XSSFWorkbook workbook = new XSSFWorkbook();
    Sheet sheet = workbook.createSheet("report");

    Row header = sheet.createRow(0);
    header.createCell(0).setCellValue("指标");
    header.createCell(1).setCellValue("数值");

    long total = eventRepository.count();
    long pending = eventRepository.countByStatus("待确认");
    long processing = eventRepository.countByStatus("处理中");

    Row r1 = sheet.createRow(1);
    r1.createCell(0).setCellValue("事件总数");
    r1.createCell(1).setCellValue(total);

    Row r2 = sheet.createRow(2);
    r2.createCell(0).setCellValue("待确认");
    r2.createCell(1).setCellValue(pending);

    Row r3 = sheet.createRow(3);
    r3.createCell(0).setCellValue("处理中");
    r3.createCell(1).setCellValue(processing);

    Row r4 = sheet.createRow(4);
    r4.createCell(0).setCellValue("生成时间");
    r4.createCell(1).setCellValue(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    workbook.write(out);
    workbook.close();

    return ResponseEntity.ok()
      .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.xlsx")
      .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
      .body(out.toByteArray());
  }

  @GetMapping("/reports/export/pdf")
  public ResponseEntity<byte[]> exportPdf() throws Exception {
    PDDocument doc = new PDDocument();
    PDPage page = new PDPage(PDRectangle.A4);
    doc.addPage(page);

    long total = eventRepository.count();
    long pending = eventRepository.countByStatus("待确认");
    long processing = eventRepository.countByStatus("处理中");

    PDPageContentStream content = new PDPageContentStream(doc, page);
    content.beginText();
    content.setFont(PDType1Font.HELVETICA_BOLD, 16);
    content.newLineAtOffset(50, 770);
    content.showText("Fruit Maturity Report");

    content.setFont(PDType1Font.HELVETICA, 12);
    content.newLineAtOffset(0, -30);
    content.showText("Total events: " + total);
    content.newLineAtOffset(0, -18);
    content.showText("Pending: " + pending);
    content.newLineAtOffset(0, -18);
    content.showText("Processing: " + processing);
    content.newLineAtOffset(0, -18);
    content.showText("Generated at: " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    content.endText();
    content.close();

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    doc.save(out);
    doc.close();

    return ResponseEntity.ok()
      .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.pdf")
      .contentType(MediaType.APPLICATION_PDF)
      .body(out.toByteArray());
  }
}
