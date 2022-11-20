/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes2;

/**
 *
 * @author Tiago
 */
public class Testes2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String[] str =  {"uva", "pera", "laranja", "manga", "goiaba"};
        String [] saida;
 
        /** Combinacoes de 5 elementos agrupados
         * de 3 em 3.
         */
        Combinacao comb1 = new Combinacao(str, 3) ;
        while (comb1.hasNext()) {
            saida = comb1.next();
            System.out.println(saida[0] + "," + saida[1] + "," + saida[2]) ;
        } 
       
    }
}
