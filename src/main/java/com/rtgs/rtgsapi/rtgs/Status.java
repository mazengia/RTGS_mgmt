package com.rtgs.rtgsapi.rtgs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum Status {
    P("PENDING"), A("APPROVED"), R("REJECTED");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    @JsonValue
    public String getStatus() {
        return status;
    }

    @JsonCreator
    public static Status decode(final String status) {
        return Stream.of(Status.values()).filter(targetEnum -> targetEnum.status.equals(status)).findFirst().orElse(null);
    }
}
