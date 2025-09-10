package com.example.demo.model;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Voucher {
  private Long id;

  @NotBlank(message = "Voucher code is required")
  private String codigo;

  @NotBlank(message = "Voucher type is required") // PERCENTUAL ou VALOR_FIXO
  private String tipo;

  @Min(value = 10, message = "Percentual mínimo é 10%")
  @Max(value = 30, message = "Percentual máximo é 30%")
  private Double percentualDesconto; // usado se tipo = PERCENTUAL

  @Min(value = 10, message = "Valor fixo mínimo é 10")
  @Max(value = 100, message = "Valor fixo máximo é 100")
  private Double valorFixoDesconto; // usado se tipo = VALOR_FIXO

  @NotNull(message = "Data de validade é obrigatória")
  private LocalDate dataValidade;

  @Positive(message = "Valor mínimo de compra deve ser positivo")
  private Double valorMinimoCompra;

  private boolean ativo = true;
}
