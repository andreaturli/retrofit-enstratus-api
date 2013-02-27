package com.enstratus.api.features;

import static org.testng.Assert.assertNotNull;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.enstratus.api.model.BillingCode;
import com.enstratus.api.model.Job;
import com.enstratus.api.model.Region;

@Test(groups = "live", testName = "AdminAPILiveTest")
public class AdminAPILiveTest extends BasicEnstratusLiveTest {

    private AdminAPI api;
    private Region region;

    @BeforeClass
    public void beforeClass() throws Exception {
        api = enstratusContext.getAdminAPI();
        assertNotNull(api);
        region = tryFindRegionInEUorNull();
        Assert.assertNotNull(region);
    }

    @Test
    public void listBillingCodes() throws Exception {
        for (BillingCode billingCode : api.listBillingCodes(region.getRegionId())) {
            assertNotNull(billingCode.getBillingCodeId());
        }
    }

    @Test
    public void listJobs() throws Exception {
        for (Job job : api.listJobs()) {
            assertNotNull(job.getJobId());
        }
    }
}
