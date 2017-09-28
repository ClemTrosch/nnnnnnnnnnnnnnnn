import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class IataExchangeRateCsvFileReaderTest {
	private IataExchangeRateCsvFileReader iataExchangeRateCsvFileReader;
	private final IataExchangeRateDataSet exchangeRateDataSet = new IataExchangeRateDataSet();

	@Before
	public void setUp() {
		iataExchangeRateCsvFileReader = new IataExchangeRateCsvFileReader(exchangeRateDataSet);
	}

	@Test
	public void testCsvLineIsCorrect_With_ValidInput() {
		String csvLine = "Ägypten;7,71163;EGP;10.12.2010;09.01.2011;;";
		String[] tokens = csvLine.split(";");
		
		assertTrue(iataExchangeRateCsvFileReader.csvLineIsCorrect(tokens));
	}

	@Test
	public void testCsvLineIsCorrect_With_InValidExchangeRate() {
		String csvLine = "Ägypten;7.711.63;EGP;10.12.2010;09.01.2011;;";
		String[] tokens = csvLine.split(";");
		
		assertFalse(iataExchangeRateCsvFileReader.csvLineIsCorrect(tokens));
	}

	@Test
	public void testCsvLineIsCorrect_With_InValidIsoCode() {
		String csvLine = "Ägypten;7.71163;EGG;10.12.2010;09.01.2011;;";
		String[] tokens = csvLine.split(";");
		
		assertFalse(iataExchangeRateCsvFileReader.csvLineIsCorrect(tokens));
	}

	@Test
	public void testCsvLineIsCorrect_With_InValidDate() {
		String csvLine = "Ägypten;7.71163;EGG;10/12/2010;09/01/2011;;";
		String[] tokens = csvLine.split(";");
		
		assertFalse(iataExchangeRateCsvFileReader.csvLineIsCorrect(tokens));
	}
	
	@Test
	public void testCsvLineIsCorrect_With_ExistingComment() {
		String csvLine = "Ägypten;7.71163;EGG;10/12/2010;09/01/2011; Umrechnungskurs für 1000 WE (Währungseinheiten);;";
		String[] tokens = csvLine.split(";");
		
		assertFalse(iataExchangeRateCsvFileReader.csvLineIsCorrect(tokens));
	}
	
	@Test
	public void testCsvLineIsCorrect_WithMissingValues() {
		String csvLine = "7.71163;EGG;10/12/2010;09/01/2011;;";
		String[] tokens = csvLine.split(";");
		
		assertFalse(iataExchangeRateCsvFileReader.csvLineIsCorrect(tokens));
	}
}

