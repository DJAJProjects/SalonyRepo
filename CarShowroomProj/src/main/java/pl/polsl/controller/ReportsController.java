package pl.polsl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.repository.ReportsRepository;
import pl.polsl.model.Report;


/**
 * Created by Kuba on 02.04.2016.
 */

@Component
public class ReportsController {

    @Autowired
    private ReportsRepository reportsRepository;

    @Transactional
    public Report findReport(int id){
        return reportsRepository.findOne(id);}
}
