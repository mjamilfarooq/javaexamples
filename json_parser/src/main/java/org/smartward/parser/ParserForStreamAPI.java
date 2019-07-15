package org.smartward.parser;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.smartward.people.Person;

import java.io.InputStream;
import java.util.Optional;
import java.util.function.Supplier;

public class ParserForStreamAPI implements Supplier<Optional<Person>> {
    private ObjectMapper objectMapper = new ObjectMapper();
    private JsonParser jsonParser;

    public ParserForStreamAPI(InputStream stream) throws Exception
    {
        jsonParser = new JsonFactory().createParser(stream);

        if (jsonParser.nextToken()!=JsonToken.START_ARRAY) {
            throw new JsonParseException("array expected ", jsonParser.getCurrentLocation());
        }
    }

    @Override
    public Optional<Person> get() {
        Optional<Person> person = Optional.empty();   //indicate end of stream or error;
        try {
            //consuming each object starting with "{"
            if (jsonParser.nextToken()==JsonToken.START_OBJECT)
            {
                person = Optional.of(objectMapper.readValue(jsonParser, Person.class));
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return person;
    }

}
