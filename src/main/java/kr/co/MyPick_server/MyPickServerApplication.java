package kr.co.MyPick_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class MyPickServerApplication {

	public static void main(String[] args) {
		// 로그 파일 경로 확인 및 생성
		String logPath = "/volume1/docker/MyPick/Server/logs";
		File logDir = new File(logPath);
		if (!logDir.exists()) {
			boolean created = logDir.mkdirs();
			if (created) {
				System.out.println("Log directory created at: " + logPath);
			}
		}

		SpringApplication.run(MyPickServerApplication.class, args);
	}
}
