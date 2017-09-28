import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class IataExchangeRateDataSet {
	private Set<IataExchangeRateData> exchangeRateDataSet = new HashSet<>();

	public void printSelectedEntry(String currencyIsoCode, Date date) {
		boolean exchangeRateDataNotFound = true;

		for (IataExchangeRateData entry : getExchangeRateDataSet()) {
			if (hasSameIsoCodeAndIsInSamePeriod(entry, currencyIsoCode, date)) {
				exchangeRateDataNotFound = false;
				System.out.printf("1 Euro entspricht %.4f %s im Zeitraum vom %s bis zum %s.%n%n",
						entry.getExchangeRate().setScale(4, BigDecimal.ROUND_UP), entry.getCurrencyIsoCode(),
						new SimpleDateFormat("dd.MM.yyyy").format(entry.getPeriodFrom()),
						new SimpleDateFormat("dd.MM.yyyy").format(entry.getTo()));
			}
		}
		if (exchangeRateDataNotFound) {
			System.out.printf(
					"Für den Währungskurs %s zu dem angegebenen Datum %s wurde kein Umrechnungskurs gefunden.%n%n",
					currencyIsoCode, new SimpleDateFormat("dd.MM.yyyy").format(date));
		}
	}

	// Returns true if there exist an entry with specific currencyIsoCode and date
	private boolean hasSameIsoCodeAndIsInSamePeriod(IataExchangeRateData entry, String currencyIsoCode, Date date) {
		return (entry.getCurrencyIsoCode().equals(currencyIsoCode)
				&& ((entry.getPeriodFrom().before(date) || entry.getPeriodFrom().equals(date))
						&& (entry.getTo().after(date) || entry.getTo().equals(date))));
	}

	public Set<IataExchangeRateData> getExchangeRateDataSet() {
		return this.exchangeRateDataSet;
	}
}
