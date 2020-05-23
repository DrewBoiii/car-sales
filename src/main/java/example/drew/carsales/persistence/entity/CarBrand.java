package example.drew.carsales.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CarBrand extends AbstractEntity {

    @Column
    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<CarModel> carModels;

}
