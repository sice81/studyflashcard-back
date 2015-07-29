package junit.com.genius.flashcard.api.auth.facebook;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import com.genius.flashcard.Application;
import com.genius.flashcard.api.v1.cardpacks.dao.StudyActLogStatisticsDao;
import com.genius.flashcard.api.v1.cardpacks.dto.StudyActLogStatistics;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = Application.class)   // 2
//@IntegrationTest("server.port:0")   // 4
public class TestDao {
	StudyActLogStatisticsDao dao;

	@Before
	public void init() throws NoSuchAlgorithmException, NoSuchProviderException {
		ApplicationContext context = SpringApplication.run(Application.class, new String[]{});
		dao = (StudyActLogStatisticsDao) context.getBean(StudyActLogStatisticsDao.class);
//		context.getBean
	}

	@Test
	public void test() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date startDate = sdf.parse("20100720"), endDate = sdf.parse("20190730");
		List<StudyActLogStatistics> r = dao.findDays("fb-974273282604443", startDate, endDate);

		r = null;
	}
}
