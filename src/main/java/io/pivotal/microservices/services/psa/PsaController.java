package io.pivotal.microservices.services.psa;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Client controller, fetches Account info from the microservice via
 *
 * @author Paul Chapman
 */
@RestController
public class PsaController {

	protected Logger logger = Logger.getLogger(PsaController.class
			.getName());

	@RequestMapping(value = "/psa/search", produces = "application/json")
	public List<Psa> ownerSearch(Model model) {
		logger.info("psa-service byOwner() invoked: ");

		List<Psa> psaList = new ArrayList<>();
		logger.info("psa-service search() found: " + psaList);
		Psa psa;
		for (int i = 0; i < 10 ; i++) {
			psa = new Psa();
			psa.setOwner("owner " + i);
			psa.setNumber("number " + i);
			psa.setId(i);
			psa.setBalance(new BigDecimal(500 * i));
			psaList.add(psa);
		}

		return psaList;
	}
}
