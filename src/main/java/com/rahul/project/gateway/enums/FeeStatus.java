package com.rahul.project.gateway.enums;

import com.rahul.project.gateway.serialize.FeeStatusSerializer;
import com.rahul.project.gateway.serialize.TaxStatusSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@JsonSerialize(using = FeeStatusSerializer.class)
public enum FeeStatus {

    FIXED("FIXED", FeeType.FIXED),
    VARIABLE("VARIABLE", FeeType.VARIABLE);

    private final String name;
    private final FeeType type;

    FeeStatus(final String name,
              final FeeType type) {
        this.name = name;
        this.type = type;
    }

    public static List<FeeStatus> getFeeStatusByTypes(
            List<FeeType> feeTypes) {

        List<FeeStatus> feeStatuses = new ArrayList<FeeStatus>();

        for (FeeStatus feeStatus : FeeStatus
                .values()) {
            if (feeTypes
                    .contains(feeStatus.getType())) {
                feeStatuses.add(feeStatus);
            }
        }

        return feeStatuses;
    }

    public static List<FeeStatus> getFeeStatusByType(
            FeeType feeType) {
        return getFeeStatusByTypes(Collections
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
