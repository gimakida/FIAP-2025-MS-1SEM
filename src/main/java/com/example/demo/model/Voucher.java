package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

// @Data
// public class Voucher {
//   private Long id;

//   @NotBlank(message = "Voucher code is required")
//   private String codigo;

//   @NotBlank(message = "Voucher type is required") // PERCENTUAL ou VALOR_FIXO
//   private String tipo;

//   @Min(value = 10, message = "Percentual mínimo é 10%")
//   @Max(value = 30, message = "Percentual máximo é 30%")
//   private Double percentualDesconto; // usado se tipo = PERCENTUAL

//   @Min(value = 10, message = "Valor fixo mínimo é 10")
//   @Max(value = 100, message = "Valor fixo máximo é 100")
//   private Double valorFixoDesconto; // usado se tipo = VALOR_FIXO

//   @NotNull(message = "Data de validade é obrigatória")
//   private LocalDate dataValidade;

//   @Positive(message = "Valor mínimo de compra deve ser positivo")
//   private Double valorMinimoCompra;

//   private boolean ativo = true;
// }

@Data
public class Voucher{
  @NotBlank(message = "Código é requerido")
  @Pattern(regexp = "^[A-Z0-9]{4,10}$",message = "Código precisa ter entr 4 e 10 letras maiúsculas e números")
  private String code;

  @NotNull(message ="Tipo é requirido" )
  private String type;

  @NotNull (message = "Valor é requirido")
  @Positive (message = "Precisa ser positivo")
  private BigDecimal value;

  @NotNull(message = "Valor requirido")
  @Min (value = 0, message = "Não pode ser negativo")
  private BigDecimal minimumPurchase;

  @NotNull(message = "Valor requirido")
  @Future(message = "Expiração precisa ser futura")
  private LocalDate expirationDate;

  @NotNull (message = "Limite é requerido")
  @Min(value = 1,message = "Não pode ser ao menos 1")
  private Integer usageLimit;

  private Integer usageCount = 0; 
}

