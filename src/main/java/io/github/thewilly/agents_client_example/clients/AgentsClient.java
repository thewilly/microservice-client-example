package io.github.thewilly.agents_client_example.clients;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient("AgentsClient")
public interface AgentsClient {

}
