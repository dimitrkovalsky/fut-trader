package com.liberty.rest.request;

import com.liberty.model.PlayerId;
import lombok.Data;

/**
 * @author Dmytro_Kovalskyi.
 * @since 17.06.2016.
 */
@Data
public class AutoBuyRequest {

    private PlayerId playerId;
    private Boolean enabled;
}
