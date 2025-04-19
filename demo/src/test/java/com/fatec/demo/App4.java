package com.fatec.demo;

import org.junit.jupiter.api.Test;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;

@UsePlaywright
class App4 {
	// Shared between all tests in this class.
	static Playwright playwright;
	static Browser browser;

	@Test
	void ct01_cadastrar_cliente_com_api_indisponivel(Page page) {
		page.navigate("http://localhost:5173/");
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Menu Cliente")).click();
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Cadastrar")).click();
		page.getByTestId("cpf").click();
		page.getByTestId("cpf").fill("111111");
		page.getByTestId("nome").click();
		page.getByTestId("nome").fill("Jose");
		page.getByTestId("cep").click();
		page.getByTestId("cep").fill("111111");
		page.getByTestId("email").click();
		page.getByTestId("email").fill("jose@gmail.com");
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Confirmar")).click();
		PlaywrightAssertions.assertThat(page.getByTestId("mensagem")).hasText("Erro componente cadastrar view: Failed to fetch");
	}
}
