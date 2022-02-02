package code.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.game.game.Game;

import code.Q_learning.Alex_Clem;

public class BotScreen implements Screen {

    private static Game myGame;
    private Stage stage;

    // Instances for the background and title images + textures
    private Image background;
    private Texture bgTexture;
    private Image gameModeImage;
    private Texture gameModeTexture;

    // Package with the design used for the buttons
    private Skin skin;

    // Instances for the different game modes buttons
    private TextButton agentButton;
    private TextButton qAgentButton;
    private TextButton aStarButton;

    // Instance variable for the chosen solver
    private static String botName;

    public static Alex_Clem lets_Go_Baby;

    public BotScreen(final Game game, final GameMode gameMode) {

        this.myGame = game;
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        bgTexture = new Texture(Gdx.files.internal("IntroBackground.png"));
        bgTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        background = new Image(bgTexture);
        background.setPosition(0, 0);
        background.setSize(com.game.game.Game.WIDTH, com.game.game.Game.HEIGHT);
        stage.addActor(background);

        gameModeTexture = new Texture(Gdx.files.internal("Solver.png"));
        gameModeTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        gameModeImage = new Image(gameModeTexture);
        gameModeImage.setPosition(100, 420);
        gameModeImage.setSize(400, 60);
        stage.addActor(gameModeImage);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        agentButton = new TextButton("Q-Bot", skin);
        agentButton.setPosition(100, 300);
        agentButton.setSize(200, 60);
        stage.addActor(agentButton);

        // qAgentButton = new TextButton("Q Agent", skin);
        // qAgentButton.setPosition(400, 300);
        // qAgentButton.setSize(200, 60);
        // stage.addActor(qAgentButton);

        // aStarButton = new TextButton("A* bot", skin);
        // aStarButton.setPosition(100, 200);
        // aStarButton.setSize(200, 60);
        // stage.addActor(aStarButton);

        class agentListener extends ChangeListener {

            private Game game;
            private Screen screen;

            private agentListener(final Game game, Screen screen) {

                this.game = game;
                this.screen = screen;
            }

            @Override
            public void changed(ChangeEvent event, Actor actor) {

                this.game.setScreen(new PuttingGameScreen(myGame, new GameMode(gameMode.gameName)));
                this.screen.dispose();
                botName = "agent";
            }
        }
        agentButton.addListener(new agentListener(game, this));

        class qAgentListener extends ChangeListener {

            private Game game;
            private Screen screen;

            private qAgentListener(final Game game, Screen screen) {

                this.game = game;
                this.screen = screen;
            }

            @Override
            public void changed(ChangeEvent event, Actor actor) {

                PuttingGameScreen scrn = new PuttingGameScreen(2, 2);
                this.game.setScreen(scrn);
                botName = "Q_agent";
                this.screen.dispose();
                botName = "Q_agent";

                lets_Go_Baby = new Alex_Clem(scrn.getBallPositionX(), scrn.getBallPositionZ(), scrn.getFlagPositionX(), scrn.getFlagPositionZ());

                System.out.println("lets_Go_Baby CREATED");
            }


        }
        // qAgentButton.addListener(new qAgentListener(game, this));

        class aStarListener extends ChangeListener {

            private Game game;
            private Screen screen;

            private aStarListener(final Game game, Screen screen) {

                this.game = game;
                this.screen = screen;
            }

            @Override
            public void changed(ChangeEvent event, Actor actor) {

                this.game.setScreen(new PuttingGameScreen(myGame, new GameMode(gameMode.gameName)));
                this.screen.dispose();
                botName = "aStar";
            }
        }
        // aStarButton.addListener(new aStarListener(game, this));
    }

    /*
    public static void startTraining() {


        myGame.setScreen(new PuttingGameScreen(2,4));

        if (PuttingGameScreen.finishAgent)
        try {
            new Alex_Clem(PuttingGameScreen.getStartingPositionX(), PuttingGameScreen.getStartingPositionZ(), PuttingGameScreen.getFlagPositionX(), PuttingGameScreen.getFlagPositionZ());
        } catch (ExceptionHandeling exceptionHandeling) {
            exceptionHandeling.printStackTrace();
        }
    }
    */

/*
    public static boolean displayGUI(float x, float y) {

        PuttingGameScreen gameScreen = new PuttingGameScreen(x,y);

        myGame.setScreen(gameScreen);

        return gameScreen.getFinishAgent();
    }
*/

    public static String getBotName() {
        return botName;
    }

    public static void setBotName(String botName) {
        BotScreen.botName = botName;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();

        if (Gdx.input.isKeyPressed(Input.Keys.B)) {
            myGame.setScreen(new GameModeScreen(myGame));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public static Alex_Clem getLets_Go_Baby() {
        return lets_Go_Baby;
    }

    public void setLets_Go_Baby(Alex_Clem lets_Go_Baby) {
        this.lets_Go_Baby = lets_Go_Baby;
    }
}
