//package com.cripto.controller;
//import java.util.Scanner;
//
//public class Questionário {
//
//    Scanner scanner = new Scanner(System.in);
//    int score = 0;
//
//    String[][] questions = {
//            {"Qual é seu conhecimento sobre Criptomoedas?", "Não tenho conhecimento", "Pouco conhecimento", "Médio conhecimento", "Tenho conhecimento/Pular", "2"},
//            {"Você sabe como funciona uma carteira Digital?", "Não tenho conhecimento", "Pouco conhecimento", "Médio conhecimento", "Tenho conhecimento/Pular", "2"},
//            {"Quem escreveu 'Dom Quixote'?", "Machado de Assis", "William Shakespeare", "Miguel de Cervantes", "José Saramago", "3"},
//            {"Qual é o maior planeta do sistema solar?", "Terra", "Marte", "Júpiter", "Saturno", "3"}
//    };
//
//        for (String[] question : questions) {
//        System.out.println(question[0]);
//        for (int i = 1; i <= 4; i++) {
//            System.out.println(i + ") " + question[i]);
//        }
//        System.out.print("Sua resposta: ");
//        int answer = scanner.nextInt();
//
//        if (Integer.toString(answer).equals(question[5])) {
//            System.out.println("Correto!\n");
//            score++;
//        } else {
//            System.out.println("Errado! A resposta correta era: " + question[Integer.parseInt(question[5])] + "\n");
//        }
//    }
//
//        System.out.println("Quiz finalizado! Sua pontuação: " + score + " de " + questions.length);
//        scanner.close();
//}
//
//}
//
//
//
//
//
//
