package br.edu.ies.aps8.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vehicle", schema = "general")
@SequenceGenerator(name = "VEHICLE_GENERATOR", schema = "security", sequenceName = "SEQ_VEHICLE", allocationSize = 1)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VEHICLE_GENERATOR")
    private Long id;
    private String name;
    // TODO: Possibly create an entity with these pre-defined
    /**
     * GASOLINE, DIESEL, ETHANOL
     */
    @ManyToOne
    @JoinColumn(name = "fuel_type_id", referencedColumnName = "id")
    private FuelType fuelType;
    // TODO: Maybe remove this and base the fuelConsuption on the
    /**
     * km/l
     */
    private double fuelConsuption;
    // TODO: Possibly create an entity with these pre-defined
    /**
         * SYNTHETIC, SEMI_SYNTHETIC, CONVENTIONAL
     */
    private String oilType;
    /**
     * km
     */
    private double oilChangeInterval;
    private double weight;

}
