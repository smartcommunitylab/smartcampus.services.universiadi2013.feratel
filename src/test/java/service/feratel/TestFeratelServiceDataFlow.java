package service.feratel;

import it.sayservice.platform.core.common.exception.ServiceException;
import it.sayservice.platform.servicebus.test.DataFlowTestHelper;
import it.sayservice.services.feratel.impl.GetFeratelCommercialeDataFlow;
import it.sayservice.services.feratel.impl.GetFeratelPubblicheDataFlow;
import it.sayservice.services.feratel.impl.GetFeratelRicettiveDataFlow;
import it.sayservice.services.feratel.impl.GetFeratelRistoroDataFlow;
import it.sayservice.services.feratel.impl.GetFeratelSportiveDataFlow;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

public class TestFeratelServiceDataFlow extends TestCase {
	
	public void testGetFeratelRicettive() throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("file", "trento");
		DataFlowTestHelper helper = new DataFlowTestHelper();
		Map<String, Object> out = helper.executeDataFlow("it.sayservice.ext.feratel", "GetFeratelRicettive", new GetFeratelRicettiveDataFlow(), params);
		System.out.println(out);
	}
	
	public void testGetFeratelPubbliche() throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("file", "fiemme");
		DataFlowTestHelper helper = new DataFlowTestHelper();
		Map<String, Object> out = helper.executeDataFlow("it.sayservice.ext.feratel", "GetFeratelPubbliche", new GetFeratelPubblicheDataFlow(), params);
		//System.out.println(out);
	}
	
	public void testGetFeratelRistoro() throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("file", "fiemme");
		DataFlowTestHelper helper = new DataFlowTestHelper();
		Map<String, Object> out = helper.executeDataFlow("it.sayservice.ext.feratel", "GetFeratelRistoro", new GetFeratelRistoroDataFlow(), params);
		//System.out.println(out);
	}
	public void testGetFeratelCommerciale() throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("file", "fiemme");
		DataFlowTestHelper helper = new DataFlowTestHelper();
		Map<String, Object> out = helper.executeDataFlow("it.sayservice.ext.feratel", "GetFeratelCommerciale", new GetFeratelCommercialeDataFlow(), params);
		//System.out.println(out);
	}
	public void testGetFeratelSportive() throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("file", "fiemme");
		DataFlowTestHelper helper = new DataFlowTestHelper();
		Map<String, Object> out = helper.executeDataFlow("it.sayservice.ext.feratel", "GetFeratelSportive", new GetFeratelSportiveDataFlow(), params);
		//System.out.println(out);
	}

}
