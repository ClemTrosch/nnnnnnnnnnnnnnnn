import java.util.Date;

public class OverlappingStates {
	private IataExchangeRateData overlappingEntryFirst;
	private Date from;
	private Date to;

	public OverlappingStates(Date from, Date to) {
		this.from = from;
		this.to = to;
	}

	public void setOverlappingEntryFirst(IataExchangeRateData overlappingEntryFirst) {
		this.overlappingEntryFirst = overlappingEntryFirst;
	}

	public boolean isOverlappingPeriodStart() {
		return (from.equals(overlappingEntryFirst.getPeriodFrom()) || to.equals(overlappingEntryFirst.getPeriodFrom())
				|| (from.before(overlappingEntryFirst.getPeriodFrom())
						&& to.after(overlappingEntryFirst.getPeriodFrom())));
	}

	public boolean isOverlappingPeriodInner() {
		return (from.after(overlappingEntryFirst.getPeriodFrom()) && from.before(overlappingEntryFirst.getTo())
				|| to.after(overlappingEntryFirst.getPeriodFrom()) && to.before(overlappingEntryFirst.getTo()));
	}

	public boolean isOverlappingPeriodEnd() {
		return from.equals(overlappingEntryFirst.getTo()) || to.equals(overlappingEntryFirst.getTo())
				|| (from.before(overlappingEntryFirst.getTo()) && to.after(overlappingEntryFirst.getTo()));
	}

	public boolean isNotIdenticalToExistedOne() {
		return isOverlappingPeriodStart() || isOverlappingPeriodInner() || isOverlappingPeriodEnd();
	}

	public boolean onlyOverlapsInnerOrEndOfPeriod() {
		return !isOverlappingPeriodStart() && (isOverlappingPeriodInner() || isOverlappingPeriodEnd());
	}

	public boolean periodIsGreaterThanOneDay() {
		return overlappingEntryFirst.getPeriodFrom().before(overlappingEntryFirst.getTo());
	}

	public boolean onlyOverlapsInnerPeriod() {
		return !isOverlappingPeriodStart() && isOverlappingPeriodInner() && !isOverlappingPeriodEnd();
	}

	public boolean onlyOverlapsInitialOrInnerPeriod() {
		return (isOverlappingPeriodStart() || isOverlappingPeriodInner()) && !isOverlappingPeriodEnd();
	}
}
