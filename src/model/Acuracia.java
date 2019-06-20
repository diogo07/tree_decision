package model;

public class Acuracia {
	
	
	public static double calcular(DataSet dataset, Arvore arvore) {
		int acertos = 0;
		int erros = 0;
		int indiceArrayClasse = dataset.getRegistroAt(0).getValores().size() - 1;
		
		for (int i = 0; i < dataset.size(); i++) {
			if(arvore.predict(dataset.getRegistroAt(i)).equals(dataset.getRegistroAt(i).getValores().get(indiceArrayClasse))) {
				acertos++;
			}else {
				erros++;
			}
		}
		
		System.out.println("Acertos: "+acertos);
		System.out.println("Erros: "+erros);
		
		double acuracia =  (double)(acertos) / (double)(dataset.size());
		
		return acuracia*100;
	}

}
