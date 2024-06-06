package br.edu.ies.aps8.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fuel_type", schema = "general")
@SequenceGenerator(name = "FUEL_TYPE_GENERATOR", schema = "security", sequenceName = "SEQ_FUEL_TYPE", allocationSize = 1)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FuelType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FUEL_TYPE_GENERATOR")
    private Long id;
    private String name;
    private double emissionFactor;
    private EmissionFactorUnit emissionFactorUnit;
    private Unit unit;
}
