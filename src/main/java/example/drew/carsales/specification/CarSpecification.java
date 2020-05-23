package example.drew.carsales.specification;

import example.drew.carsales.persistence.entity.Car;
import example.drew.carsales.util.HtmlSanitizerUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDate;

public class CarSpecification {

    public static Specification<Car> getCarsByBrand(String carBrand) {
        return StringUtils.isNotBlank(carBrand) ? (Specification<Car>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(
            criteriaBuilder.upper(
                    root.join("carBrand", JoinType.LEFT).getParent().get("carBrand").get("name")
            ),
            HtmlSanitizerUtil.sanitize(carBrand.toUpperCase().trim())
        ) : null;
    }

    public static Specification<Car> getCarsByModel(String carModel) {
        return StringUtils.isNotBlank(carModel) ? (Specification<Car>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.upper(
                        root.join("carModel", JoinType.LEFT).getParent().get("carModel").get("name")
                ),
                HtmlSanitizerUtil.sanitize(carModel.toUpperCase().trim())
        ) : null;
    }

    public static Specification<Car> getCarsByOwner(String username) {
        return StringUtils.isNotBlank(username) ? (Specification<Car>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.upper(
                        root.join("user", JoinType.LEFT).getParent().get("user").get("username")
                ),
                HtmlSanitizerUtil.sanitize(username.toUpperCase().trim())
        ) : null;
    }

    public static Specification<Car> getCarsByKilometers(Long kilometers) {
        return kilometers != null ? (Specification<Car>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(
                root.get("kilometers"),
                kilometers
        ) : null;
    }

    public static Specification<Car> getCarsByBuild(String build) {
        return StringUtils.isNotBlank(build) ? (Specification<Car>) (root, criteriaQuery, criteriaBuilder) -> {
            LocalDate date = LocalDate.parse(build);
            return criteriaBuilder.equal(
                    root.get("build"),
                    date
            );
        } : null;
    }

}
