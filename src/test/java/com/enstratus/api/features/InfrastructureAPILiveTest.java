package com.enstratus.api.features;

import static org.testng.Assert.assertNotNull;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.enstratus.api.model.BillingCode;
import com.enstratus.api.model.Datacenter;
import com.enstratus.api.model.Firewall;
import com.enstratus.api.model.Job;
import com.enstratus.api.model.MachineImage;
import com.enstratus.api.model.Region;
import com.enstratus.api.model.Server;
import com.enstratus.api.model.ServerProduct;
import com.enstratus.api.utils.Jobs;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

@Test(groups = "live", testName = "InfrastructureAPILiveTest")
public class InfrastructureAPILiveTest extends BasicEnstratusLiveTest {

    private InfrastructureAPI api;
    private Region region;
    private BillingCode billingCode;
    private Datacenter datacenter;
    private MachineImage machineImage;
    private Firewall firewall;

    @BeforeClass
    public void beforeClass() throws Exception {
        api = enstratusContext.getInfrastructureAPI();
        assertNotNull(api);
        region = tryFindRegionInEUorNull();
        Assert.assertNotNull(region);
        
        billingCode = tryFindBillingCodeInEuOrNull();
        Assert.assertNotNull(billingCode);

        datacenter = tryFindDatacenterInEuOrNull();
        Assert.assertNotNull(datacenter);

        machineImage = tryFindMachineImageInEuOrNull();
        Assert.assertNotNull(machineImage);
        
        firewall = tryFindFirewallInEuOrNull();
        Assert.assertNotNull(firewall);
    }

    @Test
    public void launchServer() throws Exception {
        String name = "serverTest";
        String description = "server test";       
        Server server = createServer(name, description, billingCode.getBillingCodeId(), region, datacenter, machineImage, null);
        Job job = null;
        try {
            List<Job> jobs = api.launchServer(server);
            assertNotNull(jobs);
            job = Iterables.getOnlyElement(jobs);
            job = Jobs.waitForJob(job, enstratusContext.getAdminAPI());
        } finally {
            if (Jobs.isComplete(job)) {
                String serverId = job.getMessage();
                api.deleteServer(serverId, "just a test");
            }
        }
    }

    @Test
    public void launchServerWithFirewall() throws Exception {
        String name = "serverTestWithFirewall";
        String description = "server test with firewall";
        Server server = createServer(name, description, billingCode.getBillingCodeId(), region, datacenter, machineImage, ImmutableList.of(firewall));
        Job job = null;
        try {
            List<Job> jobs = api.launchServer(server);
            assertNotNull(jobs);
            job = Iterables.getOnlyElement(jobs);
            job = Jobs.waitForJob(job, enstratusContext.getAdminAPI());
        } finally {
            if (Jobs.isComplete(job)) {
                String serverId = job.getMessage();
                api.deleteServer(serverId, "just a test");
            }
        }
    }

    @Test
    public void listMachineImages() throws Exception {
        for (MachineImage machineImage : api.listMachineImages(region.getRegionId())) {
            assertNotNull(machineImage.getMachineImageId());
        }
    }

    @Test
    public void listServerProducts() throws Exception {
        for (ServerProduct serverProduct : api.listServerProducts(region.getRegionId())) {
            assertNotNull(serverProduct.getProductId());
        }
    }

    @Test
    public void listServers() throws Exception {
        for (Server server : api.listServers(region.getRegionId())) {
            assertNotNull(server.getServerId());
        }
    }

}
