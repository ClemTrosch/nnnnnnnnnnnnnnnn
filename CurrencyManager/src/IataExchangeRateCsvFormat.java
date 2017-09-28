import java.util.Currency;

public class IataExchangeRateCsvFormat {

	public static boolean checkCurrencyValue(String currencyValue) {
		String currencyValuePattern = "[0-9][0-9]*\\,[0-9][0-9]*";
		return currencyValue.matches(currencyValuePattern);
	}

	public static boolean checkIsoCode(String isoCode) {
		try {
			Currency.getInstance(isoCode);
		} catch (NullPointerException | IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	public static boolean checkDateFormat(String date) {
		String datePattern = "((0[1-9])|(1[0-9])|(2[0-9])|(3[01]))\\.((0[1-9])|(1[012]))\\.((19|20)[0-9]{2})";
		return date.matches(datePattern);
	}

}
