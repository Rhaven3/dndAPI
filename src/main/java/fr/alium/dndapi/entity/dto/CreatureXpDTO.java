package fr.alium.dndapi.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatureXpDTO {
    private Long id;
    private Integer cr;

    public Integer getXp() {
        int[] xpTable = {
                0, 25, 50, 100,
                200, 450, 700, 1100, 1800, 2300, 2900, 3900, 5000, 5900,
                7200, 8400, 10000, 11500, 13000, 15000, 18000, 20000, 22000, 25000,
                33000, 41000, 5000, 62000, 75000, 90000, 105000, 120000, 135000, 155000
        };
        return xpTable[cr];
    }
}
