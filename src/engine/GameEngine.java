package ooga.engine;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ooga.data.GameData;
import ooga.exceptions.EndGameException;
import ooga.exceptions.ReflectionException;
import ooga.gamelogic.activeagentgetter.ActiveAgentGetter;
import ooga.gamelogic.activeagentgetter.AllCellsOnBoard;
import ooga.gamelogic.boundary.Boundary;
import ooga.gamelogic.destroyblocks.RemoveBlockLogic;
import ooga.gamelogic.endgame.EndGameLogic;
import ooga.gamelogic.grid.Agent;
import ooga.gamelogic.grid.Cell;
import ooga.gamelogic.grid.Grid;
import ooga.gamelogic.grid.ImmutableGrid;
import ooga.gamelogic.moves.Move;
import ooga.gamelogic.scorekeeper.Scorekeeper;
import ooga.gamelogic.spawners.Spawner;
import ooga.gamelogic.timekeeper.TimeKeeper;

/**
 * Implements the engine interface to facilitate the running of the game (implementation of engine
 * methods) and direct all the backend functionality. Implements the strategy design pattern to
 * achieve the flexibility to use the same game engine class for all the different games.
 */
public class GameEngine implements Engine {

    private static final int EMPTY = 0;
    private static final int NUM_CELLS_TO_ADD = 10;
    private static final int NUM_TIMES_TO_ADD_CELLS = 100;
    private static final int STARTING_ROW_COL = 0;
    private static final int STARTING_TIME = 12;
    private static final int TIMER_STARTING_INCREMENT = -1;
    private static final int TIMER_INCREMENT_WHEN_DESTROYED = 3;
    private static final String END_GAME_MESSAGE = "LOSE";
    private static final String RESET_DIRECTION = "None";
    private static final String DOWN_DIRECTION = "Down";
    private static final String DOWN_MOVE = "DownOneCell";
    private static final String DOWN_UNTIL_BOUNDARY = "DownUntilBoundary";
    private static final String CHEAT_CODE_METHOD_START = "cheatCode%s";
    private static final String CHEAT_CODE_MESSAGE = "cheatCode";
    private static final String ENDGAME_LOGIC = "endgame";
    private static final String DESTROY_BLOCKS_LOGIC = "destroyblocks";
    private static final String SPAWNERS_LOGIC = "spawners";
    private static final String ACTIVE_AGENT_LOGIC = "activeagentgetter";
    private static final String BOUNDARY_LOGIC = "boundary";
    private static final String GAME_LOGIC_PATH = "ooga.gamelogic.%s.%s";
    private static final String ISSUE_MESSAGE = "Issue with %s";


    private final List<Boundary> boundaryChecks = new ArrayList<>();
    private final List<RemoveBlockLogic> removeBlockChecks = new ArrayList<>();
    private Spawner spawner;
    private Agent latestAgent = null;
    private Grid myGrid;
    private List<Agent> activeAgents;
    private ActiveAgentGetter activeAgentGetter;
    private final MoveFactory moveFactory = new MoveFactory();
    private Map<String, String> controlRules;
    private boolean gravity = false;
    private boolean spawnOnTurn = false;
    private boolean spawnOnHitFloor = false;
    private boolean isPaused = false;
    private boolean endGameInterrupt = false;
    private String currentDirection;
    private Scorekeeper myScorekeeper;
    private EndGameLogic gameEnder;
    private TimeKeeper timer;
    private Boolean mouseControl;
    private Boolean stopTime = false;

    /**
     * Respond to an input key the user entered
     *
     * @param input A string representing the key a user input
     * @return returns a boolean depending on whether the gameengine responded properly
     * to the users input
     * @throws ReflectionException
     */
    @Override
    public boolean respondToUserInputKeys(String input) throws ReflectionException {
        if (!isPaused && input != null && controlRules.get(input) != null) {
            if (spawnOnTurn) {
                latestAgent = spawner.spawnNewAgent(myGrid);
            }
            currentDirection = input;
            activeAgents = activeAgentGetter.getActiveAgent(myGrid, activeAgents, latestAgent);
            moveActiveAgents(activeAgents, controlRules.get(currentDirection));
            return true;
        }
        return false;
    }

