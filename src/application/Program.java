package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import model.entities.CarRental;
import model.entities.Vehicle;
import model.services.BrazilTaxService;
import model.services.RentalService;

public class Program {

	public static void main(String[] args) throws ParseException {
		//Seta a localizacao para usar o . ao inves da ,
		Locale.setDefault(Locale.US);
		//Pega o padrao de entrada que e o teclado
		Scanner sc = new Scanner(System.in);
		//Para formatar a data
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		//Entra os dados do alugue - modelo do carro
		System.out.println("Enter rental data");
		System.out.print("Car model: ");
		String carModel = sc.nextLine();
		//Entra a data de inicio no formato proposto
		System.out.print("Pickup (dd/MM/yyyy HH:mm): ");
		Date start = sdf.parse(sc.nextLine());
		//Entra a data de fim no formato proposto
		System.out.print("Return (dd/MM/yyyy HH:mm): ");
		Date finish = sdf.parse(sc.nextLine());
		
		//Instacia o veiculo e ja vincula ele ao aluguel do carro
		CarRental cr = new CarRental(start, finish, new Vehicle(carModel));

		//Entra o preco por hora
		System.out.print("Enter price per hour: ");
		double pricePerHour = sc.nextDouble();
		//Entra o preco por dia
		System.out.print("Enter price per day: ");
		double pricePerDay = sc.nextDouble();
		
		//Cria o Servico de aluguel passando os precos de dia e hora inclusive a INTERFACE de pagamento 
		RentalService rentalService = new RentalService(pricePerDay, pricePerHour, new BrazilTaxService());
		
		//Processa a nota passando o aluguel do carro que contem a data inicial e final
		rentalService.processInvoice(cr);

		//Fecha a nota e imprime
		System.out.println("INVOICE:");
		System.out.println("Basic payment: " + String.format("%.2f", cr.getInvoice().getBasicPayment()));
		System.out.println("Tax: " + String.format("%.2f", cr.getInvoice().getTax()));
		System.out.println("Total payment: " + String.format("%.2f", cr.getInvoice().getTotalPayment()));
		
		sc.close();
	}
}
