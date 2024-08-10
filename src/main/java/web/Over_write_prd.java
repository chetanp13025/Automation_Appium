package web;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.reporter.ExtentReporter;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;



public class Over_write_prd extends MobileDeviceInfo {
	static String uniqueValue;
	static String IRRD;
	private static String IRD;
	static String CRN;
	static String Tag_number;
	static Set<String> usedValues = new HashSet<String>();
	protected static ArrayList<String> tagList = new ArrayList<String>();
	static String filePath = ".\\csv_file\\flipkart_prd.csv";
	public static void write() throws CsvException {
		MobileDeviceInfo.fetch();
		Properties.pro();

//	public static void main(String[] args) throws CsvException {
		try {
			int columnToOverwrite = 0;
			int columnToOverwrite1 = 3;
			int columnToOverwrite2 = 6;
			int columnToOverwrite3 = 10;

			CSVReader csvReader = new CSVReader(new FileReader(filePath));
			List<String[]> records = csvReader.readAll();
			csvReader.close();

			// Generate a new value for the column
			IRRD = generateNewValue();
			setIRD(generateNewValue1());
//            String newValue2 = generateNewValue2();
			CRN = generateNewValue1();

			for (int i = 1; i < records.size(); i++) {
				String[] record = records.get(i);
				if (record.length > columnToOverwrite) {
					// Overwrite the data in the 0th index column with the new value
					record[columnToOverwrite] = IRRD;
				}
			}
			for (int i = 1; i < records.size(); i++) {
				String[] record = records.get(i);
				if (record.length > columnToOverwrite1) {
					// Overwrite the data in the 0th index column with the new value
					record[columnToOverwrite1] = getIRD();
				}
			}
			for (int i = 1; i < records.size(); i++) {
				String[] record = records.get(i);
				if (record.length > columnToOverwrite1) {
					// Overwrite the data in the 0th index column with the new value
					record[columnToOverwrite2] = getIRD();
					;
				}
			}
			for (int i = 1; i < records.size(); i++) {
				String[] record = records.get(i);
				if (record.length > columnToOverwrite3) {
					// Overwrite the data in the 0th index column with the new value
					if (record.length > columnToOverwrite3) {
						record[columnToOverwrite3] = generateUniqueRandomValue(usedValues);
					}
				}
			}

			CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath));
			csvWriter.writeAll(records);
			csvWriter.close();

			System.out.println("Data in the IRRD column overwritten successfully with: " + IRRD);
			System.out.println("Data in the IRD column overwritten successfully with: " + getIRD());
			System.out.println("Data in the CRN column overwritten successfully with: " + getIRD());
			tags();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return;
	}

//	public static ArrayList<String> tags() {
//		return Fetch_tag.tags();
//	}
	private static String generateNewValue() {
		// Generate a new random string for the column
		Random random = new Random();
		return "FK-BB" + random.nextInt(10000);
	}

	private static String generateNewValue1() {
		// Generate a new random string for the column
		Random random1 = new Random();
		return "IRD-BB" + random1.nextInt(10000);
	}

//	private static String generateNewValue3() {
//		// Generate a new random string for the column
//		Random random3 = new Random();
//		return "Tag-f00" + random3.nextInt(10000);
//	}
	private static String generateUniqueRandomValue(Set<String> usedValues) {
		// Generate a new unique random string for the column
		Random random = new Random();

		do {
			uniqueValue = "Tag-f000" + random.nextInt(10000);
		} while (!usedValues.add(uniqueValue));

		return uniqueValue;
	}

	public static String getIRD() {
		return IRD;
	}

	public static void setIRD(String iRD) {
		IRD = iRD;
	}

//}
	public static ArrayList<String> tags() {
		try (FileReader fileReader = new FileReader(filePath);
				CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT)) {
//	 int rowIndex1 = 1; // Replace with the desired row number
//        int cellIndex2 = 0;
			for (CSVRecord csvRecord : csvParser) {
				Tag_number = csvRecord.get(10);
				tagList.add(Tag_number);
				System.out.println(Tag_number);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tagList;
	}

//public static ArrayList<String> getTagList() {
//	return tagList;
//}
//
//public static void setTagList(ArrayList<String> tagList) {
//	Over_write_prd.tagList = tagList;
//}
}
