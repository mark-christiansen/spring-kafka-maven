package com.mycompany.kafka.streams;

import io.confluent.kafka.schemaregistry.client.MockSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.streams.StreamsConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public abstract class TopologyTest {

    protected final Properties kafkaConfig = new Properties();
    protected final Map<String, Object> schemaRegistryConfig = new HashMap<>();
    protected final Properties schemaRegistryProps = new Properties();
    protected final SchemaRegistryClient schemaRegistryClient = new MockSchemaRegistryClient();
    protected Serdes serdes;

    public void setup() {

        kafkaConfig.put(StreamsConfig.APPLICATION_ID_CONFIG, "test-app");
        kafkaConfig.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "mock:9092");

        schemaRegistryConfig.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "mock://schema-registry");
        schemaRegistryConfig.put("schema.registry.auth", "false");
        schemaRegistryConfig.put("schema.cache.capacity", 2000);
        schemaRegistryConfig.put(KafkaAvroDeserializerConfig.AUTO_REGISTER_SCHEMAS, true);

        for (String key : schemaRegistryConfig.keySet()) {
            schemaRegistryProps.put(key, schemaRegistryConfig.get(key));
        }
        serdes = new Serdes(schemaRegistryClient, schemaRegistryProps);
    }
}
