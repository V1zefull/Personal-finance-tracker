package com.v1zefull.pft.controller;

import com.v1zefull.pft.dto.category.CategoryResponse;
import com.v1zefull.pft.dto.transaction.TransactionRequest;
import com.v1zefull.pft.dto.transaction.TransactionResponse;
import com.v1zefull.pft.dto.user.UserResponse;
import com.v1zefull.pft.entity.enums.TransactionType;
import com.v1zefull.pft.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
@AutoConfigureMockMvc(addFilters = false)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransactionService transactionService;

    @ParameterizedTest(name = "Сумма {0} должна возвращать 400")
    @ValueSource(strings = {"-100", "-0.01", "0"})
    void shouldReturn400WhenAmountIsNotPositive(String amount) throws Exception {
        String requestBody = """
              {
                "amount": %s,
                "date": "2026-09-18",
                "description": "Проверка валидации",
                "categoryId": 1,
                "userId": 1
              }
              """.formatted(amount);

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.title").value("Validation failed"))
                .andExpect(jsonPath("$.errors.length()").value(1))
                .andExpect(jsonPath("$.errors[0].field").value("amount"))
                .andExpect(jsonPath("$.errors[0].message")
                        .value("Сумма должна быть больше нуля"));

        verifyNoInteractions(transactionService);
    }

    @Test
    void shouldReturn400WhenAmountIsMissing() throws Exception {
        String requestBody = """
              {
                "date": "2026-09-18",
                "description": "Проверка обязательной суммы",
                "categoryId": 1,
                "userId": 1
              }
              """;

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.title").value("Validation failed"))
                .andExpect(jsonPath("$.errors.length()").value(1))
                .andExpect(jsonPath("$.errors[0].field").value("amount"))
                .andExpect(jsonPath("$.errors[0].message")
                        .value("Укажите сумму"));

        verifyNoInteractions(transactionService);
    }

    @ParameterizedTest(name = "Обновление с суммой {0} должно возвращать 400")
    @ValueSource(strings = {"-100", "-0.01", "0"})
    void shouldReturn400WhenUpdatingWithNonPositiveAmount(String amount) throws Exception{
        String requestBody = """
                {
                    "amount": %s,
                    "date": "2026-09-21",
                    "description": "Проверка обновления",
                    "categoryId": 1,
                    "userId": 1
                }
                """.formatted(amount);

        mockMvc.perform(put("/api/transactions/{id}", 10L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.title").value("Validation failed"))
                .andExpect(jsonPath("$.instance").value("/api/transactions/10"))
                .andExpect(jsonPath("$.errors.length()").value(1))
                .andExpect(jsonPath("$.errors[0].field").value("amount"))
                .andExpect(jsonPath("$.errors[0].message").value("Сумма должна быть больше нуля"));

        verifyNoInteractions(transactionService);
    }

    @Test
    void shouldCreateTransactionWhenRequestIsValid() throws Exception {
        String requestBody = """
            {
                "amount": 100.50,
                "date": "2026-09-21",
                "description": "Покупка продуктов",
                "categoryId": 2,
                "userId": 3
            }
        """;

        CategoryResponse category = new  CategoryResponse(2L, "Продукты", TransactionType.EXPENSE);

        UserResponse user = new UserResponse(3L, "Егор", "egor@example.com");

        TransactionResponse response = new TransactionResponse(
                10L,
                new BigDecimal("100.50"),
                LocalDate.of(2026, 9, 21),
                "Покупка продуктов",
                category,
                user
        );

        when(transactionService.createTransaction(any(TransactionRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.amount").value(100.50))
                .andExpect(jsonPath("$.date").value("2026-09-21"))
                .andExpect(jsonPath("$.description").value("Покупка продуктов"))
                .andExpect(jsonPath("$.category.id").value(2))
                .andExpect(jsonPath("$.user.id").value(3L));

        ArgumentCaptor<TransactionRequest> captor = ArgumentCaptor.forClass(TransactionRequest.class);

        verify(transactionService).createTransaction(captor.capture());

        TransactionRequest capturedRequest = captor.getValue();

        assertThat(capturedRequest.getAmount()).isEqualByComparingTo("100.50");
        assertThat(capturedRequest.getDate()).isEqualTo(LocalDate.of(2026, 9, 21));
        assertThat(capturedRequest.getDescription()).isEqualTo("Покупка продуктов");
        assertThat(capturedRequest.getCategoryId()).isEqualTo(2L);
        assertThat(capturedRequest.getUserId()).isEqualTo(3L);
    }
}
