package org.smartward.people;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "class")
@JsonSubTypes({
        @Type(value = Patient.class, name = "Patient"),
        @Type(value = Staff.class, name = "Doctor"),
        @Type(value = Staff.class, name = "Nurse")
})

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Person  {

    @JsonProperty(value="first_name")
    private String firstName;

    @JsonProperty(value="family_name")
    private String familyName;

}
