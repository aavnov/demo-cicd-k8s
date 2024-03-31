package com.jeroenreijn.demomicrometerprometheusgrafana;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class HelloWorldController {

	static final int max = 10;
	static final int min = 1;

	@Autowired
	OrderService orderService;

	@GetMapping("/")
	public ResponseEntity<Message> index() throws Exception {

		int randomWintNextIntWithinARange = new Random().nextInt(max - min) + min;

		Message helloWorld = new Message();

		switch (randomWintNextIntWithinARange) {
		case 1:
		case 6:
		case 9:
			log.info("Hello World calling");
			helloWorld.setText("Hello World calling");
			break;
		case 2:
		case 7:
			log.warn("a long run process called...");
			helloWorld.setText("a long run process called...");
			Thread.sleep(5 * 1000);
			break;
		case 3:
		case 8:
			helloWorld.setText("Looking into a Message object");
			log.debug("Looking into a Message object {}", helloWorld);
			break;
		case 4:
			helloWorld.setText("Ops...something did wrong");
			throw new Exception("Ops...something did wrong");
		case 5:
		case 10:
			helloWorld.setText("You've got an order");
			log.info("You've got an order", helloWorld);
			orderService.orderPurchase();
			break;

		}
		return ResponseEntity.ok(helloWorld);

	}

	class Message {
		private String text;

		public Message() {
		};

		public Message(String text) {
			this.text = text;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
	}
}
