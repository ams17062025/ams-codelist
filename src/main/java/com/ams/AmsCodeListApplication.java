package com.ams;

import com.ams.dao.entity.CodeList;
import com.ams.dao.entity.CodeListCode;
import com.ams.dao.repo.CodeListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class AmsCodeListApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AmsCodeListApplication.class, args);
	}

	@Autowired
	private CodeListRepo repo;

	@Override
	public void run(String... args) throws Exception {
		for(int i=1; i<15;i++) {
			CodeList codeList = new CodeList();
			codeList.setName("Name_"+i);
			codeList.setDescription("Description of Name_"+i);
			List<CodeListCode> codeListCodeList = new ArrayList<>();
			for(int j=1;j<4;j++) {
				CodeListCode codeListCode = new CodeListCode();
				codeListCode.setCode("CODE_"+j);
				codeListCode.setCodeValue("CODE_VALUE_"+j);
				codeListCode.setCodeList(codeList);
				codeListCodeList.add(codeListCode);
			}
			codeList.setCodeListCode(codeListCodeList);
			try {
				repo.save(codeList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
