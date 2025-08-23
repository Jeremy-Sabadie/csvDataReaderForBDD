package model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "compte", indexes = {
    @Index(name = "uk_compte_iban", columnList = "iban", unique = true)
})
public class Compte {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 34, nullable = false, unique = true)
    private String iban;

    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal balance;

    // getters/setters
    public Long getId() { return id; }
    public String getIban() { return iban; }
    public void setIban(String iban) { this.iban = iban; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
}
