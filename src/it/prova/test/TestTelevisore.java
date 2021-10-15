package it.prova.test;

import java.util.Date;
import java.util.List;

import it.prova.model.Televisore;
import it.prova.service.MyServiceFactory;
import it.prova.service.televisore.TelevisoreService;


public class TestTelevisore {
	public static void main(String[] args) {
		
		TelevisoreService televisoreService = MyServiceFactory.getTelevisoreServiceImpl();

		try {
			
			System.out.println("In tabella ci sono " + televisoreService.listAll().size() + " elementi.");

			testInserimentoNuovoTelevisore(televisoreService);
			System.out.println("In tabella ci sono " + televisoreService.listAll().size() + " elementi.");
			
			testGet(televisoreService);
			System.out.println("In tabella ci sono " + televisoreService.listAll().size() + " elementi.");

			testRimozioneTelevisore(televisoreService);
			System.out.println("In tabella ci sono " + televisoreService.listAll().size() + " elementi.");

			testFindByExample(televisoreService);
			System.out.println("In tabella ci sono " + televisoreService.listAll().size() + " elementi.");

			testUpdateTelevisore(televisoreService);
			System.out.println("In tabella ci sono " + televisoreService.listAll().size() + " elementi.");			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	private static void testInserimentoNuovoTelevisore(TelevisoreService televisoreService) throws Exception {
		System.out.println(".......testInserimentoNuovotelevisore inizio.............");
		Televisore newTelevisoreInstance = new Televisore("samsung", "aab", new Date());
		if (televisoreService.inserisciNuovo(newTelevisoreInstance) != 1)
			throw new RuntimeException("testInserimentoNuovoTelevisore fallito ");

		System.out.println("inserito nuovo record: " + newTelevisoreInstance);
		System.out.println(".......testInserimentoNuovoTelevisore fine.............");
	}
	
	private static void testGet(TelevisoreService televisoreService) throws Exception {
		System.out.println(".......testGet inizio.............");

		Televisore televisoreDaTrovareTramiteId = televisoreService.findById(1L);
		if(televisoreDaTrovareTramiteId.getId() != 1) {
			throw new RuntimeException("testUpdateTelevisore fallito ");
		}	
		System.out.println(".......testGet inizio.............");
	}

	private static void testRimozioneTelevisore(TelevisoreService televisoreService) throws Exception {
		System.out.println(".......testRimozioneTelevisore inizio.............");
		// recupero tutti gli user
		List<Televisore> interoContenutoTabella = televisoreService.listAll();
		if (interoContenutoTabella.isEmpty() || interoContenutoTabella.get(0) == null)
			throw new Exception("Non ho nulla da rimuovere");

		Long idDelPrimo = interoContenutoTabella.get(0).getId();
		// ricarico per sicurezza con l'id individuato
		Televisore toBeRemoved = televisoreService.findById(idDelPrimo);
		System.out.println("televisore alla rimozione: " + toBeRemoved);
		if (televisoreService.rimuovi(toBeRemoved) != 1)
			throw new RuntimeException("testRimozioneUser fallito ");

		System.out.println("rimosso record: " + toBeRemoved);
		System.out.println(".......testRimozioneTelevisore fine.............");
	}

	private static void testFindByExample(TelevisoreService televisoreService) throws Exception {
		System.out.println(".......testFindByExample inizio.............");
		// inserisco i dati che poi mi aspetto di ritrovare
		televisoreService.inserisciNuovo(new Televisore("LG", "ere", new Date()));
		televisoreService.inserisciNuovo(new Televisore("LG", "hdhs", new Date()));

		// preparo un example che ha come nome 'as' e ricerco
		List<Televisore> risultatifindByExample = televisoreService.findByExample(new Televisore("LG"));
		if (risultatifindByExample.size() != 2)
			throw new RuntimeException("testFindByExample fallito ");

		// se sono qui il test è ok quindi ripulisco i dati che ho inserito altrimenti
		// la prossima volta non sarebbero 2 ma 4, ecc.
		for (Televisore televisoreItem : risultatifindByExample) {
			televisoreService.rimuovi(televisoreItem);
		}

		System.out.println(".......testFindByExample fine.............");
	}

	private static void testUpdateTelevisore(TelevisoreService televisoreService) throws Exception {
		System.out.println(".......testUpdateTelevisore inizio.............");

		// inserisco i dati che poi modifico
		if (televisoreService.inserisciNuovo(new Televisore("Asus", "ab12", new Date())) != 1)
			throw new RuntimeException("testUpdateTelevisore: inserimento preliminare fallito ");

		// recupero col findbyexample e mi aspetto di trovarla
		List<Televisore> risultatifindByExample = televisoreService.findByExample(new Televisore("Asus", "ab12"));
		if (risultatifindByExample.size() != 1)
			throw new RuntimeException("testUpdateTelevisore: testFindByExample fallito ");

		Long idTelevisioneDaModificare = risultatifindByExample.get(0).getId();
		// ricarico per sicurezza con l'id individuato e gli modifico un campo
		String nuovoModello = "55POLL";
		Televisore toBeUpdated = televisoreService.findById(idTelevisioneDaModificare);
		toBeUpdated.setModello(nuovoModello);
		System.out.println("Televisore per la modifica: " + toBeUpdated);
		if (televisoreService.aggiorna(toBeUpdated) != 1)
			throw new RuntimeException("testUpdateTelevisore fallito ");

		System.out.println("aggiornato record: " + toBeUpdated);
		System.out.println(".......testUpdateTelevisore fine.............");
	}
	
	
}

