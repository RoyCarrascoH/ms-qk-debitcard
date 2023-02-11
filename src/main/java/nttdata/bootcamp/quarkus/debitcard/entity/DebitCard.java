package nttdata.bootcamp.quarkus.debitcard.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Cacheable
@Table(name = "debitcard")
public class DebitCard extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDebitCard;
    private String debitCardNumber;
    private Integer pin;
    private Date expirationDate;
    private String validationCode;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idBankAccount")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)//deserializa para procesarlo
    private BankAccount bankAccount;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idClient")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Client client;

}
