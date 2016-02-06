package com.denis.feed.Utils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Instant;
import java.util.Date;

/**
 * Created by denis on 5/10/15
 */
@Converter(autoApply = true)
public class InstantConverter implements AttributeConverter<Instant, Date> {


    @Override
    public Date convertToDatabaseColumn(Instant attribute) {
        return Date.from(attribute);
    }

    @Override
    public Instant convertToEntityAttribute(Date dbData) {
        return dbData.toInstant();
    }
}