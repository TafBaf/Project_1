package controller;

import javax.jws.WebService;

@WebService(targetNamespace = "http://controller/", portName = "InfoProviderPort", serviceName = "InfoProviderService")
public class InfoProvider {

	
	public String GetInfo(String id) {
		return "Returned ID: " + id;
	}
}
