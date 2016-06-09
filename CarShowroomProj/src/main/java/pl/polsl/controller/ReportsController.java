package pl.polsl.controller;

import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.Data;
import pl.polsl.model.*;
import pl.polsl.repository.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Created by Kuba on 02.04.2016.
 */

@Component
@Path("/raports")
@Produces(MediaType.APPLICATION_JSON)
public class ReportsController {

    @Autowired
    private ReportsRepository reportsRepository;

    @Autowired
    private ShowroomsRepository showroomsRepository;

    @Autowired
    private CarsRepository carsRepository;

    @Autowired
    private WorkersRepository workersRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Transactional
    public Report findReport(int id){
        return reportsRepository.findOne(id);}

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Report> findAllRaports() {
        return Lists.newArrayList(reportsRepository.findAll());
    }



    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Report findOne(int id){
        return reportsRepository.findOne(id);
    }

    public void deleteReport(int id){
        reportsRepository.delete(id);
    }

    public Report addReport(String name,
                            int showroomId ,
                            String content,
                            Date dateBeggining,
                            Date dateEnd
                            ) {
        Showroom targetShowroom = showroomsRepository.findOne(showroomId);
        List<Car> cars = carsRepository.findAllFromTheSameShowroom(targetShowroom);
        List<Worker> workers = workersRepository.findAllFromTheSameShowroom(targetShowroom);
        Double income  = invoiceRepository.getIncomeForShowroom(targetShowroom, dateBeggining, dateEnd);
        if(income == null) income = 0.0;
        Double monthlyPayment = workersRepository.getMonthlyPaymentsFromSameShowroom(targetShowroom);
        if(monthlyPayment == null)monthlyPayment = 0.0;
        String showroomName = targetShowroom.getName();

        long datesDiff = dateEnd.getTime() - dateBeggining.getTime();
        long days = TimeUnit.DAYS.convert(datesDiff, TimeUnit.MILLISECONDS);
        int monthsWorked = (int)days/30;
        Double outcome = monthlyPayment * monthsWorked;
        Double profit = income - outcome;

        content = "";
        content += name + "\n";
        content += "Raport dotyczy: " + showroomName + "\n";
        content += "Ilość samochodów: " + cars.size() + "\n";
        content += "Ilość pracowników: " + workers.size()+ "\n";
        content += "Dochód: " + income + "\n";
        content += "Wydatki: " + outcome + "\n";
        content += "Zysk: " + profit;

        return reportsRepository.save(new Report(name, targetShowroom,content, dateBeggining, dateEnd));
    }

    public List<Report> findReportsRelatedToWorker(Worker worker){
        List<Report> retList;
        String position = worker.getPosition().getValue();
        if( position.equals(Data.directorValue))
            retList = reportsRepository.findRelatedToDirector(worker);
        else if(position.equals(Data.adminValue))
            retList = findAllRaports();
        else retList = null;

        if(retList == null)retList = new ArrayList<Report>();

        return retList;
    }
}
