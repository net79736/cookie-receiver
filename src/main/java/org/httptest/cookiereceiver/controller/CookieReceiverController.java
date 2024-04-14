package org.httptest.cookiereceiver.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.httptest.cookiereceiver.config.cookie.CookieManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
public class CookieReceiverController {

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("name", "jongwook");
        return "index";
    }

    @RequestMapping(value="/network-client/valid-route/receive-cookie", method = RequestMethod.POST)
    public ResponseEntity<String> receiveCookieByValid(HttpServletRequest request, HttpServletResponse response) {
        log.info("CookieReceiverController receiveCookieByValid START");
        String rawTicket = request.getParameter("rawTicket");
        log.info("CookieCookController sendCookie senderManagerTicket: {}", rawTicket);

        // 쿠키 생성
        CookieManager.addCookie("name", rawTicket + "_is_returned_from_receiver", "localhost", "/network-client/send-cookie", request, response);
        // HttpHeaders 객체 생성
        HttpHeaders headers = new HttpHeaders();

        // 헤더 값 설정
        headers.add("Set-Cookie", "CreaterCookieReceiver");

        // ResponseEntity 객체 생성 시 헤더 값 함께 설정하여 반환
        return ResponseEntity.ok().headers(headers).body("");
    }

    @RequestMapping(value="/network-client/invalid-route/receive-cookie", method = RequestMethod.POST)
    public void receiveCookieByInvalid(HttpServletRequest request, HttpServletResponse response) {
        log.info("CookieReceiverController receiveCookieByInvalid START");
        String name = request.getParameter("name");
        log.info("CookieCookController sendCookie name: {}", name);

    }


}
