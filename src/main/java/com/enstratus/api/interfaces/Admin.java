package com.enstratus.api.interfaces;

import java.util.List;
import java.util.Map;

import retrofit.http.GET;
import retrofit.http.Name;

import com.enstratus.api.model.BillingCode;
import com.enstratus.api.model.Job;

public interface Admin {
    
    @GET("/admin/Job")
    Map<String, List<Job>> listJobs();
    
    @GET("/admin/Job/{id}")
    Map<String, List<Job>> getJob(@Name("id") String jobId);
    
    @GET("/admin/BillingCode")
    Map<String, List<BillingCode>> listBillingCodes(@Name("regionId") String regionId);

}
