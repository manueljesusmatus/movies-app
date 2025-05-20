package cl.dev.mmatush.moviesapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
public class MongoConfig {

    @Bean
    public MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory dbFactory,
                                                       MongoMappingContext context) {
        var mongoConverter = new MappingMongoConverter(dbFactory, context);
        mongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return mongoConverter;
    }

}
