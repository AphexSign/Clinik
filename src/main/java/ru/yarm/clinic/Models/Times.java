package ru.yarm.clinic.Models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

//Вспомогательный объект для генерации слотов-окошек приема врача
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Times {

    private LocalDate date_start;
    private Long hourBegin;
    private Long hourEnd;
    private Long interval;


}
