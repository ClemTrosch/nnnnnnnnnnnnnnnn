import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleDateFormatStringToDate {
	private String expectedPattern = "dd.MM.yyyy";
	private SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
	private Date parsedDate;

	public Date parseStringToDate(String date) {
		try {
			parsedDate = formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parsedDate;
	}
}
