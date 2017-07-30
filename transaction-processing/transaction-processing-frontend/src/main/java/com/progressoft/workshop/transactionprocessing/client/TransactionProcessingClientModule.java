package com.progressoft.workshop.transactionprocessing.client;

import com.google.gwt.core.client.EntryPoint;
import com.progressoft.brix.domino.api.client.ModuleConfigurator;
import com.progressoft.brix.domino.api.client.annotations.ClientModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ClientModule(name="TransactionProcessing")
public class TransactionProcessingClientModule implements EntryPoint {

	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionProcessingClientModule.class);

	public void onModuleLoad() {
		LOGGER.info("Initializing TransactionProcessing frontend module ...");
		new ModuleConfigurator().configureModule(new TransactionProcessingModuleConfiguration());
	}
}
