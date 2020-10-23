package com.rahul.project.gateway.enums;

import com.rahul.project.gateway.model.Fee;
import com.rahul.project.gateway.serialize.TaxStatusSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@JsonSerialize(using = TaxStatusSerializer.class)
public enum TaxStatus {

    FIXED("FIXED", FeeType.FIXED),
    VARIABLE("VARIABLE", FeeType.VARIABLE);

    private final String name;
    private final FeeType type;

    TaxStatus(final String name,
              final FeeType type) {
        this.name = name;
        this.type = type;
    }

    public static List<TaxStatus> getTaxStatusByTypes(
            List<FeeType> feeTypes) {

        List<TaxStatus> taxStatuses = new ArrayList<TaxStatus>();

        for (TaxStatus taxStatus : TaxStatus
                .values()) {
            if (feeTypes
                    .contains(taxStatus.getType())) {
                taxStatuses.add(taxStatus);
            }
        }

        return taxStatuses;
    }

    public static List<TaxStatus> getTaxStatusByType(
            FeeType feeType) {
        return getTaxStatusByTypes(Collections
                .singletonList(feeType));
    }

    public String getName() {
        return this.name;
    }

    public FeeType getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return name;
    }

}
