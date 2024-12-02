package timebridge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.core.convert.converter.Converter;
import org.bson.Document;
import org.springframework.lang.NonNull;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import java.time.ZonedDateTime;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {
    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Bean
    @NonNull
    public MongoClient mongoClient() {
        return MongoClients.create(mongoUri);
    }

    @Override
    @NonNull
    protected String getDatabaseName() {
        return "timebridge";
    }

    @Override
    @NonNull
    public MongoCustomConversions customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new ZonedDateTimeToDocumentConverter());
        converters.add(new DocumentToZonedDateTimeConverter());
        return new MongoCustomConversions(converters);
    }

    public static class ZonedDateTimeToDocumentConverter implements Converter<ZonedDateTime, Document> {
        @Override
        public Document convert(@NonNull ZonedDateTime source) {
            return new Document("dateTime", source.toInstant())
                    .append("zoneId", source.getZone().getId());
        }
    }

    public static class DocumentToZonedDateTimeConverter implements Converter<Document, ZonedDateTime> {
        @Override
        public ZonedDateTime convert(@NonNull Document source) {
            Instant instant = source.getDate("dateTime").toInstant();
            ZoneId zoneId = ZoneId.of(source.getString("zoneId"));
            return ZonedDateTime.ofInstant(instant, zoneId);
        }
    }
}