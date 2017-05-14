package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilesystemObjectSorter {

	public final String startDirPath;
	
	public FilesystemObjectSorter(String startDirPath){
		
		this.startDirPath = startDirPath; 
		
	}
	
	public void sortFiles(){
		
		File dir = new File(startDirPath);
		
		File[] directoryListing = dir.listFiles();
		  
		if (directoryListing != null) {
			for (File f : directoryListing) {
		     
				Pattern pattern = Pattern.compile("[tT]able\\d+");
				Matcher matcher = pattern.matcher(f.getPath());
				if (matcher.find())
				{
					
					String tableName = matcher.group(0);
					
				    
				    
				    File directory = new File(f.getParent() + File.separator + tableName.toLowerCase());
							
				    if(!directory.exists()){
				    	directory.mkdir();
				    	System.out.println("Created directory " + directory.getPath());
				    }
				    
				    File destinationFile = new File(directory.getPath() + File.separator + f.getName());
				    
				    f.renameTo(destinationFile);
							
				    System.out.println("Moved file " + f.getName() + " to " + directory.getPath());
				    
				    
				    try {
						convertXlsToCsv(destinationFile, new File(
								directory.getPath() + File.separator + f.getName().replaceFirst("[.][^.]+$", "") + ".csv"));
					} catch (InvalidFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
		    }
		}
		
		
	}
	
	public void convertFiles(){
		
		File dir = new File(startDirPath);
		
		File[] directoryListing = dir.listFiles();
		  
		if (directoryListing != null) {
			for (File f : directoryListing) {
		     
				Pattern pattern = Pattern.compile("[tT]able\\d+");
				Matcher matcher = pattern.matcher(f.getPath());
				if (matcher.find())
				{
					
					String tableName = matcher.group(0);
					
				    File directory = new File(f.getParent());
				
				    File destinationFile = new File(directory.getPath() + File.separator + f.getName());
				    
				    
				    try {
						convertXlsToCsv(destinationFile, new File(
								directory.getPath() + File.separator + f.getName().replaceFirst("[.][^.]+$", "") + ".csv"));
					} catch (InvalidFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
		    }
		}
		
		
	}
	
	private void convertXlsToCsv(File inputFile, File outputFile) throws InvalidFormatException 
	{
        // For storing data into CSV files
        StringBuffer data = new StringBuffer();
        try 
        {
        FileOutputStream fos = new FileOutputStream(outputFile);

        Workbook wb = WorkbookFactory.create(new FileInputStream(inputFile));
        Sheet sheet = wb.getSheetAt(0);
        
        Cell cell;
        Row row;

        // Iterate through each rows from first sheet
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) 
        {
                row = rowIterator.next();
                // For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) 
                {
                        cell = cellIterator.next();
                        
                        if(cell.getCellTypeEnum() == CellType.STRING){
                        	cell.setCellValue(cell.getStringCellValue().replace(',', ' '));
                        }
                        
                        data.append(cell + ",");
                       
                }
                
                data.append("\r\n");
        }

        fos.write(data.toString().getBytes());
        fos.close();
        }
        catch (FileNotFoundException e) 
        {
                e.printStackTrace();
        }
        catch (IOException e) 
        {
                e.printStackTrace();
        }
	}

	
	
	public static void main(String[] args) throws Exception{
		
		if(args[0] == null || args[0].isEmpty()){
			throw new Exception("Starting file path cannot be empty.");
		}
		else{
		
			FilesystemObjectSorter sorter = new FilesystemObjectSorter(args[0]);
			
			//sorter.sortFiles();
			
			sorter.convertFiles();
			
			System.out.println("Done.");
			
		}
		
	}
	
	
}
