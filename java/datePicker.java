public class UiDatePicker extends CimSeleniumTestPage  {
	private String calendarLocator; 
	private final String DATE_FORMAT = "yyyy/MM/dd";
	
	 /** Initialize with default calendar locator  */
	public UiDatePicker(CimSelenium selenium){
		super(selenium);
		this.calendarLocator = "//div[@id='ui-datepicker-div']";
	}
	
	 /** Initialize with custom calendar locator. Specify "#ui-datepicker-div" location */
	public UiDatePicker(CimSelenium selenium, String calendarLocator){
		super(selenium);
		this.calendarLocator = calendarLocator;
	}
	
	public void doChooseDate(String toDateStr){
		try {
			Date date = new SimpleDateFormat(DATE_FORMAT).parse(toDateStr);
			doChooseDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void doChooseDate(Date date) {
		selectYear(new SimpleDateFormat("yyyy").format(date));
		selectMonth(new SimpleDateFormat("M月").format(date));
		selectDate(new SimpleDateFormat("d").format(date));
	}

	private void selectYear(String option){
		String locator = calendarLocator + "/div[1]/div/select[2]";
		int currentYear = Integer.parseInt(option);
		while(Integer.parseInt(selenium.getText(locator + "/option[last()]")) < currentYear){
			selenium.select(locator, selenium.getText(locator + "/option[last()]"));
		}
		selenium.select(locator, option);
	}

	private void selectMonth(String option){
		String locator = calendarLocator + "/div[1]/div/select[1]";
		selenium.select(locator, option);
	}

	private void selectDate(String option){
		String locator = calendarLocator + "/table/tbody/tr/td/a[text()='" + option + "']";
		selenium.click(locator);
	}
	
}