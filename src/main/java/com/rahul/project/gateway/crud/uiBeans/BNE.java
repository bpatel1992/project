package com.rahul.project.gateway.crud.uiBeans;

import java.io.Serializable;
import java.util.List;

public interface BNE extends Serializable {
    List process(List<?> list);

    default Object process(Object o) {
        return null;
    }

    default Object updateProcess(Object o) {
        return process(o);
    }
}
