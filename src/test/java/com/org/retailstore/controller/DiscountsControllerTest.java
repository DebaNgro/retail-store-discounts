package com.org.retailstore.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DiscountsControllerTest {

	@Autowired
	private DiscountsController controller;

	@Test
	void contextLoads() throws Exception {
		Assertions.assertThat(controller).isNotNull();
	}
}