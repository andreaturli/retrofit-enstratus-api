package com.enstratus.api.features;

import java.util.List;

import javax.inject.Inject;

import retrofit.http.RestAdapter;

import com.enstratus.api.interfaces.Admin;
import com.enstratus.api.model.BillingCode;
import com.enstratus.api.model.Job;

public class AdminAPI {

    @Inject private RestAdapter restAdapter;

    public AdminAPI(RestAdapter restAdapter) {
        this.restAdapter = restAdapter;
    }

    public List<Job> listJobs() {
        return restAdapter.create(Admin.class).listJobs().get("jobs");
    }
    
    public List<Job> getJob(String jobId) {
        return restAdapter.create(Admin.class).getJob(jobId).get("jobs");
    }
    
    public List<BillingCode> listBillingCodes(String regionId) {
        return restAdapter.create(Admin.class).listBillingCodes(regionId).get("billingCodes");
    }
}
