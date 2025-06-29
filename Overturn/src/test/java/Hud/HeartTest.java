package Hud;

import HUD.Heart;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HeartTest {

    @Test
    void testHeartSpritesLoaded() {
        Heart heart = new Heart();

        assertNotNull(heart.getSpritesArr());
        assertEquals(3, heart.getSpritesArr().length);

        for (int i = 0; i < 3; i++) {
            assertNotNull(heart.getSpritesArr()[i]);
        }
    }
}
