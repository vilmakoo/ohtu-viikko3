package ohtuesimerkki;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class StatisticsTest {

    Statistics stats;
    Reader readerStub = new Reader() {

        public List<Player> getPlayers() {
            ArrayList<Player> players = new ArrayList<Player>();

            players.add(new Player("Semenko", "EDM", 4, 12));
            players.add(new Player("Lemieux", "PIT", 45, 54));
            players.add(new Player("Kurri", "EDM", 37, 53));
            players.add(new Player("Yzerman", "DET", 42, 56));
            players.add(new Player("Gretzky", "EDM", 35, 89));

            return players;
        }
    };

    public StatisticsTest() {
        stats = new Statistics(readerStub);
    }

    @Test
    public void olemassaOlevanPelaajanEtsiminenToimii() {
        assertEquals("Semenko", stats.search("Semenko").getName());
    }
    
    @Test
    public void searchPalauttaaNullJosPelaajaaEiOle() {
        assertEquals(null, stats.search("vilma"));
    }
    
    @Test
    public void metodiTeamPalauttaaOikeatPelaajat() {
        ArrayList<String> palautetutPelaajat = new ArrayList<String>();
        for (Player pelaaja : stats.team("EDM")) {
            palautetutPelaajat.add(pelaaja.getName());
        }
        
        assertTrue(palautetutPelaajat.contains("Semenko"));
        assertTrue(palautetutPelaajat.contains("Kurri"));
        assertTrue(palautetutPelaajat.contains("Gretzky"));
    }
    
    @Test
    public void kaksiOikeaaParastaPelaajaaLoytyy() {
        ArrayList<String> palautetutPelaajat = new ArrayList<String>();
        for (Player pelaaja : stats.topScorers(2)) {
            palautetutPelaajat.add(pelaaja.getName());
        }
        assertTrue(palautetutPelaajat.contains("Lemieux"));
        assertTrue(palautetutPelaajat.contains("Yzerman"));
    }
}
