package org.dev.paymentprocessing.infraestructure.adapter.out.persistence;

import org.dev.paymentprocessing.application.port.out.ITransactionRepository;
import org.dev.paymentprocessing.application.port.out.ITransactionTemplatePort;
import org.dev.paymentprocessing.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Slice;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TransactionPersistenceAdapterTests {

    @Mock
    private ITransactionRepository transactionRepository;

    @Mock
    private ITransactionTemplatePort transactionTemplateAdapter;

    @InjectMocks
    private TransactionPersistenceAdapter transactionPersistenceAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Save transaction successfully")
    void saveTransactionSuccessfully() {
        Transaction transaction = new Transaction(
                "626ccc13-df07-46f8-acc2-d1ed011271a1",
                "U1",
                100.0,
                "success",
                "2024-07-22T23:17:20.694Z",
                "CN"
        );
        when(transactionRepository.save(any())).thenReturn(TransactionMapper.toTransactionEntity(transaction));

        Transaction result = transactionPersistenceAdapter.save(transaction);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Save all transactions successfully")
    void saveAllTransactionsSuccessfully() {
        List<Transaction> transactions = Collections.singletonList(new Transaction(
                "626ccc13-df07-46f8-acc2-d1ed011271a1",
                "U1",
                100.0,
                "success",
                "2024-07-22T23:17:20.694Z",
                "CN"
        ));
        doNothing().when(transactionTemplateAdapter).saveAll(anyList());

        transactionPersistenceAdapter.saveAll(transactions, );
        verify(transactionTemplateAdapter, times(1)).saveAll(anyList());
    }

    @Test
    @DisplayName("Fallback save all transactions on failure")
    void fallbackSaveAllTransactionsOnFailure() {
        List<Transaction> transactions = Collections.singletonList(new Transaction(
                "626ccc13-df07-46f8-acc2-d1ed011271a1",
                "U1",
                100.0,
                "success",
                "2024-07-22T23:17:20.694Z",
                "CN"
        ));
        doThrow(new RuntimeException("Error")).when(transactionTemplateAdapter).saveAll(anyList());

        assertThrows(RuntimeException.class, () -> transactionPersistenceAdapter.saveAll(transactions, ));
    }

    @Test
    @DisplayName("Get transaction summary successfully")
    void getTransactionSummarySuccessfully() {
        TransactionSummary summary = new TransactionSummary(
                100,
                50000
        );
        when(transactionTemplateAdapter.getTransactionSummary()).thenReturn(summary);

        TotalTransactionSummary result = transactionPersistenceAdapter.getTransactionSummary();
        assertNotNull(result);
    }

    @Test
    @DisplayName("Get transaction summary by status successfully")
    void getTransactionSummaryByStatusSuccessfully() {
        TransactionSummary summary = new TransactionSummary(
                100,
                50000
        );
        when(transactionTemplateAdapter.getTransactionSummaryByStatus(anyString())).thenReturn(summary);

        TotalTransactionSummary result = transactionPersistenceAdapter.getTransactionSummaryByStatus("COMPLETED");
        assertNotNull(result);
    }

    @Test
    @DisplayName("Get transaction summary by user id successfully")
    void getTransactionSummaryByUserIdSuccessfully() {
        TransactionSummary summary = new TransactionSummary(
                100,
                50000
        );
        when(transactionTemplateAdapter.getTransactionSummaryByUserId(anyString())).thenReturn(summary);

        TotalTransactionSummary result = transactionPersistenceAdapter.getTransactionSummaryByUserId("user123");
        assertNotNull(result);
    }

    @Test
    @DisplayName("Summary transactions per minute successfully")
    void summaryTransactionsPerMinuteSuccessfully() {
        //List<TransactionPerMinuteSummaryProjection> projections = Collections.singletonList(new TransactionPerMinuteSummaryProjection());
        //when(transactionRepository.findTransactionPerMinuteSummary()).thenReturn(projections);

        //TotalTransactionPerMinuteSummary result = transactionPersistenceAdapter.summaryTransactionsPerMinute();
        //assertNotNull(result);
    }

    @Test
    @DisplayName("Find all transactions with valid parameters")
    void findAllTransactionsWithValidParameters() {
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setId("626ccc13-df07-46f8-acc2-d1ed011271a1");
        transactionEntity.setUserid("U1");
        transactionEntity.setAmount(100.0);
        transactionEntity.setStatus("success");
        transactionEntity.setTime("2024-07-22T23:17:20.694Z");
        transactionEntity.setLocation("CN");

        Slice<TransactionEntity> transactions = new PageImpl<>(Collections.singletonList(transactionEntity));
        when(transactionTemplateAdapter.findAll(any(), anyList())).thenReturn(transactions);

        Slice<Transaction> result = transactionPersistenceAdapter.findAll("success", "U1", 100.0, 1000.0, "626ccc13-df07-46f8-acc2-d1ed011271a1", "pagingState", 50);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Get next paging state successfully")
    void getNextPagingStateSuccessfully() {
        CassandraPageRequest pageRequest = mock(CassandraPageRequest.class);
        when(pageRequest.getPagingState()).thenReturn(ByteBuffer.wrap(new byte[0]));

        String result = transactionPersistenceAdapter.getNextPagingState(pageRequest);
        assertNotNull(result);
    }
}