package io.pivotal.microservices.services.report;

import com.fasterxml.jackson.annotation.JsonRootName;

import java.math.BigDecimal;

/**
 * Account DTO - used to interact with the {@link ReportService}.
 * 
 * @author Paul Chapman
 */
@JsonRootName("Psa")
public class Psa {

	protected Long id;
	protected String number;
	protected String owner;
	protected BigDecimal balance;

	/**
	 * Default constructor for JPA only.
	 */
	protected Psa() {
		balance = BigDecimal.ZERO;
	}

	public long getId() {
		return id;
	}

	/**
	 * Set JPA id - for testing and JPA only. Not intended for normal use.
	 * 
	 * @param id
	 *            The new id.
	 */
	protected void setId(long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	protected void setNumber(String accountNumber) {
		this.number = accountNumber;
	}

	public String getOwner() {
		return owner;
	}

	protected void setOwner(String owner) {
		this.owner = owner;
	}

	public BigDecimal getBalance() {
		return balance.setScale(2, BigDecimal.ROUND_HALF_EVEN);
	}

	protected void setBalance(BigDecimal value) {
		balance = value;
		balance.setScale(2, BigDecimal.ROUND_HALF_EVEN);
	}

	@Override
	public String toString() {
		return number + " [" + owner + "]: $" + balance;
	}

}
