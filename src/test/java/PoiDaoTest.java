import com.plec.whatsaround.dao.IPoiDao;
import com.plec.whatsaround.dao.POIDao;

public class PoiDaoTest {
	public static void main(String[] args) {
		testPoiDao();
	}
	
	public static void testPoiDao() {
		IPoiDao dao = new POIDao();
		dao.getPoiNearPoint(48.833,2.333,100);
	}
}
