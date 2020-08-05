package util;

import com.alibaba.fastjson.JSONObject;
import config.EnumExcel;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FileUtils {

    private static final String FILE_PATH = "src\\main\\resources\\log.txt";
    private final Logger LOG = LoggerFactory.getLogger(FileUtils.class);
    private Workbook workbook = null;
    private Sheet sheet = null;
    private int rows;
    private int cols;
    private Row row = null;
    private File file;
    private int sheetNo;

    public FileUtils(){

    }

    public FileUtils(String filePath,int sheetNo){
        this.file = new File(filePath);
        this.sheetNo = sheetNo;
        try (FileInputStream inputStream = new FileInputStream(this.file)){
            this.workbook = WorkbookFactory.create(inputStream);
            this.sheet = workbook.getSheetAt(this.sheetNo);
            this.rows = sheet.getLastRowNum() + 1;
            Row row = sheet.getRow(0);
            this.cols = row.getPhysicalNumberOfCells();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 写入日志文件
     * @param str 需要写入的数据
     * **/
    public static void writeLog(String str){
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(FILE_PATH,true)
                , StandardCharsets.UTF_8))
        {
            writer.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * 读取单元格内容
     * @param colNo 纵坐标
     * @param rowNo 横坐标
     * **/
    public JSONObject read(int rowNo,int colNo){
        JSONObject json = new JSONObject();
        Cell cell = this.sheet.getRow(rowNo).getCell(colNo);

        if (CellType.BOOLEAN == cell.getCellType())
        {
            json.put(this.sheet.getRow(0).getCell(colNo).getStringCellValue(),String.valueOf(cell.getBooleanCellValue()));
            return json;
        }
        else if (CellType.NUMERIC == cell.getCellType())
        {
            NumberFormat format = NumberFormat.getInstance();
            format.setGroupingUsed(false);
            json.put(this.sheet.getRow(0).getCell(colNo).getStringCellValue(),format.format(cell.getNumericCellValue()));
            return json;
        }
        else if (CellType.FORMULA == cell.getCellType())
        {
            this.workbook.getCreationHelper().createFormulaEvaluator().evaluateFormulaCell(cell);
            String strCell;
            try {
                strCell = String.valueOf(cell.getNumericCellValue());
            }catch (IllegalStateException e){
                strCell = cell.getStringCellValue();
            }
            json.put(this.sheet.getRow(0).getCell(colNo).getStringCellValue(),strCell);
            return json;
        }
        else {
            json.put(this.sheet.getRow(0).getCell(colNo).getStringCellValue(),cell.getStringCellValue());
            return json;
        }
    }

    /**
     * 按行读取
     * @param rowNo 行号
     * @return 返回有序map
     * **/
    public Map<String, Object> readLine(int rowNo){
        Map<String,Object> map = new LinkedHashMap<>();
        row = sheet.getRow(rowNo);

        Cell cell;

        for (int column = 0; column < cols;column++){
            cell = sheet.getRow(rowNo).getCell(column);
            if (cell == null){
                continue;
            }
            if (CellType.BOOLEAN == cell.getCellType())
            {
                map.put(this.sheet.getRow(0).getCell(column).getStringCellValue(),String.valueOf(cell.getBooleanCellValue()));
            }
            else if (CellType.NUMERIC == cell.getCellType())
            {
                NumberFormat format = NumberFormat.getInstance();
                format.setGroupingUsed(false);
                map.put(this.sheet.getRow(0).getCell(column).getStringCellValue(),format.format(cell.getNumericCellValue()));
            }
            else if (CellType.FORMULA == cell.getCellType())
            {
                this.workbook.getCreationHelper().createFormulaEvaluator().evaluateFormulaCell(cell);
                String strCell;
                try {
                    strCell = String.valueOf(cell.getNumericCellValue());
                }catch (IllegalStateException e){
                    strCell = cell.getStringCellValue();
                }
                map.put(this.sheet.getRow(0).getCell(column).getStringCellValue(),strCell);
            }
            else {
                map.put(this.sheet.getRow(0).getCell(column).getStringCellValue(),cell.getStringCellValue());
            }
        }
        return map;
    }

    /**
     * 读取整个excel
     * **/
    public List<Map<String, Object>> readAll(){
        List<Map<String,Object>> list = new LinkedList<>();
        for (int i = 0;i < this.rows;i++){
            Map<String,Object> map = this.readLine(i);
            list.add(map);
        }
        return list;
    }

    /**
     * 往excel写入数据
     * @param rowNo 行号
     * @param colNo 列号
     * @param color 设置单元格的颜色，[red,green,grey]
     * @param content 单元个内容
     * **/
    public void writer(int rowNo, int colNo, String content, EnumExcel color) throws IOException {
        String enumColor = color.getVal();
        this.row = sheet.getRow(rowNo);
        if (row == null){
            sheet.createRow(rowNo);
        }
        if (row.getCell(colNo) == null){
            row.createCell(colNo);
        }
        Cell cell = row.getCell(colNo);
        cell.setCellValue(content);
        CellStyle style = this.workbook.createCellStyle();
        if ("red".equalsIgnoreCase(enumColor)){
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }
        else if ("green".equalsIgnoreCase(enumColor)){
            style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }else if ("grey".equalsIgnoreCase(enumColor)){
            style.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }

        cell.setCellStyle(style);

        this.workbook.write(new FileOutputStream(this.file));
        LOG.info("成功写入 >> {}",this.file.getAbsolutePath());
    }

    /**
     * 读取文档内单元格内容相同的所有行
     * @param content 单元格内容
     * **/
    public List<Map<String,Object>> read(String content){
        List<Map<String,Object>> list = new LinkedList<>();
        for (Map<String,Object> map : this.readAll()){
            for (Map.Entry<String,Object> entry : map.entrySet()){
                if ("function".equals(entry.getKey())){
                    if (content.equals(map.get(entry.getKey()).toString())){
                        list.add(map);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 通过单元格内容获取单元额坐标
     * @param cellContent 单元格内容
     * **/
    public JSONObject getXYZ(String cellContent){
        JSONObject object = new JSONObject();
        this.row = this.sheet.getRow(0);
        Cell cell = null;
        for (int i = 0;i < cols;i++){
            cell = this.row.getCell(i);
            if (cellContent.equals(cell.getStringCellValue())){
                object.put("rowNo",row.getRowNum());
                object.put("columnNo",i);
                return object;
            }
        }
        return null;
    }


    /**
     * 关闭资源
     * **/
    public void close() throws IOException {
        if (this.workbook != null){
            workbook.close();
        }
    }

    /**
     * 获取总行数
     * **/
    public int getRows(){
        return this.rows;
    }

    /**
     * 获取总列数
     * **/
    public int getCols(){
        return this.cols;
    }

}
