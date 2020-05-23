package example.drew.carsales.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Car extends AbstractEntity {

    @OneToOne
    private CarBrand carBrand;

    @OneToOne
    private CarModel carModel;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Column
    private Long kilometers;

    @Column
    private String description;

    @Column
    private LocalDate build;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime editedAt;

    @PrePersist
    void createdAt(){
        this.createdAt = LocalDateTime.now();
        this.editedAt = LocalDateTime.now();
        this.build = LocalDate.now();
    }

}
