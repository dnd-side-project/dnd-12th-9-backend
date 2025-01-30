package com.dnd.sbooky.api.support.converter;

import com.dnd.sbooky.core.book.ReadStatus;
import org.springframework.core.convert.converter.Converter;

public class ReadStatusConverter implements Converter<String, ReadStatus> {

    @Override
    public ReadStatus convert(String source) {
        return ReadStatus.create(source.toUpperCase());
    }
}
