package ConfrontoSpeedVento.testStats;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ConfrontoSpeedVento.exceptions.cityException;
import com.ConfrontoSpeedVento.stats_filters.cityStats;

public class cityStatsTest {
	private cityStats stats;
	private Vector<Double> storageSpeed;


	
	@BeforeEach
	void setUp() throws Exception {
		stats = new cityStats();
		storageSpeed=stats.setStorageSpeed("Roma", "2021-12-20 21:00:00", "2021-12-21 00:00:00");
		}

	/**
     * Serve per distruggere ciò che è stato inizializzato dal metodo setUp.
     * throws java.lang.Exception
     */
	
	@AfterEach
	void tearDown() throws Exception {
	}
	

	@Test
    @DisplayName("Statistiche corrette")
	void stat() {
		//assertEquals(1.3, stats.minCalculator(storageSpeed)); //in questo caso il test termina con failures
		assertEquals(0.89, stats.minCalculator(storageSpeed));
		assertEquals(4.12, stats.maxCalculator(storageSpeed));
		assertEquals(2.31, stats.averageCalculator(storageSpeed));
		assertEquals(1.99, stats.varianceCalculator(storageSpeed));
	}
	
	@Test
    @DisplayName("Test eccezioni")
	void eccezioni() {
	
		assertThrows(cityException.class, ()->stats.setStorageSpeed("Parigi", "2021-12-20 21:00:00", "2021-12-21 00:00:00"));
	}
}
