package model.services;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {

	private Double pricePerDay;
	private Double pricePerHour;
	
	private TaxService taxService;

	//Construtor usando a interface para nao acoplamento.
	//Vale notar que nao e instanciado na classe para evitar o acoplamento
	public RentalService(Double pricePerDay, Double pricePerHour, TaxService taxService) {
		this.pricePerDay = pricePerDay;
		this.pricePerHour = pricePerHour;
		this.taxService = taxService;
	}
	
	//Processa a nota
	public void processInvoice(CarRental carRental) {
		//Vai guardar na variavel o valor em mili segundos da entrada e da saida
		long t1 = carRental.getStart().getTime();
		long t2 = carRental.getFinish().getTime();
		//Diminui um pelo outro para encontrar a diferenca e ja converte para hora
		double hours = (double)(t2 - t1) / 1000 / 60 / 60;
		
		//Gera o valor
		double basicPayment;
		if (hours <= 12.0) {
			basicPayment = pricePerHour * Math.ceil(hours);
		}
		else {
			basicPayment = pricePerDay * Math.ceil(hours / 24);
		}
		//Soma o imposto na pagamento
		basicPayment += taxService.tax(basicPayment);
		
		//Cria a nota
		carRental.setInvoice(new Invoice(basicPayment, basicPayment));
	}
}
