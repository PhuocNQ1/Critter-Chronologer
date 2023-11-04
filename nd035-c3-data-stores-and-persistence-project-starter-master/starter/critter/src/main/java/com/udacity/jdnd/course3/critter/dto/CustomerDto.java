package com.udacity.jdnd.course3.critter.dto;

import lombok.Data;

import java.util.List;

/**
 * Represents the form that customer request and response data takes. Does not map
 * to the database directly.
 */
@Data
public class CustomerDto {
    private long customerId;
    private String customerName;
    private String customerPhoneNumber;
    private String customerNotes;
    private List<Long> petIds;
}
