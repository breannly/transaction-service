package org.proj3ct.transactionservive.config;

import io.r2dbc.postgresql.codec.Json;
import io.r2dbc.spi.ConnectionFactory;
import org.proj3ct.transactionservive.entity.common.CardData;
import org.proj3ct.transactionservive.entity.common.Customer;
import org.proj3ct.transactionservive.utils.JsonUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.DialectResolver;
import org.springframework.data.r2dbc.dialect.R2dbcDialect;

import java.util.List;

@Configuration
public class R2dbcConfig {

    @Bean
    R2dbcCustomConversions r2dbcCustomConversions(ConnectionFactory factory) {
        R2dbcDialect dialect = DialectResolver.getDialect(factory);
        return R2dbcCustomConversions
                .of(
                        dialect,
                        List.of(
                                new CardDataToJsonConverter(),
                                new JsonToCardDataConverter(),
                                new CustomerToJsonConverter(),
                                new JsonToCustomerConverter()
                        )
                );
    }

    @WritingConverter
    private static class CardDataToJsonConverter implements Converter<CardData, Json> {

        @Override
        public Json convert(CardData source) {
            return Json.of(JsonUtils.writeValueAsString(source));
        }
    }

    @ReadingConverter
    private static class JsonToCardDataConverter implements Converter<Json, CardData> {

        @Override
        public CardData convert(Json source) {
            return JsonUtils.readValue(source.asString(), CardData.class);
        }
    }

    @WritingConverter
    private static class CustomerToJsonConverter implements Converter<Customer, Json> {

        @Override
        public Json convert(Customer source) {
            return Json.of(JsonUtils.writeValueAsString(source));
        }
    }

    @ReadingConverter
    private static class JsonToCustomerConverter implements Converter<Json, Customer> {

        @Override
        public Customer convert(Json source) {
            return JsonUtils.readValue(source.asString(), Customer.class);
        }
    }

}