    /**
     * Responds to the user clicking on cells with the mouse, and takes the according action. In our
     * case this represents selecting a single cell and making it the sole active agent.
     *
     * @param row    - row of input
     * @param column - column of input
     * @return a boolean indicating if clicked cell was set as the new current agent
     */
    @Override
    public boolean respondToUserInputMouse(int row, int column) {
        if (mouseControl) {
            activeAgents.clear();
            Cell selectedCell = myGrid.getCell(row, column);
            if (selectedCell != null) {
                activeAgents.add(new Agent(List.of(selectedCell)));
                return true;
            }
        }
        return false;
    }

    // moves the active agents according to move specified in the string move
    private void moveActiveAgents(List<Agent> activeAgents, String move) throws ReflectionException {
        Move currentMove = moveFactory.generateMove(move, boundaryChecks);
        currentMove.moveEntity(activeAgents, myGrid);
        myGrid.updateCells();
    }

    // checks whether the agent parameter will hit a boundary in the given directions
    private boolean willHitBoundary(Agent agent, String direction) {
        for (Boundary b : boundaryChecks) {
            if (b.isHittingBoundary(agent, myGrid, direction)) {
                return true;
            }
        }
        return false;
    }

    // checks whether all active agents are hitting a boundary condition
    private boolean areAllActiveAgentsHittingBoundary(String keyPressed) {
        for (Agent agent : activeAgents) {
            if (!willHitBoundary(agent, keyPressed)) {
                return false;
            }
        }
        return true;
    }



    /**
     * Moves the game by forward by applying the game logic to determine the next game state. Updates
     * the timer, executes any cheat codes passed, applies the logic to (potentially) destroy blocks,
     * checks if the game is over, and retrieves new agents. Applies this process each time next game
     * state is called to continuously run the game.
     *
     * @param newCheatCode the cheat code passed by the user during the game
     * @return a boolean indicating if getting to the next game state has been successfully completed
     * @throws EndGameException    exception indicating that the conditions to end the game have been
     *                             met
     * @throws ReflectionException exception indicating that there has been an error in the reflection
     *                             for the game logic
     */
    @Override
    public boolean nextGameState(String newCheatCode) throws EndGameException, ReflectionException {
        if (!stopTime) {
            timer.update();
        }
        checkForAndExecuteCheatCode(newCheatCode);
        if (gravity && spawnOnHitFloor && !isPaused) {
            createFallingAgent();
        }
        List<Integer> destroyedBlocks = destroyBlocks();
        adjustTimerIfDestroyed(destroyedBlocks);
        myScorekeeper.updateScore(destroyedBlocks);
        if (gameEnder.checkConditions(latestAgent, myGrid, timer) || endGameInterrupt) {
            stopTime = true;
            throw new EndGameException(END_GAME_MESSAGE);
        }
        activeAgents = activeAgentGetter.getActiveAgent(myGrid, activeAgents, latestAgent);
        currentDirection = RESET_DIRECTION;
        return true;
    }

    // spawns a new agent and starts to let them fall down
    private void createFallingAgent() throws ReflectionException {
        if (areAllActiveAgentsHittingBoundary(DOWN_DIRECTION) || willHitBoundary(latestAgent,
                DOWN_DIRECTION)
                || activeAgents.isEmpty()) {
            latestAgent = spawner.spawnNewAgent(myGrid);
        }
        moveActiveAgents(activeAgents, DOWN_MOVE);
    }

