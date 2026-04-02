package com.nhnacademy.core.price.service.impl;

import com.nhnacademy.core.account.service.AuthenticationService;
import com.nhnacademy.core.exception.TariffNotFoundException;
import com.nhnacademy.core.formatter.OutPutFormatter;
import com.nhnacademy.core.price.dto.Price;
import com.nhnacademy.core.price.repository.PriceRepository;
import com.nhnacademy.core.price.service.PriceService;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PriceServiceImpl implements PriceService {
    private final PriceRepository priceRepository;
    private final AuthenticationService authenticationService;
    private final OutPutFormatter outPutFormatter;

    private void checkLogin() {
        authenticationService.getCurrentAccount();
    }

    @Override
    public List<String> sectors(String city) {
        checkLogin();
        if(Objects.isNull(city)) {
            throw new IllegalArgumentException("city는 필수 값 입니다.");
        }
        return priceRepository.sectors(city);
    }

    @Override
    public String billTotal(String city, String sector, int usage) {
        checkLogin();
        Price price = priceRepository.price(city, sector);

        if(Objects.isNull(price)) {
            throw new TariffNotFoundException(city, sector);
        }

        return outPutFormatter.format(price, usage);
    }

    @Override
    public Price price(String city, String sector) {
        checkLogin();

        Price price = priceRepository.price(city, sector);

        if(Objects.isNull(price)) {
            throw new TariffNotFoundException(city, sector);
        }
        return price;
    }

    @Override
    public List<String> cities() {
        checkLogin();
        return priceRepository.cities();
    }
}
