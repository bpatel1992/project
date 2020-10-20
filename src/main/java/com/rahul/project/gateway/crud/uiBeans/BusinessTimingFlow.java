package com.rahul.project.gateway.crud.uiBeans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessTimingFlow {
    private String days;//": "Monday - Saturday",
    private String[] time;/*": [
        "10:00 AM - 01:00 PM",
                "06:00 PM - 09:00 PM"
    ]*/

}
