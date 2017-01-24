package com.liberty.model;

import lombok.Data;

/**
 * @author Dmytro_Kovalskyi.
 * @since 17.01.2017.
 */
@Data
public class PlayerId {
    private Long id;
    private int rareFlag;

    public String toMapKey() {
        return id + "_" + rareFlag;
    }
}
