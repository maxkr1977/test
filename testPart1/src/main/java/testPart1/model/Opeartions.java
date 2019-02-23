package testPart1.model;

import java.math.BigDecimal;
import java.util.Date;

public class Opeartions {
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
	
	public void setDate(long time) {
		this.date = new Date(time);
	}
	
	public Office getOffice() {
		return office;
	}
	
	public void setOffice(Office office) {
		this.office = office;
	}

	@Override
	public String toString() {
		return office.toString() + " " + date.getTime() +" " + amount.toString();
	}
}
