package example.drew.carsales.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Car extends AbstractEntity {

    @Column
    private String carBrand;

    @Column
    private String carModel;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Column
    private Long kilometers;

    @Column
    private String description;

    @Column
    private LocalDateTime build;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime editedAt;

    @PrePersist
    void createdAt(){
        this.createdAt = LocalDateTime.now();
        this.editedAt = LocalDateTime.now();
    }

}
