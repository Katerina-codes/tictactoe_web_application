package game.Players;

import fakes.FakeCommandLineUI;
import game.UI;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class PlayerFactoryTest {

    private PlayerFactory playerTypes;

    @Before
    public void setUp() {
        FakeCommandLineUI ui = new FakeCommandLineUI();
        playerTypes = new PlayerFactory(ui);
    }

    @Test
    public void createsTwoHumanPlayers() {
        List<Player> players = playerTypes.getPlayerTypes(UI.HUMAN_VS_HUMAN);

        assertTrue(players.get(0) instanceof HumanPlayer);
        assertTrue(players.get(1) instanceof HumanPlayer);
    }

    @Test
    public void createsOneHumanAndOneComputerPlayer() {
        List<Player> players = playerTypes.getPlayerTypes(UI.HUMAN_VS_COMPUTER);

        assertTrue(players.get(0) instanceof HumanPlayer);
        assertTrue(players.get(1) instanceof Computer);
    }

    @Test
    public void createsComputerPlayerAsPlayerOne() {
        List<Player> players = playerTypes.getPlayerTypes(UI.COMPUTER_VS_HUMAN);

        assertTrue(players.get(0) instanceof Computer);
        assertTrue(players.get(1) instanceof HumanPlayer);
    }

    @Test
    public void createsTwoComputerPlayers() {
        List<Player> players = playerTypes.getPlayerTypes(UI.COMPUTER_VS_COMPUTER);

        assertTrue(players.get(0) instanceof Computer);
        assertTrue(players.get(1) instanceof Computer);
    }

    @Test
    public void createsOneHumanAndOneUnbeatable() {
        List<Player> players = playerTypes.getPlayerTypes(UI.HUMAN_VS_UNBEATABLE_PLAYER);

        assertTrue(players.get(0) instanceof HumanPlayer);
        assertTrue(players.get(1) instanceof UnbeatableComputer);
    }

    @Test
    public void unbeatableComputerAsPlayerOne() {
        List<Player> players = playerTypes.getPlayerTypes(UI.UNBEATABLE_PLAYER_VS_HUMAN);

        assertTrue(players.get(0) instanceof UnbeatableComputer);
        assertTrue(players.get(1) instanceof HumanPlayer);
    }

    @Test
    public void createsTwoUnbeatableComputerPlayers() {
        List<Player> players = playerTypes.getPlayerTypes(UI.UNBEATABLE_PLAYER_VS_UNBEATABLE_PLAYER);

        assertTrue(players.get(0) instanceof UnbeatableComputer);
        assertTrue(players.get(1) instanceof UnbeatableComputer);
    }

    @Test
    public void createsTwoWebAppPlayers() {
        List<Player> players = playerTypes.getPlayerTypes(UI.WEB_PLAYER_VS_WEB_PLAYER);

        assertTrue(players.get(0) instanceof WebApplicationPlayer);
        assertTrue(players.get(1) instanceof WebApplicationPlayer);
    }
}
