package io.pivotal.microservices.services.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Client controller, fetches Account info from the microservice via
 * {@link ReportService}.
 *
 * @author Paul Chapman
 */
@RestController
public class ReportController {

	@Autowired
	protected ReportService reportService;

    protected Logger logger = Logger.getLogger(ReportController.class.getName());

	public ReportController(ReportService reportService) {
		this.reportService = reportService;
	}

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields("accountNumber", "searchText");
    }

    @RequestMapping("/report/psa")
    public List<Psa> groupReport(Model model) {
        logger.info("report-service byOwner() invoked: ");

        List<Psa> psaList = reportService.find();
        //List<Psa> psaList = new ArrayList<>();
        logger.info("report-service groupReport() found: " + psaList);

        return psaList;
    }

}
