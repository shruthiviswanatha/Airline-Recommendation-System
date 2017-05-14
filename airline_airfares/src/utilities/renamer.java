package utilities;

import java.io.File;

public class renamer {
	
	public static void main(String[] args){
		
		String path = args[0];
		
		// Parent Directory
		File parentDir = new File(path);
		
		File[] allFiles = parentDir.listFiles();

		for(int x=0; x<allFiles.length; x++){
			
			System.out.println("Renaming " + allFiles[x].getPath() + " to " + allFiles[x].getPath() + ".csv" );
			
			allFiles[x].renameTo(new File(allFiles[x].getPath() + ".csv"));
		}
		
		System.out.println("Complete.");
		
	}

}
