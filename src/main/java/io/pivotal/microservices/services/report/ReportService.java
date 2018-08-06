package io.pivotal.microservices.services.report;

import io.pivotal.microservices.exceptions.AccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Hide the access to the microservice inside this local service.
 * 
 * @author Paul Chapman
 */
@Service
public class ReportService {

	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;

	protected String serviceUrl;

	protected Logger logger = Logger.getLogger(ReportService.class
			.getName());

	public ReportService(String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl
				: "http://" + serviceUrl;
	}

	/**
	 * The RestTemplate works because it uses a custom request-factory that uses
	 * Ribbon to look-up the service to use. This method simply exists to show
	 * this.
	 */
	@PostConstruct
	public void demoOnly() {
		// Can't do this in the constructor because the RestTemplate injection
		// happens afterwards.
		logger.warning("The RestTemplate request factory is "
				+ restTemplate.getRequestFactory().getClass());
	}

	public Psa findByNumber(String accountNumber) {

		logger.info("findByNumber() invoked: for " + accountNumber);
		return restTemplate.getForObject(serviceUrl + "/accounts/{number}",
				Psa.class, accountNumber);
	}

	public List<Psa> find() {
		logger.info("find() invoked");
		Psa[] psa = null;

		try {
			psa = restTemplate.getForObject(serviceUrl + "/psa/search", Psa[].class);
		} catch (HttpClientErrorException e) { // 404
			logger.info(e.getMessage());
		}

		if (psa == null || psa.length == 0)
			return null;
		else
			return Arrays.asList(psa);
	}

	public List<Psa> byOwnerContains(String name) {
		logger.info("byOwnerContains() invoked:  for " + name);
		Psa[] accounts = null;

		try {
			accounts = restTemplate.getForObject(serviceUrl
					+ "/accounts/owner/{name}", Psa[].class, name);
		} catch (HttpClientErrorException e) { // 404
			// Nothing found
		}

		if (accounts == null || accounts.length == 0)
			return null;
		else
			return Arrays.asList(accounts);
	}

	public Psa getByNumber(String accountNumber) {
		Psa account = restTemplate.getForObject(serviceUrl
				+ "/accounts/{number}", Psa.class, accountNumber);

		if (account == null)
			throw new AccountNotFoundException(accountNumber);
		else
			return account;
	}
}
