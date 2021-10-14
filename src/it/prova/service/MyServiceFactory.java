package it.prova.service;

import it.prova.dao.televisore.TelevisoreDAOImpl;
import it.prova.service.televisore.TelevisoreService;
import it.prova.service.televisore.TelevisoreServiceImpl;

public class MyServiceFactory {
	
	public static TelevisoreService getTelevisoreServiceImpl() {
		TelevisoreService televisoreService = new TelevisoreServiceImpl();
		televisoreService.setTelevisoreDao(new TelevisoreDAOImpl());
		return televisoreService;
	}

}