    // checks for a cheat code passed in and run the associated command
    private void checkForAndExecuteCheatCode(String newCheatCode) throws ReflectionException {
        if (newCheatCode != null) {
            try {
                this.getClass().getMethod(String.format(CHEAT_CODE_METHOD_START, newCheatCode))
                        .invoke(this);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new ReflectionException(CHEAT_CODE_MESSAGE);
            }
        }
    }

    private void adjustTimerIfDestroyed(List<Integer> destroyedBlocks) {
        if (!destroyedBlocks.isEmpty()) {
            timer.setIncrement(TIMER_INCREMENT_WHEN_DESTROYED);
        } else {
            timer.setIncrement(TIMER_STARTING_INCREMENT);
        }
    }

    private List<Integer> destroyBlocks() throws ReflectionException {
        List<Integer> destroyedCellStates = new ArrayList<>();
        for (RemoveBlockLogic rbl : removeBlockChecks) {
            for (Cell c : myGrid.getCellsInGrid()) {
                if (rbl.removeBlock(c, myGrid, currentDirection)) {
                    destroyedCellStates.add(c.getCurrentState());
                }
                myGrid.updateCells();
            }
            if (gravity && !destroyedCellStates.isEmpty()) {
                moveActiveAgents(new AllCellsOnBoard().getActiveAgent(myGrid, activeAgents, latestAgent),
                        DOWN_UNTIL_BOUNDARY);
            }
        }

        return destroyedCellStates;
    }

    /**
     * Sets up the game with the correct game logic for the game that we want to play.
     *
     * @param grid       the grid that the game will occur on
     * @param configData the game configuration information
     * @return a boolean indicating if the game has been set up correctly
     * @throws ReflectionException exception indicating that an error has occurred in creating the
     *                             game logic
     */
    @Override
    public boolean setupGame(Grid grid, GameData configData) throws ReflectionException {
        this.myGrid = grid;
        this.controlRules = configData.getControlRules();
        this.spawnOnTurn = configData.getSpawnOnTurn();
        this.spawnOnHitFloor = configData.getSpawnOnHitFloor();
        this.timer = new TimeKeeper(STARTING_TIME, TIMER_STARTING_INCREMENT);
        this.gravity = configData.getGravity();
        this.mouseControl = configData.getMouseControl();
        try {
            initializeActiveAgentGetter(configData.getActiveAgentCreator());
            initializeScorekeeper(configData);
            initializeSpawner(configData.getSpawnLogic(), configData.getSpawnNumericalValues());
            initializeEndGame(configData.getEndGame());
            initializeRemoveBlockChecks(configData);
            initializeBoundaryConditions(configData);
        } catch (Exception e) {
            throw new ReflectionException(e.getMessage());
        }
        activeAgents = new LinkedList<>();
        return true;
    }

    private void initializeEndGame(String endGameLogic) throws ReflectionException {
        gameEnder = (EndGameLogic) initializeGameLogicNoParameters(ENDGAME_LOGIC, endGameLogic);
    }

    private void initializeBoundaryConditions(GameData metadata) throws ReflectionException {
        for (String condition : List.of(metadata.getBoundaryConditions())) {
            boundaryChecks.add(
                    (Boundary) initializeGameLogicNoParameters(BOUNDARY_LOGIC, condition));
        }
    }

    private Object initializeGameLogicNoParameters(String logicType, String chosenLogic)
            throws ReflectionException {
        try {
            return Class.forName(
                            String.format(GAME_LOGIC_PATH, logicType, chosenLogic)).getConstructor()
                    .newInstance();
        } catch (Exception e) {
            throw new ReflectionException(String.format(ISSUE_MESSAGE, logicType));
        }
    }


    private void initializeRemoveBlockChecks(GameData metadata) throws ReflectionException {
        for (String condition : List.of(metadata.getRemoveBlockConditions())) {
            removeBlockChecks.add(
                    (RemoveBlockLogic) initializeGameLogicNoParameters(DESTROY_BLOCKS_LOGIC,
                            condition));
        }
    }


