package com.dev.jorge.onlinebookstore;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.SpringApplication;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.mockStatic;

@SpringBootTest
class OnlineBookStoreApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void mainStartsSpringApplication() {
		String[] args = {"--example=value"};
		try (MockedStatic<SpringApplication> spring = mockStatic(SpringApplication.class)) {
			OnlineBookStoreApplication.main(args);
			spring.verify(() -> SpringApplication.run(OnlineBookStoreApplication.class, args));
		}
	}
}
