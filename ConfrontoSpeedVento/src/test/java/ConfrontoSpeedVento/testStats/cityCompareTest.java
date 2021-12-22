package ConfrontoSpeedVento.testStats;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Vector;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ConfrontoSpeedVento.stats_filters.cityStats;
import com.ConfrontoSpeedVento.stats_filters.statsCompare;

public class cityCompareTest {

	private statsCompare compare;


	@BeforeEach
	void setUp() throws Exception {
		compare = new statsCompare("Roma", "Milano",  "2021-12-20 21:00:00", "2021-12-21 00:00:00");
	}

	/**
	 * Serve per distruggere ciò che è stato inizializzato dal metodo setUp.
	 * throws java.lang.Exception
	 */

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("Statistiche comparate corrette")
	void stat() {
		//assertEquals(134.9, compare.maxCompare()); //in questo caso il test termina con failures
		assertEquals(167.53, compare.maxCompare());
		assertEquals(74.5, compare.minCompare());
		assertEquals(126.47, compare.averageCompare());
		assertEquals(1065.71, compare.varianceCompare());
	}

}
