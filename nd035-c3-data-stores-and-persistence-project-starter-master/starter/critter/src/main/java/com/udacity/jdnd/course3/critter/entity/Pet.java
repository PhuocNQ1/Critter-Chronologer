package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.consts.PetType;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "pet")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long petId;

    @Enumerated(EnumType.STRING)
    @Column
    private PetType petType;

    @Column(nullable = false)
    private String petName;

    @Column
    private LocalDate petBirthDate;

    @Column
    private String petNotes;

    @ManyToMany(mappedBy = "pets", targetEntity = Schedule.class)
    private List<Schedule> schedules;

    @ManyToOne(targetEntity = Customer.class)
    @JoinColumn(name = "customer_id")
    private Customer customer;

}