package com.progressoft.workshop.transactionprocessing.client;

import com.google.gwtmockito.GwtMockitoTestRunner;
import com.progressoft.brix.domino.api.client.annotations.ClientModule;
import com.progressoft.brix.domino.test.api.client.DominoTestClient;
import com.progressoft.workshop.transactionprocessing.client.presenters.TransactionProcessingPresenter;
import com.progressoft.workshop.transactionprocessing.client.presenters.TransactionProcessingPresenterSpy;
import com.progressoft.workshop.transactionprocessing.client.requests.TransactionProcessingServerRequest;
import com.progressoft.workshop.transactionprocessing.client.views.FakeTransactionProcessingView;
import com.progressoft.workshop.transactionprocessing.shared.request.TransactionProcessingRequest;
import com.progressoft.workshop.transactionprocessing.shared.response.TransactionProcessingResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ClientModule(name = "TestTransactionProcessing")
@RunWith(GwtMockitoTestRunner.class)
public class TransactionProcessingClientModuleTest {

    private TransactionProcessingPresenterSpy presenterSpy;
    private FakeTransactionProcessingView fakeView;

    @Before
    public void setUp() {
        DominoTestClient.useModules(new TransactionProcessingModuleConfiguration(), new TestTransactionProcessingModuleConfiguration())
                .replacePresenter(TransactionProcessingPresenter.class, new TransactionProcessingPresenterSpy(),
                        presentable -> presenterSpy = (TransactionProcessingPresenterSpy) presentable)
                .viewOf(TransactionProcessingPresenter.class, view -> fakeView = (FakeTransactionProcessingView) view)
                .start();
    }

    @Test
    public void givenTransactionProcessingModule_whenContributingToMainExtensionPoint_thenShouldReceiveMainContext() throws Exception {
        assertNotNull(presenterSpy.getMainContext());
    }

    @Test
    public void givenTransactionProcessingClientModule_whenTransactionProcessingServerRequestIsSent_thenServerMessageShouldBeRecieved()
            throws Exception {

        new TransactionProcessingServerRequest() {
            @Override
            protected void process(TransactionProcessingPresenter presenter, TransactionProcessingRequest serverArgs, TransactionProcessingResponse response) {
                super.process(presenter, serverArgs, response);
                assertEquals("Server message", response.getServerMessage());
            }

            @Override
            public String getKey() {
                return TransactionProcessingServerRequest.class.getCanonicalName();
            }
        }.send();
    }
}
