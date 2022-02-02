package code.Q_learning;

public class ExceptionHandeling extends Exception {

    public ExceptionHandeling(String error){
            if (error == "activation_function"){
                System.out.println("ERROR: activation function is written incorrectly or does not exist");
            }
    }
}
