package code.Bot;

import code.Physics.Rungekuttasolver;
import code.Screens.PuttingGameScreen;

import java.util.Arrays;

public class AgentBot {

    private float[] sensorSize;
    private float[] sensorsAngleX;
    private float[] sensorsAngleZ;
    private float[] maxPositionX;
    private float[] maxPositionZ;
    private float[] sensorsWeight;
    private float ballX;
    private float ballZ;
    private float powerScalar;
    private int bestSensor;

    private boolean[] canHitFlag;
    private boolean[] isSensorOnSand;

    public AgentBot(float[] sensorsSize, float[] sensorsAngleX, float[] sensorsAngleZ, float[] maxPositionX, float[] maxPositionZ,  boolean[] canHitFlag, boolean[] isSensorOnSand, float ballX, float ballZ) {

        this.sensorSize = sensorsSize;
        this.sensorsAngleX = sensorsAngleX;
        this.sensorsAngleZ = sensorsAngleZ;
        this.maxPositionX = maxPositionX;
        this.maxPositionZ = maxPositionZ;
        this.canHitFlag = canHitFlag;
        this.isSensorOnSand = isSensorOnSand;
        this.ballX = ballX;
        this.ballZ = ballZ;

        this.sensorsWeight = new float[sensorsSize.length];
        for (int i = 0; i < sensorsWeight.length; i++) {
            int[] config1 = new int[]{10,7,5,3,2,1,2,3,5,7,10};
            int[] config2 = new int[]{15,10,8,4,2,1,2,4,8,10,15};
            int[] config3 = new int[]{1,1,1,1,1,1,1,1,1,1,1};
            sensorsWeight[i] = config2[i];
        }

        System.out.println("sensorsSize = " + Arrays.toString(sensorsSize));
        System.out.println("sensorsAngleX = " + Arrays.toString(sensorsAngleX));
        System.out.println("sensorsAngleZ = " + Arrays.toString(sensorsAngleZ));
        System.out.println("maxSensorsX = " + Arrays.toString(maxPositionX));
        System.out.println("maxSensorsZ = " + Arrays.toString(maxPositionZ));
        System.out.println("canHitFlag = " + Arrays.toString(canHitFlag));
    }

    public float[] startBot() {

        for (int i = 0; i < sensorsWeight.length; i++) {
            if (isSensorOnSand[i]) {
                sensorsWeight[i] = sensorsWeight[i]*2;
            }
        }

        for (int i = 0; i < sensorSize.length; i++) {
            if (sensorSize[i] < 1.5f) {
                sensorsWeight[i] = sensorsWeight[i]*10;
            }
        }

        float[] sensorsCost = new float[sensorSize.length];
        for (int i = 0; i < sensorSize.length; i++) {
            sensorsCost[i] = costFunction(i);
        }

        bestSensor = 0;
        float minimumCost = sensorsCost[0];
        for (int i = 0; i < sensorsCost.length; i++) {
            if (sensorsCost[i] < minimumCost) {
                minimumCost = sensorsCost[i];
                bestSensor = i;
            }
        }

        if (Math.abs(sensorsAngleX[bestSensor]) < 0.001 || Math.abs(sensorsAngleZ[bestSensor]) < 0.001) {
            bestSensor++;
        }

        System.out.println("sensorsCost = " + Arrays.toString(sensorsCost));
        System.out.println("bestSensor = " + (bestSensor+1));
        System.out.println("best angle x = " + sensorsAngleX[bestSensor]);
        System.out.println("best angle z = " + sensorsAngleZ[bestSensor]);

        Rungekuttasolver RK4 = new Rungekuttasolver();

        powerScalar = PuttingGameScreen.evaluatePowerRK4(ballX, ballZ, maxPositionX[bestSensor], maxPositionZ[bestSensor], sensorsAngleX[bestSensor], sensorsAngleZ[bestSensor]);

        System.out.println("powerScalar = " + powerScalar);
        System.out.println();

        if (sensorsCost[bestSensor] == 0) {
            powerScalar += (1/(sensorSize[bestSensor]))*powerScalar;
        }

        RK4.setValues(ballX, ballZ, (sensorsAngleX[bestSensor]*powerScalar), (sensorsAngleZ[bestSensor]*powerScalar));
        RK4.RK4();

        float newBallX = (float) RK4.getX();
        float newBallZ = (float) RK4.getY();

        PuttingGameScreen.countTries++;

        return new float[]{newBallX, newBallZ};
    }

    public float costFunction(int index) {

        if (canHitFlag[index]) {
            return 0;
        }
        return sensorsWeight[index]/sensorSize[index];
    }

    public int getBestSensor() {
        return bestSensor;
    }

    public float getPowerScalar() {
        return powerScalar;
    }
}
