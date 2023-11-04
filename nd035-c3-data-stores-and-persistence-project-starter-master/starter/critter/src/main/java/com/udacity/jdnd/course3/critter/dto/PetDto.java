package com.udacity.jdnd.course3.critter.dto;

import com.udacity.jdnd.course3.critter.consts.PetType;
import lombok.Data;

import java.time.LocalDate;

/**
 * Represents the form that pet request and response data takes. Does not map
 * to the database directly.
 */
@Data
public class PetDto {
    private long petId;
    private PetType petType;
    private String petName;
    private long ownerId;
    private LocalDate petBirthDate;
    private String petNotes;
}
