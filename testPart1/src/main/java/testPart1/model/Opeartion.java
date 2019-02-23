package testPart1.model;

import java.math.BigDecimal;
import java.sql.Date;

public class Opeartion {
	private BigDecimal amount;
	private Date date;
	private Office office;
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = new Date(date.getTime());
	}
	public Office getOffice() {
		return office;
	}
	public void setOffice(Office office) {
		this.office = office;
	}
}
