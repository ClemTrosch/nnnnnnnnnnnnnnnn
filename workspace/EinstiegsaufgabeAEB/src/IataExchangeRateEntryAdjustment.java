import java.math.BigDecimal;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.TreeSet;
import org.apache.commons.lang3.time.DateUtils;

public class IataExchangeRateEntryAdjustment {
	private IataExchangeRateDataSet exchangeRateDataSet = new IataExchangeRateDataSet();
	private TreeSet<IataExchangeRateData> overlappingEntries = new TreeSet<>();
	private IataExchangeRateData overlappingEntryFirst = null;
	private IataExchangeRateData overlappingEntryLast = null;
	private Double exchangeRate;
	private String currencyIsoCode;
	private Date from;
	private Date to;
	private OverlappingStates states;

	public IataExchangeRateEntryAdjustment(IataExchangeRateDataSet exchangeRateDataSet, Double exchangeRate,
			String currencyIsoCode, Date from, Date to) {
		this.exchangeRateDataSet = exchangeRateDataSet;
		this.exchangeRate = exchangeRate;
		this.currencyIsoCode = currencyIsoCode;
		this.from = from;
		this.to = to;
		
		

		states = new OverlappingStates(from, to);
	}

	public void adjustEntries() {
		createNewTreeSetWithOverlappingEntries();

		removeOverlappingEntriesFromExchangeRateSet();

		addUserEntryToExchangeRateSet();

		if (!overlappingEntries.isEmpty()) {
			addOverlappingEntriesToExchangeRateSet();
		}
	}

	private void createNewTreeSetWithOverlappingEntries() {
		for (IataExchangeRateData entry : exchangeRateDataSet.getExchangeRateDataSet()) {
			if (isOverlappingWithExistedEntry(entry))
				overlappingEntries.add(entry);
		}
	}

	private boolean isOverlappingWithExistedEntry(IataExchangeRateData entry) {
		if (currencyIsoCode.equals(entry.getCurrencyIsoCode())) {
			if (from.equals(entry.getPeriodFrom()) || from.equals(entry.getTo()) || to.equals(entry.getPeriodFrom())
					|| to.equals(entry.getTo())) {
				return true;
			} else if (from.after(entry.getPeriodFrom()) && (from.before(entry.getTo()))) {
				return true;
			} else if (to.after(entry.getPeriodFrom()) && to.before(entry.getTo())) {
				return true;
			} else if (from.before(entry.getPeriodFrom()) && (to.after(entry.getTo()))) {
				return true;
			}
		}
		return false;
	}

	private void removeOverlappingEntriesFromExchangeRateSet() {
		exchangeRateDataSet.getExchangeRateDataSet().removeAll(overlappingEntries);
	}

	private void addUserEntryToExchangeRateSet() {
		exchangeRateDataSet.getExchangeRateDataSet()
				.add(new IataExchangeRateData(new BigDecimal(exchangeRate), currencyIsoCode, from, to));
	}

	private void addOverlappingEntriesToExchangeRateSet() {
		setOverlappingEntriesFirstAndLast();

		if (states.isNotIdenticalToExistedOne()) {
			if (states.onlyOverlapsInnerOrEndOfPeriod()) {
				if (overlappingEntryLast != null) {
					if (to.before(overlappingEntryLast.getTo())) {
						
						addToExchangeRateDataSet(overlappingEntryLast, DateUtils.addDays(to, 1), overlappingEntryLast.getTo());
						
						addToExchangeRateDataSet(overlappingEntryFirst, overlappingEntryFirst.getPeriodFrom(),
								DateUtils.addDays(from, -1));
					}
				} else if (states.periodIsGreaterThanOneDay()) {
					addToExchangeRateDataSet(overlappingEntryFirst, overlappingEntryFirst.getPeriodFrom(),
							DateUtils.addDays(from, -1));
				}
			}
			if (states.onlyOverlapsInnerPeriod()) {
				addToExchangeRateDataSet(overlappingEntryFirst, overlappingEntryFirst.getPeriodFrom(),
						DateUtils.addDays(from, -1));
				addToExchangeRateDataSet(overlappingEntryFirst, DateUtils.addDays(to, 1), overlappingEntryFirst.getTo());
			}
			if (states.onlyOverlapsInitialOrInnerPeriod()) {
				if (states.periodIsGreaterThanOneDay()) {
					addToExchangeRateDataSet(overlappingEntryFirst, DateUtils.addDays(to, 1),
							overlappingEntryFirst.getTo());
				} else {
					addToExchangeRateDataSet(overlappingEntryFirst, DateUtils.addDays(to, 1),
							overlappingEntryFirst.getTo());
				}
			}
		}
		overlappingEntries.clear();
	}

	private void setOverlappingEntriesFirstAndLast() {
		try {
			overlappingEntryFirst = overlappingEntries.first();
			if (!overlappingEntries.first().equals(overlappingEntries.last())) {
				overlappingEntryLast = overlappingEntries.last();
			}
			states.setOverlappingEntryFirst(overlappingEntryFirst);
		} catch (NullPointerException | NoSuchElementException e) {
			e.printStackTrace();
		}
	}

	public IataExchangeRateData getOverlappingEntryFirst() {
		return overlappingEntryFirst;
	}

	public IataExchangeRateData getOverlappingEntryLast() {
		return overlappingEntryLast;
	}

	private void addToExchangeRateDataSet(IataExchangeRateData entry, Date periodFrom, Date periodTo) {
		exchangeRateDataSet.getExchangeRateDataSet().add(
				new IataExchangeRateData(entry.getExchangeRate(), entry.getCurrencyIsoCode(), periodFrom, periodTo));
	}
}
