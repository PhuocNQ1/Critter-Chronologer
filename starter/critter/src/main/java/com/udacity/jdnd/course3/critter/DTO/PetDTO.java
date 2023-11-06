package com.udacity.jdnd.course3.critter.DTO;

import java.time.LocalDate;

import com.udacity.jdnd.course3.critter.Constant.PetType;

/**
 * Represents the form that pet request and response data takes. Does not map to
 * the database directly.
 */
public class PetDTO {
    private long pet_id;
    private PetType petType;
    private String name;
    private long ownerId;
    private LocalDate birthDate;
    private String notes;

    public PetType getType() {
        return petType;
    }

    public void setType(PetType type) {
        this.petType = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public long getId() {
        return pet_id;
    }

    public void setId(long id) {
        this.pet_id = id;
    }
}
