package com.example.proyectoparcial;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.Stack;

public class calculator {
    @FXML
    public Label screen;

    private String operacion = "";

    @FXML
    public void clickNumero(ActionEvent actionEvent) {
        // Obtener el número del botón presionado
        Button boton = (Button) actionEvent.getSource();
        String numero = boton.getText();

        // Concatenar el número a la operación
        operacion = operacion + numero;
        screen.setText(operacion);
    }

    @FXML
    public void clickOperador(ActionEvent actionEvent) {
        // Obtener el operador del botón presionado
        Button boton = (Button) actionEvent.getSource();
        String operador = boton.getText();

        // Concatenar el operador a la operación
        operacion = operacion + operador;
        screen.setText(operacion);
    }

    @FXML
    public void clickIgual(ActionEvent actionEvent) {
        try {
            // Evaluar la expresión matemática ingresada
            double resultado = evaluarExpresion(operacion);
            // Mostrar el resultado en la pantalla
            screen.setText(String.valueOf(resultado));
            // Limpiar la variable de operación para una nueva entrada
            operacion = "";
        } catch (ArithmeticException | NumberFormatException e) {
            // Si hay un error en la expresión matemática, muestra "Error" en la pantalla
            screen.setText("Error");
            operacion = "";
        }
    }

    private double evaluarExpresion(String expresion) {
        // Divide la expresión en partes separando los operadores y operandos
        String[] partes = expresion.split("(?=[-+*/])|(?<=[-+*/])");
        // Utiliza dos pilas para evaluar la expresión
        Stack<Double> operandos = new Stack<>();
        Stack<String> operadores = new Stack<>();

        for (String parte : partes) {
            if (esOperador(parte)) {
                // Es un operador, lo agregamos a la pila de operadores
                operadores.push(parte);
            } else {
                // Es un operando, lo agregamos a la pila de operandos
                operandos.push(Double.parseDouble(parte));
            }

            // Realizamos las operaciones si hay suficientes operandos y operadores en las pilas
            while (operandos.size() >= 2 && operadores.size() >= 1) {
                double segundoOperando = operandos.pop();
                double primerOperando = operandos.pop();
                String operador = operadores.pop();
                double resultado = aplicarOperador(primerOperando, operador, segundoOperando);
                operandos.push(resultado);
            }
        }

        // Al final, el resultado estará en la cima de la pila de operandos
        return operandos.pop();
    }

    private boolean esOperador(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }

    private double aplicarOperador(double a, String operador, double b) {
        switch (operador) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                if (b != 0) {
                    return a / b;
                } else {
                    throw new ArithmeticException("División por cero");
                }
            default:
                throw new IllegalArgumentException("Operador inválido: " + operador);
        }
    }
}


