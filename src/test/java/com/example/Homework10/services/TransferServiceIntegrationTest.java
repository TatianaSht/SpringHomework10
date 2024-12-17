package com.example.Homework10.services;

import com.example.Homework10.model.Account;
import com.example.Homework10.repositories.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TransferServiceIntegrationTest {

    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private TransferService transferService;

    @Test
    @DisplayName("Transfer amount crediting test")
    void testForCorrectCreditingOfTheTransferAmount() {

        // Arrange
        Account senderAccount = new Account();
        senderAccount.setId(1);
        senderAccount.setAmount(new BigDecimal(1000));
        Account receiverAccount = new Account();
        receiverAccount.setId(2);
        receiverAccount.setAmount(new BigDecimal(1000));
        when(accountRepository.findById(1L)).thenReturn(Optional.of(senderAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(receiverAccount));

        // Act
        transferService.transferMoney(1, 2, new BigDecimal(100));

        // Assert
        verify(accountRepository).changeAmount(1, new BigDecimal(900));
        verify(accountRepository).changeAmount(2, new BigDecimal(1100));
    }
}
