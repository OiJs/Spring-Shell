package com.nhnacademy.core.shell;

import com.nhnacademy.core.account.dto.Account;
import com.nhnacademy.core.account.service.AuthenticationService;
import com.nhnacademy.core.price.dto.Price;
import com.nhnacademy.core.price.service.PriceService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@Slf4j
@RequiredArgsConstructor
@ShellComponent
public class MyCommands {
    private final AuthenticationService authenticationService;
    private final PriceService priceService;


    @ShellMethod(key = "login", value = "사용자 로그인 - login [id] [password]")
    public String login(Long id ,String pwd) {
        if(authenticationService.isLoggedIn()) {
            return String.format("이미 %s님으로 로그인 되어있습니다.", authenticationService.getCurrentAccount().getName());
        }
        Account account = authenticationService.login(id, pwd);

        return String.format("Account(id = %d, name = %s, name = %s)", account.getId(), account.getPwd(), account.getName());
    }

    @ShellMethod(key = "logout",value = "사용자 로그아웃")
    public String logout(){
        authenticationService.logout();
        return "good bye";
    }

    @ShellMethod(key = "current-user", value = "현재 사용자")
    public String currentUser() {
        Account account = authenticationService.getCurrentAccount();

        return String.format("Account(id = %d, name = %s, name = %s)", account.getId(), account.getPwd(), account.getName());
    }

    @ShellMethod(key = "city", value = "도시 목록")
    public String city(){
        List<String> cities = priceService.cities();
        return cities.toString();
    }

    @ShellMethod(key = "bill-total", value = "금액 계산")
    public String billTotal(String city, String sector, int usage) {
        return priceService.billTotal(city, sector, usage);
    }

    @ShellMethod(key = "sector")
    public String sector(String city) {
        List<String> sectorList =  priceService.sectors(city);

        if (sectorList.isEmpty()) {
            return String.format("해당 지자체(%s)의 업종 정보를 찾을 수 없습니다.", city);
        }
        return sectorList.toString();
    }

    @ShellMethod(key = "price")
    public String price(String city, String sector) {
        Price price = priceService.price(city, sector);

        return String.format("Price(id=%d, city=%s, sector=%s, unitPrice=%d", price.getSequence(), price.getCity(), price.getSector(), price.getUnitPrice());
    }
}
