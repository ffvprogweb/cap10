package com.fatec.demo;

import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;

@UsePlaywright
class App2Tests {
	// Shared between all tests in this class.
	static Playwright playwright;
	static Browser browser;

	// New instance for each test method.
	BrowserContext context;
	Page page;

	@BeforeAll
	static void launchBrowser() {
		playwright = Playwright.create();
		browser = playwright.chromium().launch();
	}

	@AfterAll
	static void closeBrowser() {
		playwright.close();
	}

	@BeforeEach
	void createContextAndPage() {
		context = browser.newContext();
		page = context.newPage();
	}

	@AfterEach
	void closeContext() {
		context.close();
	}

	@Test
	void pesquisaNaFatec(Page page) {
		page.navigate("https://fatecipiranga.cps.sp.gov.br/");
		page.getByRole(AriaRole.SEARCHBOX, new Page.GetByRoleOptions().setName("O que deseja localizar?")).fill("ads");
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("")).click();
		page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Resultados encontrados para: ads")).click();
		page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("e:/evidencia/consulta_ads2.png")));
		// Asserção para verificar se o elemento com o texto esperado está visível
        PlaywrightAssertions.assertThat(page.getByRole(AriaRole.HEADING, new com.microsoft.playwright.Page.GetByRoleOptions().setName("Resultados encontrados para: ads"))).isVisible();
	}

}
