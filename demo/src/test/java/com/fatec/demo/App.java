package com.fatec.demo;

import com.microsoft.playwright.*;

public class App {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
        	playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(150));
            Browser browser = playwright.chromium().launch();
            Page page = browser.newPage();
            page.navigate("https://playwright.dev");
            System.out.println(">>>>>>" + page.title());
        }
    }
}