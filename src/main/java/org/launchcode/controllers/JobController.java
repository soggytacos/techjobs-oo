package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String index(Model model, @PathVariable int id) {

        Job someJob = jobData.findById(id);
        // TODO #1 - get the Job with the given ID and pass it into the view
        model.addAttribute("someJob", jobData.findById(id));
        model.addAttribute("title", "Displaying Job: " + someJob.getId());
        return "job-detail";
    }


    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        model.addAttribute("title", "Add a Job");
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        if(errors.hasErrors()) {
            model.addAttribute("title", "Add a Job");
            return "new-job";
        }
        Job someJob = new Job(jobForm.getName(), jobData.getEmployers().findById(jobForm.getEmployerId()), jobData.getLocations().findById(jobForm.getLocationId()),
                jobData.getPositionTypes().findById(jobForm.getPositionTypeId()), jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId()));
        jobData.add(someJob);
        model.addAttribute("someJob", someJob);
        model.addAttribute("title", "Displaying Job: " + someJob.getId());
        model.addAttribute("id", someJob.getId());
        return "job-detail";

    }
}







