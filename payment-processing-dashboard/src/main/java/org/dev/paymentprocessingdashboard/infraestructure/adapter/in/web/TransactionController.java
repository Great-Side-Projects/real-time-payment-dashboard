package org.dev.paymentprocessingdashboard.infraestructure.adapter.in.web;


import org.dev.paymentprocessingdashboard.application.port.in.ITransactionServicePort;
import org.dev.paymentprocessingdashboard.common.WebAdapter;
import org.dev.paymentprocessingdashboard.domain.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final ITransactionServicePort transactionService;

    public TransactionController(ITransactionServicePort transactionService) {
        this.transactionService = transactionService;
    }


    @GetMapping("/filter")
    public Page<Transaction> filterTransactions(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) Double minAmount,
            @RequestParam(required = false) Double maxAmount,
            @RequestParam(required = false) String transactionId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return transactionService.filterTransactions(status, userId, minAmount, maxAmount, transactionId, page, size);
    }

}
