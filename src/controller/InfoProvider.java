package controller;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(targetNamespace = "http://controller/", portName = "InfoProviderPort", serviceName = "InfoProviderService")
public class InfoProvider {

	@WebMethod
	public String GetInfo(String id) {
		return "Returned ID: " + id;
	}
}
