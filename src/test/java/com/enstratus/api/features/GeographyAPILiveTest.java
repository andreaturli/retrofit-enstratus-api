package com.enstratus.api.features;

import static org.testng.Assert.assertNotNull;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.enstratus.api.model.Cloud;
import com.enstratus.api.model.Datacenter;
import com.enstratus.api.model.Region;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;

@Test(groups = "live", testName = "GeographyAPILiveTest")
public class GeographyAPILiveTest extends BasicEnstratusLiveTest {

    private GeographyAPI geographyAPI;

    @BeforeClass
    public void beforeClass() {
        geographyAPI = enstratusContext.getGeographyAPI();
        assertNotNull(geographyAPI);
    }

    public void testListClouds() throws Exception {
        for (Cloud cloud : geographyAPI.listClouds()) {
            assertNotNull(cloud.getCloudId());
        }
    }

    public void testListRegions() throws Exception {
        for (Region region : geographyAPI.listRegions(null, null, null)) {
            assertNotNull(region.getRegionId());
        }
    }
    
    public void testGetRegion() throws Exception {
        Region region = Iterables.tryFind(geographyAPI.listRegions(null, null, null), Predicates.notNull()).orNull();
        Assert.assertNotNull(region);
        assertNotNull(geographyAPI.getRegion(region.getRegionId(), null, null, null));
    }    

    public void testListDatacenters() throws Exception {
        Region region = Iterables.tryFind(geographyAPI.listRegions(null, null, null), Predicates.notNull()).orNull();
        Assert.assertNotNull(region);
        for (Datacenter datacenter : geographyAPI.listDatacenters(region.getRegionId())) {
            assertNotNull(datacenter.getDataCenterId());
        }
    }
    
}