    private void initializeScorekeeper(GameData metadata) {
        int startingScore = metadata.getStartingScore();
        myScorekeeper = new Scorekeeper(startingScore, metadata.getIncreaseScore());
    }

    private void initializeSpawner(String passedSpawnLogic, int[] spawnerParameters)
            throws ReflectionException {
        try {
            spawner = (Spawner) Class.forName(
                            String.format(GAME_LOGIC_PATH, SPAWNERS_LOGIC, passedSpawnLogic))
                    .getConstructor(int[].class).newInstance(spawnerParameters);
        } catch (Exception e) {
            throw new ReflectionException(String.format(ISSUE_MESSAGE, SPAWNERS_LOGIC));
        }
    }

    private void initializeActiveAgentGetter(String activeAgentLogic) throws ReflectionException {
        activeAgentGetter = (ActiveAgentGetter) initializeGameLogicNoParameters(ACTIVE_AGENT_LOGIC,
                activeAgentLogic);

    }

    @Override
    public boolean resetGame(Grid newGrid) {
        myGrid = newGrid;
        myGrid.updateCells();
        myScorekeeper.resetScore();
        timer.reset();
        stopTime = false;
        return true;
    }


    @Override
    public Boolean setState(int row, int col, int state) {
        if (row < STARTING_ROW_COL || row > myGrid.getNumberOfRows() || col < STARTING_ROW_COL || col > myGrid.getNumberOfColumns()
                || state < EMPTY) {
            return false;
        }
        myGrid.getCell(row, col).setNextState(state);
        return true;
    }

    public TimeKeeper getTimer() {
        return timer;
    }


    /**
     * Meant to change the difficult to the specified level
     *
     * @param difficultyLevel number corresponding to the level
     * @return
     */
    @Override
    public boolean adjustDifficulty(int difficultyLevel) {
        return false;
    }

    @Override
    public int getGameScore() {
        return myScorekeeper.getCurrentScore();
    }

    @Override
    public Agent getCellHint() {
        return null;
    }


    @Override
    public ImmutableGrid getImmutableGrid() {
        return myGrid;
    }


    /**
     * cheat code method for turning gravity offf
     */
    public void cheatCodeZeroGravity() {
        gravity = false;
    }

    /**
     * cheat code method for turning gravity on
     */
    public void cheatCodeRelease() {
        gravity = true;
    }

    /**
     * the cheat code for stopping time
     */
    public void cheatCodeTHEWORLD() {
        stopTime = true;
    }

    public void cheatCode5Secs() {
        stopTime = false;
    }

    /**
     * the cheat code for reseting time
     */
    public void cheatCodeHomura() {
        timer.reset();
    }

    /**
     * the cheat code for resetting the score
     */
    public void cheatCodeResetScore() {
        myScorekeeper.resetScore();
    }

    /**
     * the cheat code function for resetting the game
     */
    public void cheatCodeSquareOne() {
        resetGame(new Grid(myGrid.getNumberOfRows(), myGrid.getNumberOfColumns()));
    }

    public void cheatCodeNoLife() {
        endGameInterrupt = true;
    }


    public void cheatCodeRareCandy() {
        ArrayList<Integer> lotsOfCells = new ArrayList<>();
        for (int i = 0; i < NUM_TIMES_TO_ADD_CELLS; i++) {
            lotsOfCells.add(NUM_CELLS_TO_ADD);
        }
        myScorekeeper.updateScore(lotsOfCells);
    }


    public void cheatCodeMildCandy() {
        ArrayList<Integer> lotsOfCells = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            lotsOfCells.add(NUM_CELLS_TO_ADD);
        }
        myScorekeeper.updateScore(lotsOfCells);
    }

    public void cheatCodePause() {
        isPaused = true;
    }

    public void cheatCodePlay() {
        isPaused = false;
    }

    public int getGameTime() {
        return timer.getTime();
    }
}
