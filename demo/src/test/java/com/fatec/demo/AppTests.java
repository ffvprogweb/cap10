package com.fatec.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import java.nio.file.Paths;

class AppTests {
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
	//  O metodo selecionarCheckBox cria uma página HTML simples diretamente na 
	//  memória do navegador controlado pelo Playwright. Essa página contém um botão que, 
	//  ao ser clicado, modifica uma variável JavaScript chamada result.
    //  O objetivo desse trecho de código no teste é criar um ambiente isolado e rápido 
	//  para testar a interação com elementos da página (neste caso, um botão) e verificar
	//  o estado do JavaScript após essa interação, sem depender de um servidor web externo 
	//  ou de arquivos HTML separados.
	@Test
	void selecionarCheckBox() {
		page.navigate("data:text/html,<script>var result;</script><button onclick=result=\"Clicked\">Go</button>");
	    page.locator("button").click();
	    assertEquals("Clicked", page.evaluate("result"));
	}
	//O Playwright é uma biblioteca para automação de navegadores e não uma linguagem 
	//de programação funcional pura, mas permite e incentiva o uso de conceitos da programação funcional.
	//Em muitas das APIs do Playwright, é possivel passar funções como argumentos (callbacks) 
	//para serem executadas no contexto do navegador (page.evaluate()). Essa é uma característica 
	//fundamental da programação funcional, onde as funções são tratadas como qualquer outro valor 
	//e podem ser passadas como parâmetros ou retornadas de outras funções
	@Test
	  void verificaSeEstaSelecionado() {
	    page.setContent("<input id='checkbox' type='checkbox'></input>");
	    page.locator("input").check();
	    //passa uma funcao como parametro
	    assertTrue((Boolean) page.evaluate("() => window['checkbox'].checked"));
	  }
	@Test
	  void pesquisaNoWiki() {
		playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(150));
	    page.navigate("https://www.wikipedia.org/");
	    page.locator("input[name=\"search\"]").click();
	    page.locator("input[name=\"search\"]").fill("playwright");
	    page.locator("input[name=\"search\"]").press("Enter");
	    System.out.println(">>>>>>" + page.url());
	    assertEquals("https://pt.wikipedia.org/w/index.php?go=Go&search=playwright&title=Especial:Pesquisar&ns0=1", page.url());
	  }
	@Test
	  void pesquisaNaFatec() {
	    page.navigate("https://fatecipiranga.cps.sp.gov.br/");
	    page.locator("input[name=\"s\"]").fill("ADS");
	    page.locator("input[name=\"s\"]").press("Enter");
	    System.out.println(">>>>>>" + page.url());
	    page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("e:/evidencia/consulta_ads.png")));
	    assertEquals("https://fatecipiranga.cps.sp.gov.br/?s=ADS", page.url());
	  }
}
