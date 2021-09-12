package ru.zotov.carracing.store.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.zotov.carracing.store.config.FeignConfig;
import ru.zotov.carracing.store.dto.RequestPaymentCheckedDto;
import ru.zotov.carracing.store.dto.ResponsePaymentCheckedDto;
import ru.zotov.carracing.store.dto.ResponsePaymentCheckedIntegrationDto;

/**
 * @author Created by ZotovES on 12.09.2021
 */
@FeignClient(name = "gogleStoreClient", url = "${google.store.url}", configuration = FeignConfig.class)
public interface GoogleStoreIntegration {
    @PostMapping("/check")
    ResponsePaymentCheckedIntegrationDto checkPayment(@RequestBody RequestPaymentCheckedDto requestPaymentChecked);
}
