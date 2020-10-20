package com.rahul.project.gateway.crud.uiBeans;

import java.io.Serializable;
import java.util.List;

public interface BNE extends Serializable {
    //want list of the object
    List process(List<?> list);

    //when you save the object
    default Object process(Object o) {
        return null;
    }

    //when you update the object
    default Object updateProcess(Object o) {
        return process(o);
    }
}
