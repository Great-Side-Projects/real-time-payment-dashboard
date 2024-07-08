package org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence;

import org.dev.paymentprocessingdashboard.application.port.out.IActionLogRepository;
import org.dev.paymentprocessingdashboard.domain.ActionLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

class ActionLogPersistenceAdapterTests {

    @Mock
    private IActionLogRepository actionLogRepository;

    @InjectMocks
    private ActionLogPersistenceAdapter actionLogPersistenceAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Save action log successfully")
    void saveActionLogSuccessfully() {
        ActionLog actionLog = new ActionLog("Process Transaction", "Transaction processed successfully");
        actionLogPersistenceAdapter.save(actionLog);
        verify(actionLogRepository, times(1)).save(any(ActionLogEntity.class));
    }

    @Test
    @DisplayName("Save action log throws exception triggers fallback")
    void saveActionLogThrowsExceptionTriggersFallback() {
        ActionLog actionLog = new ActionLog("Process Transaction", "Transaction processed successfully");
        doThrow(new RuntimeException("Database error")).when(actionLogRepository).save(any(ActionLogEntity.class));
        try {
            actionLogPersistenceAdapter.save(actionLog);
        } catch (Exception ignored) {
        }
        verify(actionLogRepository, times(1)).save(any(ActionLogEntity.class));
    }

    @Test
    @DisplayName("Save all action logs successfully")
    void saveAllActionLogsSuccessfully() {
        ActionLog actionLog = new ActionLog("Process Transaction", "Transaction processed successfully");
        List<ActionLog> actionLogs = List.of(actionLog, actionLog);
        actionLogPersistenceAdapter.saveAll(actionLogs);
        verify(actionLogRepository, times(1)).saveAll(anyList());
    }

    @Test
    @DisplayName("Save all action logs throws exception triggers fallback")
    void saveAllActionLogsThrowsExceptionTriggersFallback() {
        ActionLog actionLog = new ActionLog("Process Transaction", "Transaction processed successfully");
        List<ActionLog> actionLogs = List.of(actionLog, actionLog);
        doThrow(new RuntimeException("Database error")).when(actionLogRepository).saveAll(anyList());
        try {
            actionLogPersistenceAdapter.saveAll(actionLogs);
        } catch (Exception ignored) {
        }
        verify(actionLogRepository, times(1)).saveAll(anyList());
    }
}