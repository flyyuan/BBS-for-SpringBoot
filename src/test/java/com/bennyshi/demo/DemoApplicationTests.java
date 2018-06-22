package com.bennyshi.demo;

import com.bennyshi.demo.Mapper.UserMapper;
import com.bennyshi.demo.bean.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Test
	public void contextLoads() {
	}

	private static final Logger log = LoggerFactory.getLogger(DemoApplicationTests.class);

	@Autowired
	private UserMapper userMapper;

	@Test
	public void test1() throws Exception {
		final List<User> u1 = userMapper.findByUsername("u1");
		log.info("[根据用户名查询] - [{}]", u1);
	}

}
