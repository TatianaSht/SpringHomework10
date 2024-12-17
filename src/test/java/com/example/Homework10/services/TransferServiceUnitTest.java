package com.example.Homework10.services;

import com.example.Homework10.exceptions.AccountNotFoundException;
import com.example.Homework10.model.Account;
import com.example.Homework10.repositories.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TransferServiceUnitTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransferService transferService;

    @Test
    @DisplayName("Test of correct change of balance of accounts of sender and recipient.")
    public void recipientAccountExistsTest() {

        // Arrange
        Account senderAccount = new Account();
        senderAccount.setId(1);
        senderAccount.setAmount(new BigDecimal(1000));

        Account receiverAccount = new Account();
        receiverAccount.setId(2);
        receiverAccount.setAmount(new BigDecimal(1000));

        given(accountRepository.findById(senderAccount.getId())).willReturn(Optional.of(senderAccount));
        given(accountRepository.findById(receiverAccount.getId())).willReturn(Optional.of(receiverAccount));

        // Act
        transferService.transferMoney(1, 2, new BigDecimal(100));

        // Assert
        verify(accountRepository).changeAmount(1, new BigDecimal(900));
        verify(accountRepository).changeAmount(2, new BigDecimal(1100));
    }


    @Test
    @DisplayName("Test for no change to sender account if recipient account not found.")
    public void recipientAccountNotExistsTest() {

        // Arrange
        Account senderAccount = new Account();
        senderAccount.setId(1);
        senderAccount.setAmount(new BigDecimal(1000));
        given(accountRepository.findById(1L)).willReturn(Optional.of(senderAccount));
        given(accountRepository.findById(2L)).willReturn(Optional.empty());

        // Act
        assertThrows(AccountNotFoundException.class,
                () -> transferService.transferMoney(1, 2, new BigDecimal(100))
        );

        // Assert
        verify(accountRepository, never()).changeAmount(anyLong(), any());
    }
}
