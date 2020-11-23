package com.rahul.project.gateway.enums;

import com.rahul.project.gateway.serialize.TaxStatusSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@JsonSerialize(using = TaxStatusSerializer.class)
public enum TaxType {

    FIXED("FIXED", FeeType.FIXED),
    VARIABLE("VARIABLE", FeeType.VARIABLE);

    private final String name;
    private final FeeType type;

    TaxType(final String name,
            final FeeType type) {
        this.name = name;
        this.type = type;
    }

    public static List<TaxType> getTaxStatusByTypes(
            List<FeeType> feeTypes) {

        List<TaxType> taxTypes = new ArrayList<TaxType>();

        for (TaxType taxType : TaxType
                .values()) {
            if (feeTypes
                    .contains(taxType.getType())) {
                taxTypes.add(taxType);
            }
        }

        return taxTypes;
    }

    public static List<TaxType> getTaxStatusByType(
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
