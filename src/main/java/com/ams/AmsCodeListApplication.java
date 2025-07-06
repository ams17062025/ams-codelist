package com.ams;

import com.ams.dao.entity.CodeList;
import com.ams.dao.entity.CodeListCode;
import com.ams.dao.repo.CodeListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class AmsCodeListApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AmsCodeListApplication.class, args);
	}

	@Autowired
	private CodeListRepo repo;

	@Override
	public void run(String... args) throws Exception {
		saveUserType();
		saveItemCategory();
		saveItemName();
	}

	private void saveUserType() {
		CodeList codeList = new CodeList();
		codeList.setName("USER_TYPE");
		codeList.setDescription("User type");
		List<CodeListCode> codeListCodeList = new ArrayList<>();
		CodeListCode codeListCode = new CodeListCode();
		codeListCode.setCode("ADM");
		codeListCode.setCodeValue("Admin");
		codeListCode.setCodeList(codeList);
		codeListCodeList.add(codeListCode);

		codeListCode = new CodeListCode();
		codeListCode.setCode("DEL");
		codeListCode.setCodeValue("Delar");
		codeListCode.setCodeList(codeList);
		codeListCodeList.add(codeListCode);

		codeListCode = new CodeListCode();
		codeListCode.setCode("FAR");
		codeListCode.setCodeValue("Farmer");
		codeListCode.setCodeList(codeList);
		codeListCodeList.add(codeListCode);

		codeListCode = new CodeListCode();
		codeListCode.setCode("OTH");
		codeListCode.setCodeValue("Others");
		codeListCode.setCodeList(codeList);
		codeListCodeList.add(codeListCode);

		codeList.setCodeListCode(codeListCodeList);
		save(codeList);
	}

	private void saveItemCategory() {
		CodeList codeList = new CodeList();
		codeList.setName("ITEM_CATEGORY");
		codeList.setDescription("Item Category");
		List<CodeListCode> codeListCodeList = new ArrayList<>();
		CodeListCode codeListCode = new CodeListCode();
		codeListCode.setCode("FRT");
		codeListCode.setCodeValue("Fruits");
		codeListCode.setCodeList(codeList);
		codeListCodeList.add(codeListCode);

		codeListCode = new CodeListCode();
		codeListCode.setCode("VEG");
		codeListCode.setCodeValue("Vegetable");
		codeListCode.setCodeList(codeList);
		codeListCodeList.add(codeListCode);

		codeListCode = new CodeListCode();
		codeListCode.setCode("DRYFRT");
		codeListCode.setCodeValue("Dry Fruits");
		codeListCode.setCodeList(codeList);
		codeListCodeList.add(codeListCode);

		codeListCode = new CodeListCode();
		codeListCode.setCode("LEAF");
		codeListCode.setCodeValue("Leafs");
		codeListCode.setCodeList(codeList);
		codeListCodeList.add(codeListCode);

		codeListCode = new CodeListCode();
		codeListCode.setCode("OTH");
		codeListCode.setCodeValue("Others");
		codeListCode.setCodeList(codeList);
		codeListCodeList.add(codeListCode);

		codeList.setCodeListCode(codeListCodeList);
		save(codeList);
	}

	private void saveItemName() {
		CodeList codeList = new CodeList();
		codeList.setName("ITEM_FRUITS");
		codeList.setDescription("Item Fruits");
		List<CodeListCode> codeListCodeList = new ArrayList<>();
		CodeListCode codeListCode = new CodeListCode();
		codeListCode.setCode("APP");
		codeListCode.setCodeValue("Apple");
		codeListCode.setCodeList(codeList);
		codeListCodeList.add(codeListCode);

		codeListCode = new CodeListCode();
		codeListCode.setCode("POM");
		codeListCode.setCodeValue("Pomogranate");
		codeListCode.setCodeList(codeList);
		codeListCodeList.add(codeListCode);

		codeListCode = new CodeListCode();
		codeListCode.setCode("BANA");
		codeListCode.setCodeValue("Banana");
		codeListCode.setCodeList(codeList);
		codeListCodeList.add(codeListCode);

		codeListCode = new CodeListCode();
		codeListCode.setCode("ORG");
		codeListCode.setCodeValue("Orange");
		codeListCode.setCodeList(codeList);
		codeListCodeList.add(codeListCode);

		codeListCode = new CodeListCode();
		codeListCode.setCode("GOV");
		codeListCode.setCodeValue("Goava");
		codeListCode.setCodeList(codeList);
		codeListCodeList.add(codeListCode);

		codeListCode = new CodeListCode();
		codeListCode.setCode("OTH");
		codeListCode.setCodeValue("Others");
		codeListCode.setCodeList(codeList);
		codeListCodeList.add(codeListCode);

		codeList.setCodeListCode(codeListCodeList);
		save(codeList);
	}

	private void save(CodeList codeList) {
		try {
			repo.save(codeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
