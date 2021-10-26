package LojaVirtual;

public class Pagamento {

	private double formPagamento;
	


	public double getformPagamento() {
		return formPagamento;
	}

	public void setformPagamento(double formPagamento) {
		this.formPagamento = formPagamento;
	}



	public void setPixDinheiro(double total) {
		this.formPagamento = total * 0.8;
	}

	public void setCredito(double total, char r) {
		if (r == 's' || r == 'S') {
			this.formPagamento = total * 0.85;
		} else {
			this.formPagamento = total * 0.9;
		}
	}

	public void setParcelado(double total, int parcelas) {
		if (parcelas <= 3) {
			this.formPagamento = total;
		} else {
			this.formPagamento = 1.1 * total;
		}
	}
	

}
