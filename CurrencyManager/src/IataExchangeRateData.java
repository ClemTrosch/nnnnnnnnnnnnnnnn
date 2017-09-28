import java.math.BigDecimal;
import java.util.Date;

public class IataExchangeRateData implements Comparable<IataExchangeRateData> {
	private BigDecimal exchangeRate;
	private String currencyIsoCode;
	private Date from;
	private Date to;

	public IataExchangeRateData(BigDecimal exchangeRate, String currencyIsoCode, Date from, Date to) {
		super();
		this.exchangeRate = exchangeRate;
		this.currencyIsoCode = currencyIsoCode;
		this.from = from;
		this.to = to;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal currencyValue) {
		this.exchangeRate = currencyValue;
	}

	public String getCurrencyIsoCode() {
		return currencyIsoCode;
	}

	public void setCurrencyIsoCode(String currencyIsoCode) {
		this.currencyIsoCode = currencyIsoCode;
	}

	public Date getPeriodFrom() {
		return from;
	}

	public void setPeriodFrom(Date validityPeriodStart) {
		this.from = validityPeriodStart;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date validityPeriodEnd) {
		this.to = validityPeriodEnd;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currencyIsoCode == null) ? 0 : currencyIsoCode.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((exchangeRate == null) ? 0 : exchangeRate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IataExchangeRateData other = (IataExchangeRateData) obj;
		if (currencyIsoCode == null) {
			if (other.currencyIsoCode != null)
				return false;
		} else if (!currencyIsoCode.equals(other.currencyIsoCode))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (exchangeRate == null) {
			if (other.exchangeRate != null)
				return false;
		} else if (!exchangeRate.equals(other.exchangeRate))
			return false;
		return true;
	}

	@Override
	public int compareTo(IataExchangeRateData arg0) {
		return this.getPeriodFrom().compareTo(arg0.getPeriodFrom());
	}
}
