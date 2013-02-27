package com.enstratus.api.features;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.enstratus.api.model.BillingCode;
import com.enstratus.api.model.Direction;
import com.enstratus.api.model.Firewall;
import com.enstratus.api.model.Job;
import com.enstratus.api.model.Protocol;
import com.enstratus.api.model.Region;
import com.enstratus.api.utils.Jobs;
import com.google.common.collect.Iterables;

@Test(groups = "live", testName = "NetworkAPILiveTest")
public class NetworkAPILiveTest extends BasicEnstratusLiveTest {

    private NetworkAPI api;
    private Region region;
    private String firewallId;

    @BeforeClass
    public void beforeClass() throws Exception {
        api = enstratusContext.getNetworkAPI();
        assertNotNull(api);
        region = tryFindRegionInEUorNull();
        Assert.assertNotNull(region);
    }

    @Test
    public void listFirewalls() throws Exception {
        for (Firewall firewall : api.listFirewalls(region.getRegionId())) {
            System.out.println(firewall);
        }
    }
    
    @Test(dependsOnMethods = "listFirewalls")
    public void addFirewall() throws Exception {
        String name = "firewallIntegrationTest";
        String description = "firewall test";

        BillingCode billingCode = tryFindBillingCodeInEuOrNull();
        assertNotNull(billingCode);
        Firewall firewall = createFirewall(name, description, billingCode.getBillingCodeId(), region);
        List<Job> jobs = api.addFirewall(firewall);
        assertNotNull(jobs);
        Job job = Iterables.getOnlyElement(jobs);
        job = Jobs.waitForJob(job, enstratusContext.getAdminAPI());
        assertTrue((Jobs.isComplete(job)));
        firewallId = job.getMessage();
    }

    @Test(dependsOnMethods="addFirewall")
    public void addFirewallRule() throws Exception {
        String cidr = "157.166.224.26/32";
        String startPort = "8080";
        String endPort = "8081";
        Assert.assertNotNull(firewallId);
        try {
            api.addFirewallRule(firewallId, cidr, startPort, endPort, Direction.INGRESS, Protocol.TCP);
        } finally {
            api.deleteFirewall(firewallId, "just a test");
        }
    }
    
}
