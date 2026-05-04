package com.gnas.starter.paymentservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "ORDER_PAYMENT")
@Table(name = "order_payment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderPayment {
    @EmbeddedId
    private OrderPaymentPrimaryKey orderPaymentPrimaryKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderPaymentStatus status;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "products", columnDefinition = "jsonb")
    private List<String> products;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
