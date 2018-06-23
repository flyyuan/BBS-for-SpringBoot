package com.bennyshi.demo;

import com.bennyshi.demo.dao.UserDao;
import com.bennyshi.demo.bean.User;
import com.bennyshi.demo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Test
	public void contextLoads() {
	}

	private static final Logger log = LoggerFactory.getLogger(DemoApplicationTests.class);

	@Autowired
	private UserDao userDao;
	@Autowired
	private UserService userService;

	@Test
	public void test1() throws Exception {
 		Boolean insert = userService.insertByUser("222", "2223", "0", new Date(), new Date(), "https://pic2.zhimg.com/v2-1e02c1531c33f9460ae82eb88a999cdd_r.jpg");
		log.info(String.valueOf(insert));

	}

	@Test
	public void test12() throws Exception {
		List<User> is = userService.findByUsername("1353372");
		log.info(String.valueOf(is));

	}

}
