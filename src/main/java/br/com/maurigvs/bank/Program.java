package br.com.maurigvs.bank;

import br.com.maurigvs.bank.model.Bank;
import br.com.maurigvs.bank.model.Company;
import br.com.maurigvs.bank.model.Person;

public class Program {

    public static void main(String[] args) {

        Bank bb = new Bank();

        Person mauri = new Person("009.506.549-06", "Mauri", "Celio Gonçalves Junior");
        int pin = 1234;
        Long contaMauri = bb.openConsumerAccount(mauri, pin, 789.00);

        bb.deposit(contaMauri, pin, "Pagamento de Salario", 9624.14);
        bb.deposit(contaMauri, pin,"Restituição do IRPF", 8062.12);
        bb.withdraw(contaMauri, pin,"Pagamento de Aluguel", 1500);
        bb.withdraw(contaMauri, pin,"Transferencia Psicologa", 350);
        bb.withdraw(contaMauri, pin,"Conta de Celular", 72);

        Person sandy = new Person("111111111", "Sandy", "Aparecida dos Santos");
        int pin2 = 3456;
        Long contaSandy = bb.openConsumerAccount(sandy, pin2,1000);

        bb.deposit(contaSandy, pin2, "Recebimento de Pro-labore", 5000.00);
        bb.withdraw(contaSandy, pin2, "Conta de Luz", 150.00);

        bb.transfer(contaMauri, contaSandy, pin, "Pix para Sandy", 1000);

        bb.transfer(contaSandy, contaMauri, pin, "Pix para Junior", 10000);
        bb.transfer(contaSandy, contaMauri, pin, "Pix para Junior", 150.32);

        System.out.println("Titular: " + mauri.getHolderName());
        System.out.println("Extrato: " + bb.getStatement(contaMauri));
        System.out.println("Saldo: \n" + bb.getBalance(contaMauri));


        System.out.println("Titular: " + sandy.getHolderName());
        System.out.println("Extrato: " + bb.getStatement(contaSandy));
        System.out.println("Saldo: \n" + bb.getBalance(contaSandy));


        Company salao = new Company("2132321412413", "Escultural Estética");
        int pin3 = 23456;
        Long contaSalao = bb.openCommercialAccount(salao, pin3, 500.00);

        bb.deposit(contaSalao, pin3, "Pagamento de clientes", 3400.00);
        bb.deposit(contaSalao, pin3, "Recebimento de Cartão de Credito", 2800.00);
        bb.withdraw(contaSalao, pin3, "Pagamento de Fornecedor", 1900.00);

        bb.authorizePerson(contaSalao, sandy);

        bb.transfer(contaSalao, contaSandy, pin3, "Pro-Labore para Sandy", 2500.00);

        System.out.println("Titular: " + salao.getHolderName());
        System.out.println("Extrato: " + bb.getStatement(contaSalao));
        System.out.println("Saldo: \n" + bb.getBalance(contaSalao));

        System.out.println("Extrato: " + bb.getStatement(contaSandy));
        System.out.println("Saldo: \n" + bb.getBalance(contaSandy));
    }
}
