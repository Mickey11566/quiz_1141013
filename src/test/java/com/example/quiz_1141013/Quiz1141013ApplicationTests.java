package com.example.quiz_1141013;

import java.util.Scanner;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Quiz1141013ApplicationTests {

	@Test
	void contextLoads() {
		Scanner sc = new Scanner(System.in);
		int num = sc.nextInt();
		int odd = 0;
		int even = 0;
		for (int i = 0; i < num; i++) {
			int r = 0;
			r = (int) (Math.random() * 10);
			System.out.println(r);
			if (r % 2 == 0) {
				even += r;
			}
			if (r % 2 != 0) {
				odd += r;
			}
		}
		System.out.println(odd);
		System.out.println(even);
		sc.close();
	}

	@Test
	void test() {
		Scanner sc = new Scanner(System.in);
		int num = sc.nextInt();
		double price = 0;
		if (num < 120) {
			price = (num * 1.6);
		}
		if (num > 120 && num < 331) {
			price = (120 * 1.6) + ((num - 120) * 2.4);
		}
		if (num > 330) {
			price = (120 * 1.6) + ((num - 330) * 3.5);
		}
		if (num < 0) {
			System.err.println("ERROR");
		}
		System.out.println(price);
	}

}
