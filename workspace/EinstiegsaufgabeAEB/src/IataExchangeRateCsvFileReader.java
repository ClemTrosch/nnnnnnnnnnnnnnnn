import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IataExchangeRateCsvFileReader {
	private SimpleDateFormatStringToDate formatter = new SimpleDateFormatStringToDate();
	private IataExchangeRateDataSet exchangeRateDataSet = new IataExchangeRateDataSet();
	private final String SEMICOLON_DELIMITER = ";";
	private final int MAX_LENGTH_OF_COLUMN = 5;

	// Exchange rate attributes index
	private final int EXCHANGE_RATE = 1;
	private final int CURRENCY_ISO_CODE = 2;
	private final int FROM = 3;
	private final int TO = 4;

	public IataExchangeRateCsvFileReader(IataExchangeRateDataSet exchangeRateDataSet) {
		this.exchangeRateDataSet = exchangeRateDataSet;
	}

	public void readCsvFile(String fileName) {

		BufferedReader fileReader = null;
		try {
			String line = "";
			fileReader = new BufferedReader(new FileReader(fileName));

			while ((line = fileReader.readLine()) != null) {
				String[] tokens = line.split(SEMICOLON_DELIMITER);

				if (csvLineIsCorrect(tokens)) {
					tokens[EXCHANGE_RATE] = formatExchangeRate(tokens);
					
					addReadedDataToDataSet(tokens);
				}
			}
		} catch (Exception e) {
			System.out.println("Error in IataExchangeRateCsvFileReader");
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				System.out.println("Error while closing fileReader !!!");
				e.printStackTrace();
			}
		}
	}

	// Returns true, if exchange rate attributes in csv line are correct and no commentar exist
	public boolean csvLineIsCorrect(String[] tokens) {
		return tokens.length == MAX_LENGTH_OF_COLUMN
				&& IataExchangeRateCsvFormat.checkCurrencyValue(tokens[EXCHANGE_RATE])
				&& IataExchangeRateCsvFormat.checkIsoCode(tokens[CURRENCY_ISO_CODE])
				&& IataExchangeRateCsvFormat.checkDateFormat(tokens[FROM])
				&& IataExchangeRateCsvFormat.checkDateFormat(tokens[TO]);
	}

	private String formatExchangeRate(String[] tokens) {
		return tokens[EXCHANGE_RATE].replace(",", ".");
	}
	
	private void addReadedDataToDataSet(String[] tokens){
		IataExchangeRateData iataExchangeRateData = new IataExchangeRateData(
				new BigDecimal(tokens[EXCHANGE_RATE]), tokens[CURRENCY_ISO_CODE],
				formatter.parseStringToDate(tokens[FROM]), formatter.parseStringToDate(tokens[TO]));
		exchangeRateDataSet.getExchangeRateDataSet().add(iataExchangeRateData);
	}
}
