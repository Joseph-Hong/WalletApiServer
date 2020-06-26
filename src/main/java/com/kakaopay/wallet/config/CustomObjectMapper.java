package com.kakaopay.wallet.config;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class CustomObjectMapper extends ObjectMapper {
	public CustomObjectMapper() {
		SimpleModule simpleModule = new SimpleModule();
		simpleModule.addSerializer(LocalDate.class, new LocalDateSerializer());
		simpleModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());

		registerModule(simpleModule);
	}
}

class LocalDateSerializer extends JsonSerializer<LocalDate> {
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@Override
	public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
		jsonGenerator.writeString(DATE_FORMAT.format(localDate));
	}
}

class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@Override
	public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		return LocalDate.parse(jsonParser.getText(), DATE_FORMAT);
	}
}