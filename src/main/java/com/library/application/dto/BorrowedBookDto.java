package com.library.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowedBookDto {

    private int ref_idx;
    private String name;
    private Date borrowed_date;
    private Date return_date;
    private int borrower;
}
