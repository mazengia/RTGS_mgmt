package com.rtgs.rtgsapi.converters;

import com.rtgs.rtgsapi.rtgs.Feedbacks;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class FeedbacksConverter extends BaseJsonConverter implements AttributeConverter<Feedbacks, String> {
    @Override
    public String convertToDatabaseColumn(Feedbacks feedbacks) {
        if (feedbacks != null && feedbacks.feedbacks() != null)
            try {
                return getMapper().writeValueAsString(feedbacks);
            } catch (Exception e) {
                e.printStackTrace();
            }
        return null;
    }

    @Override
    public Feedbacks convertToEntityAttribute(String s) {
        if(s != null && !s.isBlank())
            try {
                return getMapper().readValue(s, Feedbacks.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        return null;
    }
}
