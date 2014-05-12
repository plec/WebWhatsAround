import java.util.List;

import com.plec.whatsaround.dao.HttpPOIDao;
import com.plec.whatsaround.dao.IPoiDao;
import com.plec.whatsaround.dao.POIDao;
import com.plec.whatsaround.populate.bean.POI;

public class PoiDaoTest {
	public static void main(String[] args) {
		getPoiNearPoint();
	}
	
	public static void testPoiDao() {
		IPoiDao dao = new POIDao();
		dao.init();
		dao.getPoiNearPoint(48.833,2.333,100);
	}
	
	public static void testPoiHttpDao() {
		HttpPOIDao dao = new HttpPOIDao();
		dao.getPoiByID("MDF1");
	}
	
	public static void getPoiNearPoint() {
		HttpPOIDao dao = new HttpPOIDao();
		List<POI> pois = dao.getPoiNearPoint(48.833,2.333,100);
		System.out.println(pois.size());
	}
	
}
