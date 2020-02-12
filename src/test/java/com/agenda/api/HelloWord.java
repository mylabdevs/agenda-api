package com.agenda.api;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloWord {
	
	/**
	 * Para verificar se tudo foi configurado corretamente
	 */
	@Test
	public void testHelloWorld() {
		assertEquals(1, 1);
	}

}
