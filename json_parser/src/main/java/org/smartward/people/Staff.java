package org.smartward.people;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Nurse.class, name = "Nurse"),
        @JsonSubTypes.Type(value = Doctor.class, name = "Doctor")
})

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public abstract class Staff extends Person {

    @JsonProperty(value = "staff_uuid")
    private String staffUuid;

}
