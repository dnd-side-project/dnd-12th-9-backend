package com.dnd.sbooky.core.point;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;

@Converter
public class PointPolicyConverter implements AttributeConverter<PointPolicy, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PointPolicy pointPolicy) {
        return pointPolicy != null ? pointPolicy.getId() : null;
    }

    @Override
    public PointPolicy convertToEntityAttribute(Integer aLong) {
        if (aLong == null) {
            return null;
        }

        return Arrays.stream(PointPolicy.values())
                     .filter(policy -> policy.getId().equals(aLong))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("해당하는 포인트 정책이 없습니다."));
    }
}
